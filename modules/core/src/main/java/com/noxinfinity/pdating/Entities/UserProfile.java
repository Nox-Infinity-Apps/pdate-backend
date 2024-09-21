package com.noxinfinity.pdating.Entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Table(name = "user_profile")
public class UserProfile {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column()
    private UUID userId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Users user;

    @Column(name = "full_name", length = 100)
    private String fullName;

    @Column(name = "gpa", precision = 2)
    private float gpa;

    @Column(name="age", columnDefinition = "FLOAT DEFAULT 18", nullable = true)
    private int age;

    @ManyToOne
    @JoinColumn(name = "major_id")
    private Majors majors;

    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProfilePics> profilePics;

}
