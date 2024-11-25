package com.noxinfinity.pdating.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Entity
@Accessors(chain = true)
public class UserInConversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "conversation_id", referencedColumnName = "id")
    private Conversations conversation;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fcm_id", referencedColumnName = "fcm_id")
    private UserData userData;
}
