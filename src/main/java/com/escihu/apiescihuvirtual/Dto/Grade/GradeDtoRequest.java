package com.escihu.apiescihuvirtual.Dto.Grade;

import com.escihu.apiescihuvirtual.persistence.Entity.Subject.Subject;
import lombok.Builder;

@Builder
public class GradeDtoRequest {

    private Subject subject;

    private String description;

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
