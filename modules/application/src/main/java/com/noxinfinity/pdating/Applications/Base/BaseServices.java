package com.noxinfinity.pdating.Applications.Base;

import com.noxinfinity.pdating.Entities.UserHobbies;
import com.noxinfinity.pdating.graphql.types.*;

import java.util.List;
import java.util.stream.Collectors;

public class BaseServices {
    public static UserData graphqlMapper(com.noxinfinity.pdating.Entities.UserData user){
        UserData userData = new UserData();

        userData.setUserId(user.getUserId() != null ? user.getUserId() : "");
        userData.setFullName(user.getFullName() != null ? user.getFullName() : "");
        userData.setAvatar(user.getAvatarUrl() != null ? user.getAvatarUrl() : "");
        userData.setBio(user.getBio() != null ? user.getBio() : "");
        // Handle null dob safely
        userData.setDob(user.getDob() != null ? user.getDob().toString() : "");
        userData.setEmail(user.getAuth() != null && user.getAuth().getEmail() != null ? user.getAuth().getEmail() : "");
        userData.setProvider(user.getAuth() != null && user.getAuth().getProvider() != null ? user.getAuth().getProvider().toString() : "");
        userData.setFcmNotificationToken(user.getAuth() != null && user.getAuth().getFcmNotificationToken() != null ? user.getAuth().getFcmNotificationToken() : "");
        userData.setGrade(user.getGrade() != null ?
                new Grade.Builder()
                        .id(user.getGrade().getId() != null ? Math.toIntExact(user.getGrade().getId()) : null)
                        .name(user.getGrade().getName() != null ? user.getGrade().getName() : "")
                        .build()
                : null);
        userData.setMajor(user.getMajor() != null ?
                new Major.Builder()
                        .id(user.getMajor().getId() != null ? Math.toIntExact(user.getMajor().getId()) : null)
                        .name(user.getMajor().getName() != null ? user.getMajor().getName() : "")
                        .iconUrl(user.getMajor().getIconUrl() != null ? user.getMajor().getIconUrl() : "")
                        .build()
                : null);
        userData.setLocation(user.getLocation() != null ?
                new Location.Builder()
                        .id(user.getLocation().getId() != null ? Math.toIntExact(user.getLocation().getId()) : null)
                        .lat(user.getLocation().getLat() != null ? user.getLocation().getLat() : "")
                        .lng(user.getLocation().getLng() != null ? user.getLocation().getLng() : "")
                        .build()
                : null);
        userData.setHobbies(user.getHobbies() != null ? mapUserHobbiesToHobbies(user.getHobbies()) : null);
        return userData;
    }

    private static List<Hobbies> mapUserHobbiesToHobbies(List<UserHobbies> userHobbiesList) {
        return userHobbiesList.stream()
                .map(userHobby -> new com.noxinfinity.pdating.graphql.types.Hobbies.Builder()
                        .id(Math.toIntExact(userHobby.getHobbies().getId()))
                        .title(userHobby.getHobbies().getTitle())
                        .iconUrl(userHobby.getHobbies().getIconUrl())
                        .build())
                .collect(Collectors.toList());
    }
}
