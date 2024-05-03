package com.natixis.problem.adapter.repositories;

import com.natixis.problem.adapter.entities.ProblemEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProblemRepository extends JpaRepository<ProblemEntity, Integer> {

}
