package com.natixis.problem.adapter.controllers;

import com.natixis.problem.adapter.dtos.CommitteeDTO;
import com.natixis.problem.adapter.dtos.ProblemIdDTO;
import com.natixis.problem.adapter.service.CommitteeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/committee")
@AllArgsConstructor
public class CommitteeController {
    private final CommitteeService committeeService;

    @PostMapping
    public ResponseEntity<CommitteeDTO> create(@Valid @RequestBody CommitteeDTO committeeDto){
        CommitteeDTO createCommitteeDto = committeeService.createCommittee(committeeDto);
        if (createCommitteeDto != null)
            return ResponseEntity.status(HttpStatus.CREATED).body(createCommitteeDto);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @GetMapping
    public ResponseEntity<List<CommitteeDTO>> listAllCommittees(){
        return ResponseEntity.status(HttpStatus.OK).body(committeeService.findAllCommittees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommitteeDTO> getCommitteById(@PathVariable("id") Integer id){
        CommitteeDTO committeeById = committeeService.getCommitteeById(id);
        if (committeeById != null)
            return ResponseEntity.status(HttpStatus.OK).body(committeeById);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PostMapping("/{id}/add-problem")
    public ResponseEntity<?> addProblem(@PathVariable("id") Integer id,@Valid @RequestBody ProblemIdDTO problemIdDTO){
        CommitteeDTO committeeById = committeeService.addProblem(id, problemIdDTO.getProblemId());
        if (committeeById != null)
            return ResponseEntity.status(HttpStatus.OK).body(committeeById);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Problem with ID " + problemIdDTO.getProblemId() + " need minimum one comment to be available to enter on the Committee Id " + id);
    }


    @GetMapping("/{id}/close")
    public ResponseEntity<?> closeCommitte(@PathVariable("id") Integer id){
        CommitteeDTO committeeById = committeeService.closeCommitte(id);
        if (committeeById != null)
            return ResponseEntity.status(HttpStatus.OK).body(committeeById);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Committee id " + id + " is invalid! ");
    }
}
