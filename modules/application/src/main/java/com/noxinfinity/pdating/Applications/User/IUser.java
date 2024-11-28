package com.noxinfinity.pdating.Applications.User;

import com.noxinfinity.pdating.graphql.types.BlockUserResponse;
import com.noxinfinity.pdating.graphql.types.DontCareUserResponse;
import com.noxinfinity.pdating.graphql.types.UserSuggest;

import java.util.List;

public interface IUser {
    List<UserSuggest> getSuggestedUsersWithFilter(String userId, double currentLat, double currentLng, Integer offset, Integer categoryId);

    DontCareUserResponse dontCareUser(String userId, String dontCareUserId);
}
