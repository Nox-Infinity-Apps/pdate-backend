package com.noxinfinity.pdating.Domains.AuthManagement.JWT;

import com.noxinfinity.pdating.graphql.types.UserFromGoogle;

public interface IJwtService {
    String generateToken(UserFromGoogle user);

    Boolean isTokenValid(String token);
}
