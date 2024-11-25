package com.noxinfinity.pdating.Applications.User;

import com.noxinfinity.pdating.Applications.Base.BaseServices;
import com.noxinfinity.pdating.Domains.DatingTargetManagement.DatingTargetRepository;
import com.noxinfinity.pdating.Domains.UserManagement.UserDataRepository;
import com.noxinfinity.pdating.Entities.UserData;
import com.noxinfinity.pdating.Entities.DatingTarget;
import com.noxinfinity.pdating.graphql.types.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUser {
    private final UserDataRepository userDataRepository;
    private final DatingTargetRepository datingTargetRepository;

    @Autowired
    public UserService(UserDataRepository userDataRepository, DatingTargetRepository datingTargetRepository) {
        this.userDataRepository = userDataRepository;
        this.datingTargetRepository = datingTargetRepository;
    }

    public List<UserSuggest> getSuggestedUsers(String currentUserId, double currentLat, double currentLng, int limit, int offset) {
        List<Object[]> results = userDataRepository.findSuggestedUsers(currentUserId, currentLat, currentLng, limit, offset);
        List<UserSuggest> suggestedUsers = new ArrayList<>();

        for (Object[] result : results) {
            UserSuggest user = new UserSuggest();

            user.setFcmId((String) result[0]);
            user.setFullName((String) result[1]);
            user.setDob(result[2] != null ? result[2].toString() : null);
            user.setAvatarUrl((String) result[3]);

            // Ánh xạ Grade
            Grade grade = new Grade();
            grade.setId(result[4] != null ? ((Number) result[4]).intValue() : null);
            grade.setName((String) result[5]);
            user.setGrade(grade);

            // Ánh xạ Major
            Major major = new Major();
            major.setId(result[6] != null ? ((Number) result[6]).intValue() : null);
            major.setName((String) result[7]);
            major.setIconUrl((String) result[8]);
            user.setMajor(major);

            user.setBio((String) result[9]);
            user.setDistance(result[10] != null ? ((Number) result[10]).intValue() : null);

            // Ánh xạ commonHobbies từ chuỗi thành List<Hobbie>
            String hobbiesString = (String) result[11];
            List<Hobbie> commonHobbies = new ArrayList<>();
            if (hobbiesString != null) {
                String[] hobbiesArray = hobbiesString.split("\\|");
                for (String hobbyStr : hobbiesArray) {
                    String[] hobbyDetails = hobbyStr.split(":");
                    if (hobbyDetails.length == 4) {
                        Hobbie hobby = new Hobbie();
                        hobby.setId(Integer.parseInt(hobbyDetails[0].trim()));
                        hobby.setTitle(hobbyDetails[1].trim());
                        hobby.setIconUrl(hobbyDetails[2] + hobbyDetails[3].trim());
                        commonHobbies.add(hobby);
                    }
                }
            }
            user.setCommonHobbies(commonHobbies);

            suggestedUsers.add(user);
        }

        return suggestedUsers;
    }
    public BlockUserResponse blockUser(String currentUserId, String blockedUserId) {
        UserData blocker = userDataRepository.findById(currentUserId)
                .orElseThrow(() ->  new RuntimeException("Not Found"));

        UserData blocked = userDataRepository.findById(blockedUserId)
                .orElseThrow(() ->  new RuntimeException("Not Found"));
        blocker.getBlockedUsers().add(blocked);

        userDataRepository.save(blocker);
        return BlockUserResponse.newBuilder().message("User blocked").status(StatusEnum.SUCCESS).build();
    }

    public BlockUserResponse unblockUser(String currentUserId, String blockedUserId) {
        UserData blocker = userDataRepository.findById(currentUserId)
                .orElseThrow(() ->  new RuntimeException("Not Found"));

        UserData blocked = userDataRepository.findById(blockedUserId)
                .orElseThrow(() ->  new RuntimeException("Not Found"));
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

    public List<com.noxinfinity.pdating.graphql.types.UserData> listDatingTarget(String currentUserId, int id) {
        List<UserData> datingTarget = userDataRepository.findUsersWithSameDatingTarget(currentUserId, (long)id);
        List<com.noxinfinity.pdating.graphql.types.UserData> datingTargets = new ArrayList<>();
        for (UserData blockedUser : datingTarget) {
            datingTargets.add(BaseServices.graphqlMapper(blockedUser));
        }
        return datingTargets;
    }

    @Override
    public AddDatingTargetResponse addDatingTarget(String userId, int id) {
        UserData user = userDataRepository.findById(userId).orElseThrow(() -> new RuntimeException("Internal"));
        DatingTarget datingTarget = datingTargetRepository.findById((long) id).orElseThrow(() -> new RuntimeException("Not Found"));
        user.getDatingTargets().add(datingTarget);
        userDataRepository.save(user);
        return AddDatingTargetResponse.newBuilder().message("User added").status(StatusEnum.SUCCESS).build();
    }
}
