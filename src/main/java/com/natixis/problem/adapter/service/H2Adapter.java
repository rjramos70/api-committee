package com.natixis.problem.adapter.service;

import com.natixis.problem.adapter.entities.CommentEntity;
import com.natixis.problem.adapter.entities.CommitteeEntity;
import com.natixis.problem.adapter.entities.ProblemEntity;
import com.natixis.problem.adapter.repositories.CommentRepository;
import com.natixis.problem.adapter.repositories.CommitteeRepository;
import com.natixis.problem.adapter.repositories.ProblemRepository;
import static com.natixis.problem.adapter.utils.CustomMapper.fromXtoY;

import com.natixis.problem.domain.Comment;
import com.natixis.problem.domain.Committee;
import com.natixis.problem.domain.Problem;
import com.natixis.problem.domain.ports.PortAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class H2Adapter implements PortAdapter {
    private final CommitteeRepository committeeRepository;
    private final ProblemRepository problemRepository;
    private final CommentRepository commentRepository;

    /**
     * Committee
     */
    @Transactional
    @Override
    public Committee saveOrUpdateCommittee(Committee committee) {
        CommitteeEntity entity = fromXtoY(committee, CommitteeEntity.class);
        return fromXtoY(committeeRepository.save(entity), Committee.class);
    }

    @Override
    public List<Committee> listCommittees() {
        return committeeRepository
                .findAll()
                .stream()
                .map(entity -> fromXtoY(entity, Committee.class))
                .collect(Collectors.toList());
    }

    @Override
    public Committee findCommitteeById(Integer id) {
        Optional<CommitteeEntity> optionalCommitteeEntity = committeeRepository.findById(id);
        return optionalCommitteeEntity.map(committeeEntity -> fromXtoY(committeeEntity, Committee.class)).orElse(null);
    }

    /**
     * Problem
     */
    @Override
    public Problem saveOrUpdateProblem(Problem problem) {
        ProblemEntity entity = fromXtoY(problem, ProblemEntity.class);
        return fromXtoY(problemRepository.save(entity), Problem.class);
    }

    @Override
    public List<Problem> listProblems() {
        return problemRepository
                .findAll()
                .stream()
                .map(entity -> fromXtoY(entity, Problem.class))
                .collect(Collectors.toList());
    }

    @Override
    public Problem findProblemById(Integer id) {
        Optional<ProblemEntity> optionalProblemEntity = problemRepository.findById(id);
        return optionalProblemEntity.map(problem -> fromXtoY(problem, Problem.class)).orElse(null);
    }


    /**
     * Comment
     */
    @Override
    public Comment saveOrUpdateComment(Comment comment) {
        return fromXtoY(commentRepository.save(fromXtoY(comment, CommentEntity.class)), Comment.class);
    }

    @Override
    public Comment findCommentById(Integer id) {
        Optional<CommentEntity> optionalCommentEntity = commentRepository.findById(id);
        return optionalCommentEntity.map(commentEntity -> fromXtoY(commentEntity, Comment.class)).orElse(null);
    }

    @Override
    public List<Comment> listComments() {
        return commentRepository
                .findAll()
                .stream()
                .map(entity -> fromXtoY(entity, Comment.class))
                .collect(Collectors.toList());
    }


}
