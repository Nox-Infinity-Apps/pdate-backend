package com.noxinfinity.pdating.Domains.AuthManagement;

import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepo {
    Auth findUserAuthAndCheckPassword(String username, String password);
}
