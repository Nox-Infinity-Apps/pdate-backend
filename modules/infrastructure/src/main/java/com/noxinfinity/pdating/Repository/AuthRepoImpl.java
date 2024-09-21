package com.noxinfinity.pdating.Repository;

import com.noxinfinity.pdating.Domains.AuthManagement.Auth;
import com.noxinfinity.pdating.Domains.AuthManagement.AuthRepo;
import org.springframework.stereotype.Repository;

import java.util.Objects;
@Repository
public class AuthRepoImpl implements AuthRepo {
    @Override
    public Auth findUserAuthAndCheckPassword(String username, String password) {
        //findAuth
        if (Objects.equals(username, "admin") && Objects.equals(password, "123456")){
            return new Auth("admin");
        }
        return null;
    }
}
