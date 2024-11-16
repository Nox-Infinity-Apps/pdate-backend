package com.noxinfinity.pdating.Domains.AuthManagement.Google;

import com.noxinfinity.pdate.graphql.types.LoginWithGoogle;

public interface IGoogleService {
    Boolean isValidToken(String token) throws Exception;
    Object getUserData(String token);

    String getEmailUser(String token) throws Exception;

    LoginWithGoogle getUserGoogleByToken(String token) throws Exception;
}
