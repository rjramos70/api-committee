package com.natixis.problem.adapter.entities;

import com.natixis.problem.domain.enums.StatusProblem;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_problem")
public class ProblemEntity extends BaseEntity {

    private String name;

    @Enumerated(EnumType.STRING)
    private StatusProblem status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "problemId", cascade = CascadeType.ALL)
    private List<CommentEntity> comments;

    public void addComment(CommentEntity commentEntity){
        this.comments.add(commentEntity);
    }
}
