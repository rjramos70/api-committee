package com.natixis.problem.adapter.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.natixis.problem.domain.enums.StatusCommittee;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_committee")
public class CommitteeEntity extends BaseEntity {

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private StatusCommittee status;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "t_committee_problems",
            joinColumns = @JoinColumn(name = "committee_id"),
            inverseJoinColumns = @JoinColumn(name = "problem_id")
    )
    private List<ProblemEntity> problems;

    public void addProblem(ProblemEntity problem){
        this.problems.add(problem);
    }

}
