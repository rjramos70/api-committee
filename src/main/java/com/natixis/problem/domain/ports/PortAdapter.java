package com.natixis.problem.domain.ports;

import com.natixis.problem.domain.Comment;
import com.natixis.problem.domain.Committee;
import com.natixis.problem.domain.Problem;

import java.util.List;

public interface PortAdapter {
    /**
     * Committee
     */
    Committee saveOrUpdateCommittee(Committee committee);
    List<Committee> listCommittees();
    Committee findCommitteeById(Integer id);

    /**
     * Problem
     */
    Problem saveOrUpdateProblem(Problem problem);
    List<Problem> listProblems();
    Problem findProblemById(Integer id);


    /**
     * Comment
     */
    Comment saveOrUpdateComment(Comment comment);
    Comment findCommentById(Integer id);
    List<Comment> listComments();

}
