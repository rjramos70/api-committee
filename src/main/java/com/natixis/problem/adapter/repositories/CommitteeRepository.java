package com.natixis.problem.adapter.repositories;


import com.natixis.problem.adapter.entities.CommitteeEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommitteeRepository extends JpaRepository<CommitteeEntity, Integer> {
}
