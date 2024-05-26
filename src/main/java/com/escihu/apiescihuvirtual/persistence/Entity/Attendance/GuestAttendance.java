package com.escihu.apiescihuvirtual.persistence.Entity.Attendance;

import jakarta.persistence.*;

@Entity
@Table(name = "guest_attendance")
public class GuestAttendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String apellido;
    private String procedencia;
    private String image;

    public GuestAttendance(Long id, String name, String apellido, String procedencia) {
        this.id = id;
        this.name = name;
        this.apellido = apellido;
        this.procedencia = procedencia;
    }

    public GuestAttendance() {

    }
}
