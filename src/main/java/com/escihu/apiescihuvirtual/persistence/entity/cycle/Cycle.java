package com.escihu.apiescihuvirtual.persistence.entity.cycle;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "cycles")
public class Cycle implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cycleId;
}
