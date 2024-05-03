package com.natixis.problem.adapter.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_comment")
public class CommentEntity extends BaseEntity {

    private String description;
    private Integer problemId;

}
