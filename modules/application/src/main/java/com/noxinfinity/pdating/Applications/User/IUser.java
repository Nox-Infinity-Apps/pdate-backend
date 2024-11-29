package com.noxinfinity.pdating.Applications.User;

import com.noxinfinity.pdating.graphql.types.BlockUserResponse;
import com.noxinfinity.pdating.graphql.types.DontCareUserResponse;
import com.noxinfinity.pdating.graphql.types.UserData;
import com.noxinfinity.pdating.graphql.types.UserSuggest;

import java.util.List;

public interface IUser {
    List<UserSuggest> getSuggestedUsersWithFilter(String userId, double currentLat, double currentLng, Integer offset, Integer categoryId);

    DontCareUserResponse dontCareUser(String userId, String dontCareUserId);

    List<UserSuggest> getSuggestedUsers(String currentUserId, double currentLat, double currentLng, int offset);
    BlockUserResponse blockUser(String currentUserId, String blockedUserId);
    BlockUserResponse unblockUser(String currentUserId, String blockedUserId);
    List<UserData> listBlockedUsers(String currentUserId);
    List<UserSuggest> getSuggestedUsersNearBy(String currentUserId, double currentLat, double currentLng, int offset);
    List<com.noxinfinity.pdating.graphql.types.UserData> getLikedUser(String userId);
}
