package com.noxinfinity.pdating.Domains.JwtManagement;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.noxinfinity.pdating.Domains.AuthManagement.AuthRepo;
import com.noxinfinity.pdating.Domains.AuthManagement.Auth;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthRepo authRepo;

    public CustomUserDetailsService(AuthRepo authRepo) {
        this.authRepo = authRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Auth auth = authRepo.findByUsername(username);
        if (auth == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(
                auth.getUsername(),
                auth.getPassword(),
                auth.getAuthorities()
        );
    }
}