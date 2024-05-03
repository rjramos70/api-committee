package com.natixis.problem.adapter.exceptions.types;

public class ProblemNotFoundException extends RuntimeException{
    public ProblemNotFoundException(String message){
        super(message);
    }
}
