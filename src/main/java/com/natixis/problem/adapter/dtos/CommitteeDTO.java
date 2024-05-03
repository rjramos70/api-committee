package com.natixis.problem.adapter.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.natixis.problem.domain.enums.StatusCommittee;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommitteeDTO implements Serializable {
    private Integer id;

    @Past @NotNull(message = "date should follow the pattern YYYY-MM-DD")
    private LocalDate date;
    private StatusCommittee status;

    private List<ProblemDTO> problems;

}
