package com.noxinfinity.pdating.Entities.Enums;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title")
    private String title;

    @Column(name="field")
    private String field;

    @Column(name="label")
    private String label;

    @Column(name = "icon_url")
    private String iconUrl;
}
