package com.escihu.apiescihuvirtual.Dto;

import com.escihu.apiescihuvirtual.persistence.Entity.Classroom.Classroom;
import com.escihu.apiescihuvirtual.persistence.Entity.Teacher.Teacher;
import lombok.Builder;

@Builder
public class CourseDtoRequest {

    private String name;
    private Teacher manager;
    private Classroom classroom;

    public CourseDtoRequest(String name, Teacher manager, Classroom classroom) {
        this.name = name;
        this.manager = manager;
        this.classroom = classroom;
    }

    public CourseDtoRequest() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Teacher getManager() {
        return manager;
    }

    public void setManager(Teacher manager) {
        this.manager = manager;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }
}
