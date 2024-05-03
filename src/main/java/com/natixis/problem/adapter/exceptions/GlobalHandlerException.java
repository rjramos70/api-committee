package com.natixis.problem.adapter.exceptions;

import com.natixis.problem.adapter.exceptions.types.CommitteSavedWithRestrictionsException;
import com.natixis.problem.adapter.exceptions.types.CommitteeNotFoundException;
import com.natixis.problem.adapter.exceptions.types.ProblemCannotBeAddException;
import com.natixis.problem.adapter.exceptions.types.ProblemNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalHandlerException extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }


    @ExceptionHandler({ProblemCannotBeAddException.class})
    public ResponseEntity<Object> handleProblemCannotBeAddException(ProblemCannotBeAddException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler({ProblemNotFoundException.class})
    public ResponseEntity<Object> handleProblemNotFoundException(ProblemNotFoundException ex){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler({CommitteeNotFoundException.class})
    public ResponseEntity<Object> handleCommitteeNotFoundException(CommitteeNotFoundException ex){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler({CommitteSavedWithRestrictionsException.class})
    public ResponseEntity<Object> handleCommitteeSavedWithRestrictionsException(CommitteSavedWithRestrictionsException ex){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }

}
