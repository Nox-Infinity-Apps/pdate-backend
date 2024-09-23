package com.noxinfinity.pdating.Entities;

import com.noxinfinity.pdating.Domains.UserProfileManagement.UserProfile;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "favorites")
public class Favorites {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Long id;

    @Column(name = "name", length = 50, nullable = false, unique = true)
    private String name;

    @Column(name = "icon")
    private String icon;

    @ManyToOne
    @JoinColumn(name = "user_profile_id", nullable = false)
    private UserProfile userProfile;
}
