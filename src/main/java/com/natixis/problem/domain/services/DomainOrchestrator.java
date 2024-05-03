package com.natixis.problem.domain.services;

import com.natixis.problem.adapter.exceptions.types.CommitteeNotFoundException;
import com.natixis.problem.adapter.exceptions.types.ProblemCannotBeAddException;
import com.natixis.problem.adapter.exceptions.types.ProblemNotFoundException;
import com.natixis.problem.adapter.service.HttpAdapter;
import com.natixis.problem.domain.Comment;
import com.natixis.problem.domain.Committee;
import com.natixis.problem.domain.Problem;
import com.natixis.problem.domain.enums.StatusCommittee;
import com.natixis.problem.domain.enums.StatusProblem;
import com.natixis.problem.domain.ports.*;

import java.util.List;
import java.util.stream.Collectors;

public class DomainOrchestrator implements HttpAdapter {

    private final PortAdapter portAdapter;

    public DomainOrchestrator(PortAdapter portAdapter) {
        this.portAdapter = portAdapter;
    }


    @Override
    public Committee createCommittee(Committee committee) {
        return portAdapter.saveOrUpdateCommittee(committee);
    }

    @Override
    public List<Committee> listAllCommittees() {
        return portAdapter.listCommittees();
    }

    @Override
    public Committee getCommitteeById(Integer id) {
        Committee committee = portAdapter.findCommitteeById(id);
        if (committee == null){
            throw new CommitteeNotFoundException("Committee id " + id + " is not valid!");
        }
        return committee;
    }

    @Override
    public Committee closeCommitteeById(Integer id) {
        // Check if Committee is valid
        Committee committee = portAdapter.findCommitteeById(id);
        if (committee == null){
            throw new CommitteeNotFoundException("Committee id " + id + " is not valid!");
        }
        /**
         * director close the committee, and all problems
         * on the agenda are closed
         */
        committee.setStatus(StatusCommittee.CLOSED); // close committee
        // Close and save each Problem
        List<Problem> problems = committee.getProblems().stream().map(problem -> {
            problem.setStatus(StatusProblem.CLOSE);
            return portAdapter.saveOrUpdateProblem(problem);
        }).collect(Collectors.toList());

        committee.setProblems(problems);

        return portAdapter.saveOrUpdateCommittee(committee);

    }

    @Override
    public Committee addProblemToCommittee(Integer committeeId, Integer problemId) {
        Problem problem = portAdapter.findProblemById(problemId);
        if (problem == null){
            throw new ProblemNotFoundException("Problem id " + problemId + " is not valid!");
        }
        /**
         * When a comment has been added to the problem, it's ready to be added to a committee.
         */
        if ((problem.getComments() == null) || (problem.getComments().size() == 0)){
            throw new ProblemCannotBeAddException("Problem id " + problemId + " need minimum one comment to be acceptable in a committee!");
        }

        Committee committee = portAdapter.findCommitteeById(committeeId);
        if (committee == null){
            throw new CommitteeNotFoundException("Committee id " + committeeId + " is not valid!");
        }
        // Add Problem to Comment
        committee.addProblem(problem);
        return portAdapter.saveOrUpdateCommittee(committee);
    }

    @Override
    public Problem createProblem(Problem problem) {
        return portAdapter.saveOrUpdateProblem(problem);
    }

    @Override
    public List<Problem> listAllProblems() {
        return portAdapter.listProblems();
    }

    @Override
    public Problem getProblemById(Integer id) {
        return portAdapter.findProblemById(id);
    }

    @Override
    public Problem addCommentToProblem(Integer problemId, Comment comment) {
        // Check if problem is valid
        Problem problem = portAdapter.findProblemById(problemId);
        if (problem == null){
            throw new ProblemNotFoundException("Problem id " + problemId + " is not valid!");
        }
        // Check if comment is valid
        comment.setProblem(problem);
        Comment savedComment = portAdapter.saveOrUpdateComment(comment);
        // Add comment into Problem
        problem.addComment(savedComment);

        return portAdapter.saveOrUpdateProblem(problem);

    }
}
