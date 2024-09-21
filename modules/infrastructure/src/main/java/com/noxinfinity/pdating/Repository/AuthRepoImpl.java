package com.noxinfinity.pdating.Repository;

import com.noxinfinity.pdating.Domains.AuthManagement.AuthRepo;
import org.springframework.stereotype.Repository;

import java.util.Objects;
@Repository
public class AuthRepoImpl implements AuthRepo {
    @Override
    public boolean findUserAuthAndCheckPassword(String username, String password) {
        //findAuth
        return Objects.equals(username, "admin") && Objects.equals(password, "123456");
    }
}
