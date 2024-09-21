package com.noxinfinity.pdating.Repository;

import com.noxinfinity.pdating.Domains.AuthManagement.Auth;
import com.noxinfinity.pdating.Domains.AuthManagement.AuthRepo;
import org.springframework.stereotype.Repository;

import java.util.Objects;
@Repository
public class AuthRepoImpl implements AuthRepo {
    @Override
    public Auth findByUsername(String username) {
        return new Auth(username,"12345", null);
    }
}
