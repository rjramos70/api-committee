package com.natixis.problem.adapter.service;

import com.natixis.problem.domain.Comment;
import com.natixis.problem.domain.Committee;
import com.natixis.problem.domain.Problem;

import java.util.List;

public interface HttpAdapter {
    /**
     * Committee
     */
    Committee createCommittee(Committee committee);
    List<Committee> listAllCommittees();
    Committee getCommitteeById(Integer id);
    Committee closeCommitteeById(Integer id);
    Committee addProblemToCommittee(Integer committeeId, Integer problemId);


    /**
     * Problem
     */
    Problem createProblem(Problem problem);
    List<Problem> listAllProblems();
    Problem getProblemById(Integer id);
    Problem addCommentToProblem(Integer problemId, Comment comment);



}
