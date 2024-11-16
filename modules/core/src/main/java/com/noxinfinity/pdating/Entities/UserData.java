package com.noxinfinity.pdating.Entities;

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
    private UUID userId;

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @ManyToOne
    @JoinColumn(name = "grade_id", referencedColumnName = "id")
    private Grades grade;

    @ManyToOne
    @JoinColumn(name = "major_id", referencedColumnName = "id")
    private Majors major;

    @Column(name = "bio")
    private String bio;

    @OneToOne
    @JoinColumn(name = "fcm_id", referencedColumnName = "fcm_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Auth auth;

    @OneToOne(mappedBy = "userData", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserLocation location;

    @OneToMany(mappedBy = "userData", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserHobbies> hobbies;

    @OneToMany(mappedBy = "currentUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserLikes> likesGiven;

    @OneToMany(mappedBy = "targetUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserLikes> likesReceived;

    @OneToMany(mappedBy = "userData", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserInConversation> conversationsParticipated;
}
