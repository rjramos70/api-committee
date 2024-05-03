package com.natixis.problem.adapter.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemIdDTO implements Serializable {

    @NotNull(message = "problemId an integer and is mandatory")
    private Integer problemId;
}
