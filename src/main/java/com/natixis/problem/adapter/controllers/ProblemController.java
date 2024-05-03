package com.natixis.problem.adapter.controllers;

import com.natixis.problem.adapter.dtos.CommentDTO;
import com.natixis.problem.adapter.dtos.ProblemDTO;
import com.natixis.problem.adapter.service.ProblemService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/problem")
@AllArgsConstructor
public class ProblemController {
    private final ProblemService problemService;

    @PostMapping
    public ResponseEntity<ProblemDTO> create(@Valid @RequestBody ProblemDTO problemDTO){
        System.out.println(">>>> ProblemController - ProblemDTO :: " + problemDTO);
        ProblemDTO createProblemDto = problemService.createProblem(problemDTO);
        if (createProblemDto != null)
            return ResponseEntity.status(HttpStatus.CREATED).body(createProblemDto);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProblemDTO> getProblemById(@PathVariable("id") Integer id){
        System.out.println(">> getProblemById :: " + id);
        ProblemDTO problemById = problemService.getProblemById(id);
        if (problemById != null)
            return ResponseEntity.status(HttpStatus.OK).body(problemById);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PostMapping("/{id}/add-comment")
    public ResponseEntity<ProblemDTO> addCommentByProblemId(@PathVariable("id") Integer id,@Valid @RequestBody CommentDTO commentDTO){
        ProblemDTO problemDtoUpdate = problemService.addComment(id, commentDTO);
        if (problemDtoUpdate != null)
            return ResponseEntity.status(HttpStatus.OK).body(problemDtoUpdate);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @GetMapping
    public ResponseEntity<List<ProblemDTO>> listAll(){
        return ResponseEntity.status(HttpStatus.OK).body(problemService.findAllProblems());
    }
}
