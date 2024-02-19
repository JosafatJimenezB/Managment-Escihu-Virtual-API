package com.escihu.apiescihuvirtual.persistence.Entity.Subject;

import com.escihu.apiescihuvirtual.persistence.Entity.Classroom.Classroom;
import com.escihu.apiescihuvirtual.persistence.Entity.Course.Course;
import com.escihu.apiescihuvirtual.persistence.Entity.Grade.Grade;
import com.escihu.apiescihuvirtual.persistence.Entity.Teacher.Teacher;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "subjects")
public class Subject implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    @NotNull
    private Course course;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "classroom_id")
    @NotNull
    private Classroom classroom;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    @NotNull
    private Teacher teacher;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private List<SubjectSchedule> schedules;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_id")
    private List<Grade> grades;
}
