package com.natixis.problem.adapter.repositories;

import com.natixis.problem.adapter.entities.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
}
