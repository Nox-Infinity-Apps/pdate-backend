package com.noxinfinity.pdating.Entities;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "majors")
@Getter
public class Majors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="major_id")
    private Long id;

    @Column(name="name", length = 225)
    private String name;


}
