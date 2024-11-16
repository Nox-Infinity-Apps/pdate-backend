package com.noxinfinity.pdating.Domains.AuthManagement.Google;

import com.noxinfinity.pdating.graphql.types.UserFromGoogle;

public interface IGoogleService {
    Boolean isValidToken(String token);
    void checkToken(String token) throws Exception;
    Object getUserData(String token);
    UserFromGoogle getUser(String token) throws Exception;
}
