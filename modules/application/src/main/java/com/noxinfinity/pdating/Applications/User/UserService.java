package com.noxinfinity.pdating.Applications.User;

import com.noxinfinity.pdating.Applications.Base.BaseServices;
import com.noxinfinity.pdating.Domains.CategoryManagement.CategoryRepository;
import com.noxinfinity.pdating.Domains.UserManagement.UserDataRepository;
import com.noxinfinity.pdating.Entities.Enums.Category;
import com.noxinfinity.pdating.Entities.Hobbies;
import com.noxinfinity.pdating.Entities.UserData;
import com.noxinfinity.pdating.graphql.types.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUser {
    private final UserDataRepository userDataRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public UserService(UserDataRepository userDataRepository, CategoryRepository categoryRepository) {
        this.userDataRepository = userDataRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<UserSuggest> getSuggestedUsers(String currentUserId, double currentLat, double currentLng, int offset) {
        return BaseServices.mapUserSuggest(userDataRepository.findSuggestedUsers(currentUserId, currentLat, currentLng, offset * 10));

    }
    public BlockUserResponse blockUser(String currentUserId, String blockedUserId) {
        UserData blocker = userDataRepository.findById(currentUserId)
                .orElseThrow(() ->  new RuntimeException("Internal"));

        UserData blocked = userDataRepository.findById(blockedUserId)
                .orElseThrow(() ->  new RuntimeException("Internal"));
        blocker.getBlockedUsers().add(blocked);

        userDataRepository.save(blocker);
        return BlockUserResponse.newBuilder().message("User blocked").status(StatusEnum.SUCCESS).build();
    }

    public BlockUserResponse unblockUser(String currentUserId, String blockedUserId) {
        UserData blocker = userDataRepository.findById(currentUserId)
                .orElseThrow(() ->  new RuntimeException("Internal"));

        UserData blocked = userDataRepository.findById(blockedUserId)
                .orElseThrow(() ->  new RuntimeException("Internal"));
        blocker.getBlockedUsers().remove(blocked);

        userDataRepository.save(blocker);
        return BlockUserResponse.newBuilder().message("User unblocked").status(StatusEnum.SUCCESS).build();
    }

    public List<com.noxinfinity.pdating.graphql.types.UserData> listBlockedUsers(String currentUserId) {
        UserData blocker = userDataRepository.findById(currentUserId).orElseThrow(() -> new RuntimeException("Internal"));
        List<com.noxinfinity.pdating.graphql.types.UserData> blockedUsers = new ArrayList<>();
        for (UserData blockedUser : blocker.getBlockedUsers()) {
            blockedUsers.add(BaseServices.graphqlMapper(blockedUser));
        }
        return blockedUsers;
    }

    @Override
    public List<UserSuggest> getSuggestedUsersWithFilter(String userId, double currentLat, double currentLng, Integer offset, Integer categoryId) {
        Category category = categoryRepository.findById((long) categoryId).orElseThrow(() -> new RuntimeException("Not Found"));
        UserData user = userDataRepository.findById(userId).orElseThrow(() -> new RuntimeException("Internal"));
        if (category.getField().equals("purpose")){
            return BaseServices.mapUserSuggest(userDataRepository.findSuggestedUsersByPurpose(userId, currentLat, currentLng, offset * 10, category.getLabel()));
        }
        if (category.getField().equals("major")){

            return BaseServices.mapUserSuggest(userDataRepository.findSuggestedUsersByMajor(userId, currentLat, currentLng, offset * 10, user.getMajor().getId()));
        }
        return null;
    }

    @Override
    public DontCareUserResponse dontCareUser(String userId, String dontCareUserId) {
        UserData user = userDataRepository.findById(userId).orElseThrow(() -> new RuntimeException("Internal"));
        UserData dontCareUser = userDataRepository.findById(dontCareUserId).orElseThrow(() -> new RuntimeException("Internal"));
        user.getDontCaredUsers().add(dontCareUser);
        userDataRepository.save(user);
        return DontCareUserResponse.newBuilder().message("Dont care about " + dontCareUser.getFullName()).status(StatusEnum.SUCCESS).build();
    }

    public List<UserSuggest> getSuggestedUsersNearBy(String currentUserId, double currentLat, double currentLng, int offset) {
        return BaseServices.mapUserSuggest(userDataRepository.findSuggestedUsersNearBy(currentUserId, currentLat, currentLng, offset * 10));

    }

    public List<com.noxinfinity.pdating.graphql.types.UserData> getLikedUser(String userId){
        UserData user = userDataRepository.findById(userId).orElseThrow(() -> new RuntimeException("Internal"));
        List<com.noxinfinity.pdating.Entities.UserData> likedUsers = new ArrayList<>();
        for(com.noxinfinity.pdating.Entities.UserLikes i : user.getLikesGiven()){
            UserData userLike = userDataRepository.findById(i.getCurrentUserId()).orElse(null);
            if (userLike != null) {
                likedUsers.add(userLike);
            }
        }
        return BaseServices.mapUsers(likedUsers);
    }
}
