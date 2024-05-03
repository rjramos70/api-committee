package com.natixis.problem.domain;

import com.natixis.problem.domain.enums.StatusProblem;

import java.util.ArrayList;
import java.util.List;

public class Problem{
    private Integer id;
    private String name;
    private StatusProblem status;
    private List<Comment> comments;

    public Problem(Integer id, String name, StatusProblem status, List<Comment> comments) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.comments = comments;
    }

    public Problem() {
        this.comments = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StatusProblem getStatus() {
        return status;
    }

    public void setStatus(StatusProblem status) {
        this.status = status;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment){
        this.comments.add(comment);
    }

}
