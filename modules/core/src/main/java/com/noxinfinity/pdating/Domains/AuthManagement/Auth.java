package com.noxinfinity.pdating.Domains.AuthManagement;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "auth")
public class Auth {
    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    private String username;
    private String password;


    public Auth(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
