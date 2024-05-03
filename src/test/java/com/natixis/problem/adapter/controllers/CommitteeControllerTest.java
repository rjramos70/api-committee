package com.natixis.problem.adapter.controllers;

import com.natixis.problem.adapter.entities.CommitteeEntity;
import com.natixis.problem.domain.enums.StatusCommittee;
import com.natixis.problem.domain.enums.StatusProblem;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import com.natixis.problem.adapter.dtos.CommitteeDTO;
import com.natixis.problem.adapter.dtos.ProblemIdDTO;
import com.natixis.problem.adapter.entities.ProblemEntity;
import com.natixis.problem.adapter.repositories.CommentRepository;
import com.natixis.problem.adapter.repositories.CommitteeRepository;
import com.natixis.problem.adapter.repositories.ProblemRepository;
import com.natixis.problem.utils.TestUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = Replace.ANY)
class CommitteeControllerTest {
    @Autowired
    WebTestClient webTestClient;

    @Autowired
    CommitteeRepository committeeRepository;

    @Autowired
    ProblemRepository problemRepository;

    @Autowired
    CommentRepository commentRepository;


    @BeforeEach
    public void setUp(){
        // Clean tables
        committeeRepository.deleteAll();
        problemRepository.deleteAll();
        commentRepository.deleteAll();

    }

    @Test
    void createCommittee() {
        System.out.println(">> Create test....");
        System.out.println(">>  Qtd. Before : " + committeeRepository.count());
        committeeRepository.deleteAll();
        System.out.println(">>  Qtd. After : " + committeeRepository.count());


        CommitteeDTO committeeDTO = new CommitteeDTO(
                null,
                LocalDate.of(2024, 04, 28),
                null,
                null);


        webTestClient
                .post()
                .uri("/v1/committee")
                .bodyValue(committeeDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(CommitteeDTO.class)
                .value(committee -> {
                    assertNotNull(committee.getId());
                    assertEquals(committeeDTO.getDate(), committee.getDate());
                    assertEquals(1, committee.getId());
                    assertEquals("IN_PROGRESS", committee.getStatus().toString());
                });
    }

    @Test
    void listAllCommittees() {
        committeeRepository.saveAll(TestUtils.getListOfCommittees());
        webTestClient
                .get()
                .uri("/v1/committee")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(CommitteeDTO.class)
                .value(committees -> {
                    System.out.println(">> listAllCommittees - committees :: " + committees.size());
                    assertEquals(3, committees.size());
                    assertEquals(1, committees.get(0).getId());
                    assertEquals(TestUtils.getLocalDate1(), committees.get(0).getDate());
                    assertEquals("IN_PROGRESS", committees.get(0).getStatus().toString());
                });

        committeeRepository.deleteAll();
    }

    @Test
    void getCommitteById() {
        committeeRepository.saveAll(TestUtils.getListOfCommittees());

        webTestClient
                .get()
                .uri("/v1/committee/{id}", 1)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(CommitteeDTO.class)
                .value(committee -> {
                    System.out.println("committee :: " + committee);
                    assertEquals(1, committee.getId());
                    assertEquals(TestUtils.getLocalDate1(), committee.getDate());
                    assertEquals("IN_PROGRESS", committee.getStatus().toString());
                    assertNull(committee.getProblems());
                });
//        committeeRepository.deleteAll();
    }

    @Test
    void addProblemError() {
        CommitteeEntity committeeEntity = new CommitteeEntity(LocalDate.of(2024, 04, 28), StatusCommittee.IN_PROGRESS, null);
        committeeRepository.save(committeeEntity);

        ProblemEntity problemEntity = new ProblemEntity("Problem 1", StatusProblem.OPEN, null);
        problemRepository.save(problemEntity);

        ProblemIdDTO problemIdDTO = new ProblemIdDTO();
        problemIdDTO.setProblemId(1);

        webTestClient
                .post()
                .uri("/v1/committee/{id}/add-problem", 1)
                .bodyValue(problemIdDTO)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8))
                .expectBody(String.class)
                .value(message -> assertEquals("Problem id 1 need minimum one comment to be acceptable in a committee!", message));

    }

//    @Test
//    void addProblem() {
//        CommitteeEntity committeeEntity = new CommitteeEntity(LocalDate.of(2024, 04, 28), StatusCommittee.IN_PROGRESS, null);
//        CommitteeEntity savedCommittee = committeeRepository.save(committeeEntity);
//
//        ProblemEntity problemEntity = new ProblemEntity(
//                "Problem 1",
//                StatusProblem.OPEN,
//                Arrays.asList(
//                        new CommentEntity("Comment 1", 1),
//                        new CommentEntity("Comment 2", 1),
//                        new CommentEntity("Comment 3", 1)
//                ));
//
//        ProblemEntity savedProblem = problemRepository.save(problemEntity);
//
//        System.out.println(">>>>> Saved Committee ID : " + savedCommittee.getId());
//        System.out.println(">>>>> Saved Problem ID : " + savedProblem.getId());
//
//        ProblemIdDTO problemIdDTO = new ProblemIdDTO();
//        problemIdDTO.setProblemId(1);
//
//        webTestClient
//                .post()
//                .uri("/v1/committee/{id}/add-problem", 1)
//                .bodyValue(problemIdDTO)
//                .exchange()
//                .expectStatus().isOk()
//                .expectHeader().contentType(MediaType.APPLICATION_JSON)
//                .expectBody(CommitteeDTO.class)
//                .value(committee -> {
//                    assertNotNull(committee.getId());
//                    assertEquals(TestUtils.getLocalDate1(), committee.getDate());
//                    assertEquals(1, committee.getId());
//                    assertEquals("IN_PROGRESS", committee.getStatus().toString());
//                    assertEquals(1, committee.getProblems().size());
//                });
//    }

//    @Test
//    void closeCommitte() {
//        ProblemEntity problemWithoutComment = TestUtils.getProblemWithoutComment();
//        List<CommentEntity> listOfComments = TestUtils.getListOfComments();
//        problemWithoutComment.setComments(listOfComments);
//        problemRepository.save(problemWithoutComment);
//        committeeRepository.save(TestUtils.getCommitteeWithProblemWithCommentList(problemWithoutComment));
//
//        webTestClient
//                .get()
//                .uri("/v1/committee/{id}/close", 1)
//                .exchange()
//                .expectStatus().isOk()
//                .expectHeader().contentType(MediaType.APPLICATION_JSON)
//                .expectBody(CommitteeDTO.class)
//                .value(committee -> {
//                    System.out.println("committee :: " + committee);
//                    assertEquals(1, committee.getId());
//                    assertEquals(TestUtils.getLocalDate1(), committee.getDate());
//                    assertEquals("CLOSED", committee.getStatus().toString());
//                    assertEquals(1, committee.getProblems().size());
//                    assertEquals("CLOSE", committee.getProblems().get(0).getStatus().toString());
//                    assertEquals(3, committee.getProblems().get(0).getComments().size());
//                });
//    }
}