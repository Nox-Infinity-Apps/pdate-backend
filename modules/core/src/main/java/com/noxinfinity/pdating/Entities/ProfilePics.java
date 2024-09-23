package com.noxinfinity.pdating.Entities;

import com.noxinfinity.pdating.Domains.UserProfileManagement.UserProfile;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class ProfilePics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Long id;

    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserProfile userProfile;
}
