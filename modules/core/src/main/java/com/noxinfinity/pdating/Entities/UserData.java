package com.noxinfinity.pdating.Entities;

import com.noxinfinity.pdating.Entities.Enums.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.checkerframework.checker.units.qual.C;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Accessors(chain = true)
public class UserData {
    @Id
    @Column(name = "fcm_id", unique = true, nullable = false)
    private String userId;

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "public_avatar_id")
    private String publicAvataId;

    @ManyToOne
    @JoinColumn(name = "grade_id", referencedColumnName = "id")
    private Grades grade;

    @ManyToOne
    @JoinColumn(name = "major_id", referencedColumnName = "id")
    private Majors major;

    @Column(name = "bio")
    private String bio;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", columnDefinition = "ENUM('FEMALE', 'MALE', 'OTHER') DEFAULT 'OTHER'")
    private Gender gender;

    @Column(name = "is_activated", columnDefinition = "DEFAULT 0")
    private Integer isActivated;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "is_verified", columnDefinition = "DEFAULT false")
    private boolean isVerified;

    @OneToOne
    @JoinColumn(name = "fcm_id", referencedColumnName = "fcm_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Auth auth;

    @OneToOne(mappedBy = "userData", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserLocation location;

    @OneToMany(mappedBy = "userData", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserHobbies> hobbies;

    @OneToMany(mappedBy = "currentUser", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<UserLikes> likesGiven;

    @OneToMany(mappedBy = "targetUser", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<UserLikes> likesReceived;

    @OneToMany(mappedBy = "userData", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserInConversation> conversationsParticipated;

    @OneToMany(mappedBy = "userData", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserPics> userPics;

    @ManyToMany
    @JoinTable(
            name = "user_blocks",
            joinColumns = @JoinColumn(name = "blocker_id", referencedColumnName = "fcm_id"),
            inverseJoinColumns = @JoinColumn(name = "blocked_id", referencedColumnName = "fcm_id")
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<UserData> blockedUsers;

    @ManyToMany(mappedBy = "blockedUsers")
    private List<UserData> blockedByUsers;

    @ManyToMany
    @JoinTable(
            name = "user_purposes",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "fcm_id"),
            inverseJoinColumns = @JoinColumn(name = "purpose_id", referencedColumnName = "id")
    )
    private List<Purpose> purposes;

    @ManyToMany
    @JoinTable(
            name = "user_dont_cared",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "fcm_id"),
            inverseJoinColumns = @JoinColumn(name = "dont_care_user_id", referencedColumnName = "fcm_id")
    )
    private List<UserData> dontCaredUsers;
}
