package com.noxinfinity.pdating.Entities;

import com.noxinfinity.pdating.Entities.Enums.Conversation_Type;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Conversations {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="conversation_id")
    private UUID id;

    @Column(name="conversation_type", columnDefinition = "ENUM('M_CHAT', 'WS_CHAT')")
    @Enumerated(EnumType.STRING)
    private Conversation_Type type;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_conversations",
            joinColumns = @JoinColumn(name = "conversation_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<Users> usersInConversation;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Messages> messagesInConversation;
}
