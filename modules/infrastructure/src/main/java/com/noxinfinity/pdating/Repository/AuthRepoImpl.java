package com.noxinfinity.pdating.Repository;

import com.noxinfinity.pdating.Domains.AuthManagement.Auth;
import com.noxinfinity.pdating.Domains.AuthManagement.AuthRepo;
import org.springframework.stereotype.Repository;

@Repository
public class AuthRepoImpl implements AuthRepo {
    @Override
    public Auth findByUsername(String username) {
        return new Auth(username,"$2a$10$bahESyqLKseg.vjNZsoS1eHmJnv3iUt7mvsWHcfawX/H6ONHVB4pK", null);
    }
}
