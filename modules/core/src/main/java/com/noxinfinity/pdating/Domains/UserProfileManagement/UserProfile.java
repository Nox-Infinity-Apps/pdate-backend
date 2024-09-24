package com.noxinfinity.pdating.Domains.UserProfileManagement;

import com.noxinfinity.pdating.Domains.UserManagement.Users;
import com.noxinfinity.pdating.Entities.Enums.Gender;
import com.noxinfinity.pdating.Entities.Favorites;
import com.noxinfinity.pdating.Entities.Majors;
import com.noxinfinity.pdating.Entities.ProfilePics;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Table(name = "user_profile")
public class UserProfile {

    @Id
    @Column(updatable = false, nullable = false)
    private UUID userId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Users user;

    @Column(name = "full_name", length = 100)
    private String fullName;

    @Column(name = "gpa", precision = 2, columnDefinition = "FLOAT DEFAULT NULL")
    private Float gpa;

    @Column(name = "age", columnDefinition = "INT DEFAULT 18")
    private Integer age;

    @Column(name = "address")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", columnDefinition = "ENUM('MALE', 'FEMALE', 'OTHER')")
    private Gender gender;

    @Column(name = "bio", length = 500)
    private String bio;

    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favorites> favorites;

    @ManyToOne
    @JoinColumn(name = "major_id")
    private Majors majors;

    @Column(name="is_verified", columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean isVerified;

    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProfilePics> profilePics;

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
