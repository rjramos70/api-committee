package com.natixis.problem.utils;

import com.natixis.problem.adapter.entities.CommentEntity;
import com.natixis.problem.adapter.entities.CommitteeEntity;
import com.natixis.problem.adapter.entities.ProblemEntity;
import com.natixis.problem.domain.enums.StatusCommittee;
import com.natixis.problem.domain.enums.StatusProblem;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class TestUtils {

    private static final LocalDate localDate1 = LocalDate.of(2024, 04, 28);
    private static final LocalDate localDate2 = LocalDate.of(2024, 04, 29);
    private static final LocalDate localDate3 = LocalDate.of(2024, 04, 30);

    public static Iterable<CommitteeEntity> getListOfCommittees() {

        return Arrays.asList(
                new CommitteeEntity(localDate1, StatusCommittee.IN_PROGRESS, null),
                new CommitteeEntity(localDate2, StatusCommittee.IN_PROGRESS, null),
                new CommitteeEntity(localDate3, StatusCommittee.IN_PROGRESS, null));
    }

    public static ProblemEntity getProblemWithoutComment(){
       return new ProblemEntity("Problem 1", StatusProblem.OPEN, null);
    }

    public static List<CommentEntity> getListOfComments(){
        return Arrays.asList(
                new CommentEntity("Comment 1", 1),
                new CommentEntity("Comment 2", 1),
                new CommentEntity("Comment 3", 1)
        );
    }

    public static ProblemEntity getProblemWithComment(){
        ProblemEntity problemEntity = getProblemWithoutComment();
        List<CommentEntity> commentEntityList = Arrays.asList(
                new CommentEntity("Comment 1", 1),
                new CommentEntity("Comment 2", 1),
                new CommentEntity("Comment 3", 1)
        );
        problemEntity.setComments(commentEntityList);
        return problemEntity;
    }

    public static Iterable<CommentEntity> getListOfCommentReferentProblemIdOne(){
//        ProblemEntity problemEntity = new ProblemEntity("Problem 1", StatusProblem.OPEN, null);
        return  Arrays.asList(
                new CommentEntity("Comment 1", 1),
                new CommentEntity("Comment 2", 1),
                new CommentEntity("Comment 3", 1)
        );

    }


    public static LocalDate getLocalDate1(){
        return localDate1;
    }


    public static CommitteeEntity getCommitteeWithProblemWithCommentList(ProblemEntity problem) {
        CommitteeEntity committee = new CommitteeEntity(localDate1, StatusCommittee.IN_PROGRESS, Arrays.asList(problem));
        return committee;
    }
}
