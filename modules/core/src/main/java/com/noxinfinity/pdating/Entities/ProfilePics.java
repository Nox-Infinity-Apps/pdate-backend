package com.noxinfinity.pdating.Entities;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class ProfilePics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserProfile userProfile;


}
