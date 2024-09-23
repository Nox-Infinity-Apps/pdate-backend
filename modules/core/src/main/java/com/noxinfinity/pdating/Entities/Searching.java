package com.noxinfinity.pdating.Entities;

import com.noxinfinity.pdating.Domains.UserManagement.Users;
import com.noxinfinity.pdating.Entities.Enums.Gender;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@Getter
@Entity
public class Searching
{
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(updatable = false, nullable = false)
    private UUID userId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Users user;

    @Column(name="latitude", columnDefinition = "FLOAT DEFAULT NULL")
    private double latitude;

    @Column(name="longitude",  columnDefinition = "FLOAT DEFAULT NULL")
    private double longitude;

    @Column(name="min_age_accept", columnDefinition = "INT DEFAULT 18")
    private int minAgeAccept;

    @Column(name="max_age_accept", columnDefinition = "INT DEFAULT 25")
    private int maxAgeAccept;

    @Enumerated(EnumType.STRING)
    @Column(name = "looking_for", columnDefinition = "ENUM('MALE', 'FEMALE', 'OTHER')")
    private Gender gender;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

}
