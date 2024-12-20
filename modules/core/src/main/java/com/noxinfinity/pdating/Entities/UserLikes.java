package com.noxinfinity.pdating.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class UserLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "current_user_id", insertable = false, updatable = false)
    private String currentUserId;

    @ManyToOne
    @JoinColumn(name = "current_user_id", referencedColumnName = "fcm_id")
    private UserData currentUser;

    @Column(name = "target_user_id", insertable = false, updatable = false)
    private String targetUserId;

    @ManyToOne
    @JoinColumn(name = "target_user_id", referencedColumnName = "fcm_id")
    private UserData targetUser;
}
