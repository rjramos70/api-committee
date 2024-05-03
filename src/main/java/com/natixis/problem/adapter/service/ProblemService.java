package com.natixis.problem.adapter.service;

import com.natixis.problem.adapter.dtos.CommentDTO;
import com.natixis.problem.adapter.dtos.ProblemDTO;
import com.natixis.problem.domain.Comment;
import com.natixis.problem.domain.Problem;
import com.natixis.problem.domain.enums.StatusProblem;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.natixis.problem.adapter.utils.CustomMapper.fromXtoY;

@Service
@AllArgsConstructor
public class ProblemService {
    private final HttpAdapter httpAdapter;

    public ProblemDTO createProblem(ProblemDTO problemDto) {
        /**
         * Once a problem has created, it is in open.
         */
        if (problemDto.getStatus() == null)
            problemDto.setStatus(StatusProblem.OPEN);
        return fromXtoY(httpAdapter.createProblem(fromXtoY(problemDto, Problem.class)), ProblemDTO.class);
    }


    public ProblemDTO addComment(Integer problemId, CommentDTO commentDto) {
        return fromXtoY(httpAdapter.addCommentToProblem(problemId, fromXtoY(commentDto, Comment.class)), ProblemDTO.class);
    }


    public ProblemDTO getProblemById(Integer id) {
        return fromXtoY(httpAdapter.getProblemById(id), ProblemDTO.class);
   }

    public List<ProblemDTO> findAllProblems() {
        return httpAdapter
                .listAllProblems()
                .stream()
                .map(domain -> fromXtoY(domain, ProblemDTO.class))
                .collect(Collectors.toList());
   }
}
