package com.noxinfinity.pdating.Entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
@Getter
public class Users {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "user_id")
    private UUID userId;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserProfile userProfile;


}
