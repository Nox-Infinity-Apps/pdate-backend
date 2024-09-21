package com.noxinfinity.pdating.Domains.AuthManagement;

public interface AuthRepo {
    boolean findUserAuthAndCheckPassword(String username, String password);
}
