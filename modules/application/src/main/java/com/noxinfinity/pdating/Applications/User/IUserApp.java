package com.noxinfinity.pdating.Applications.User;

import com.noxinfinity.pdating.graphql.types.UpdateUserInfo;
import com.noxinfinity.pdating.graphql.types.UserInfoSuccessResponse;

public interface IUserApp {
    UserInfoSuccessResponse getUserInfoById(String id);
    UserInfoSuccessResponse updateUserInfoById(String id, UpdateUserInfo body) throws Exception;
}
