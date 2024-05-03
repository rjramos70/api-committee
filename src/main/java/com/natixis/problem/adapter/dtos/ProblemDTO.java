package com.natixis.problem.adapter.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.natixis.problem.domain.enums.StatusProblem;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemDTO implements Serializable {
    private Integer id;
    @NotNull(message = "name is mandatory")
    private String name;
    private StatusProblem status;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CommentDTO> comments;
}
