package com.noxinfinity.pdating.Domains.AuthManagement;

import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepo {
    Auth findByUsername(String username);
    Auth addNewAuth(Auth auth);
}
