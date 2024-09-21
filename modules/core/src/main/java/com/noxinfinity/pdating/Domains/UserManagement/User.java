package com.noxinfinity.pdating.Domains.UserManagement;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue
    private long id;

    private String fullName;
    private String email;
    private String address;

    public long getId() {
        return id;
    }
}
