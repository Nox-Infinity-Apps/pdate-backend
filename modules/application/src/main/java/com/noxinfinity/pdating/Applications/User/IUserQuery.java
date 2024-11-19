package com.noxinfinity.pdating.Applications.User;

import com.noxinfinity.pdating.graphql.types.UserInfoSuccessResponse;

public interface IUserQuery {
    UserInfoSuccessResponse getUserInfoById(String id);
}
