package com.noxinfinity.pdating.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Accessors(chain = true)
public class UserLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lat")
    private String lat;

    @Column(name = "lng")
    private String lng;

    @OneToOne
    @JoinColumn(name = "fcm_id", referencedColumnName = "fcm_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserData userData;
}
