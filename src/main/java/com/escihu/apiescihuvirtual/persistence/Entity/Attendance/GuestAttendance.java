package com.escihu.apiescihuvirtual.persistence.Entity.Attendance;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "guest_attendance")
public class GuestAttendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String apellido;
    private String image;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date visitDate;

    public GuestAttendance() {

    }
}
