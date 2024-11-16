package com.noxinfinity.pdating.Domains.AuthManagement.Google;

public interface IGoogleService {
    Boolean isValidToken(String token) throws Exception;
    Object getUserData(String token);

    String getEmailUser(String token) throws Exception;
}
