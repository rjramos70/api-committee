package com.natixis.problem.domain;

import com.natixis.problem.domain.enums.StatusCommittee;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Committee{
    private Integer id;
    private LocalDate date;
    private StatusCommittee status;
    private List<Problem> problems;

    public Committee(LocalDate date, List<Problem> problems, StatusCommittee status) {
        this.date = date;
        this.status = status;
        this.problems = problems;
    }

    public Committee(LocalDate date, List<Problem> problems) {
        this.date = date;
        this.problems = problems;
    }


    public Committee(LocalDate date) {
        this.date = date;
        this.problems = new ArrayList<>();
    }

    public Committee() {
        this.problems = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Problem> getProblems() {
        return problems;
    }

    public void setProblems(List<Problem> problems) {
        this.problems = problems;
    }

    public StatusCommittee getStatus() {
        return status;
    }

    public void setStatus(StatusCommittee status) {
        this.status = status;
    }

    public void addProblem(Problem problem){
        this.problems.add(problem);
    }

}
