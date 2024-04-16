package com.escihu.apiescihuvirtual.persistence.Entity.Attendance;

import com.escihu.apiescihuvirtual.persistence.Entity.Attendance.Enum.TypeAttendace;
import com.escihu.apiescihuvirtual.persistence.Entity.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.sql.Timestamp;

@Entity
@Table(name = "attendance")
@CrossOrigin(origins = "*")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_attendance")
    private TypeAttendace typeAttendace;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Timestamp date;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    public Attendance(Long id, TypeAttendace typeAttendace, Timestamp date, User user) {
        this.id = id;
        this.typeAttendace = typeAttendace;
        this.date = date;
        this.user = user;
    }

    public Attendance() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeAttendace getTypeAttendace() {
        return typeAttendace;
    }

    public void setTypeAttendace(TypeAttendace typeAttendace) {
        this.typeAttendace = typeAttendace;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
