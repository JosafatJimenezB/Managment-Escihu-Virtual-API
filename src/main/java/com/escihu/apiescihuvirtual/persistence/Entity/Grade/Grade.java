package com.escihu.apiescihuvirtual.persistence.Entity.Grade;

import com.escihu.apiescihuvirtual.persistence.Entity.Subject.Subject;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 * La clase Grade representa una calificación en la base de datos.
 * Está mapeada a la tabla "grades" en la base de datos.
 */
@Builder
@Entity
@Table(name = "grades")
public class Grade implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @NotBlank
    private String description;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "grade_id")
    private List<GradeDetail> scores;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;

    public Grade(Long id, Subject subject, String description, List<GradeDetail> scores, Date createdAt, Date updatedAt) {
        this.id = id;
        this.subject = subject;
        this.description = description;
        this.scores = scores;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Grade() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<GradeDetail> getScores() {
        return scores;
    }

    public void setScores(List<GradeDetail> scores) {
        this.scores = scores;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
