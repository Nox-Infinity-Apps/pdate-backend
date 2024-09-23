package com.noxinfinity.pdating.Entities;

import com.noxinfinity.pdating.Domains.UserManagement.Users;
import com.noxinfinity.pdating.Entities.Enums.MessageContentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@Entity
public class Messages {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "message_id")
    private UUID id;

    @Column(name = "content_type", columnDefinition = "ENUM('TEXT', 'IMAGE', 'VIDEO','AUDIO','FILE','LINK')")
    @Enumerated(EnumType.STRING)
    private MessageContentType contentType;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "sender_user_id", referencedColumnName = "user_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Users sender;

    @ManyToOne
    @JoinColumn(name = "conversation_id", referencedColumnName = "conversation_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Conversations conversation;

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
