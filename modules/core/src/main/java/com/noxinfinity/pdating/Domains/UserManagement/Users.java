package com.noxinfinity.pdating.Domains.UserManagement;

import com.noxinfinity.pdating.Domains.AuthManagement.Auth;
import com.noxinfinity.pdating.Domains.UserProfileManagement.UserProfile;
import com.noxinfinity.pdating.Entities.Conversations;
import com.noxinfinity.pdating.Entities.Messages;
import com.noxinfinity.pdating.Entities.UserBlock;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Accessors(chain = true)
public class Users {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "user_id")
    private UUID userId;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Auth auth;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserProfile userProfile;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @ManyToMany(mappedBy = "usersInConversation")
    private List<Conversations> conversations;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserBlock> blockedUsers;

    @OneToMany(mappedBy = "blockedUser", cascade = CascadeType.ALL)
    private List<UserBlock> usersWhoBlockedMe;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private List<Messages> userSentMessage;

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
