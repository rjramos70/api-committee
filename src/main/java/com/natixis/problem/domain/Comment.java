package com.natixis.problem.domain;


public class Comment{
    private Integer id;
    private String description;
    private Problem problem;


    public Comment(String description) {
        this.description = description;
    }

    public Comment(String description, Problem problem) {
        this.description = description;
        this.problem = problem;
    }

    public Comment() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }
}
