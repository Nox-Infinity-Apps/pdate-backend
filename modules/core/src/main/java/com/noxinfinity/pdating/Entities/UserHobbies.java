package com.noxinfinity.pdating.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Entity
@Getter
@Setter
public class UserHobbies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fcm_id", insertable = false, updatable = false)
    private String fcmId;

    @Column(name = "hobby_id", insertable = false, updatable = false)
    private Long hobbyId;

    @ManyToOne
    @JoinColumn(name = "fcm_id", referencedColumnName = "fcm_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserData userData;

    @ManyToOne
    @JoinColumn(name = "hobby_id", referencedColumnName = "id")
    private Hobbies hobbies;
}
