package com.noxinfinity.pdating.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
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

    @Column(name="fullname")
    private String fullName;

    @Column(name="dob")
    private Date dob;

    @Column(name="avatar_url")
    private String avatarUrl;

    @Column(name="bio")
    private String bio;

    @OneToOne
    @JoinColumn(name = "fcm_id", referencedColumnName = "fcm_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Auth auth;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "current_user_id", referencedColumnName = "fcm_id")
    private UserData currentUser;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "target_user_id", referencedColumnName = "fcm_id")
    private UserData targetUser;
}

