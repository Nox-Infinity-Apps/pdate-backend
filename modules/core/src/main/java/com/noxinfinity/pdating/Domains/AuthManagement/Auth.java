package com.noxinfinity.pdating.Domains.AuthManagement;

import com.noxinfinity.pdating.Domains.UserManagement.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.Mapping;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "auth")
@Accessors(chain = true)
@Getter
@Setter
public class Auth implements UserDetails {
    @Id
    @Column(updatable = false, nullable = false)
    private UUID userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Users user;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private List<String> roles;


    public Auth(String username, String password,List<String> roles) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.username = username;
        this.password = encoder.encode(password);
        this.roles = roles;
    }

    public Auth() {
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
