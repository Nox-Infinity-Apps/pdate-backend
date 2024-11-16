package com.noxinfinity.pdating.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Majors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "icon_url")
    private String iconUrl;

    @Column(name = "name")
    private String name;
}
