package com.noxinfinity.pdating.Applications.Base;

import com.noxinfinity.pdating.Entities.UserHobbies;
import com.noxinfinity.pdating.graphql.types.*;

import java.util.ArrayList;
import java.util.Arrays;
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

    public static List<Category> mapCategory(List<com.noxinfinity.pdating.Entities.Enums.Category> categoryList) {
        return categoryList.stream().map(
                category -> new Category.Builder()
                        .id(Math.toIntExact(category.getId()))
                        .title(category.getTitle())
                        .iconUrl(category.getIconUrl())
                        .build())
                .collect(Collectors.toList());
    }

    public static List<UserSuggest> mapUserSuggest(List<Object[]> results) {
        List<UserSuggest> suggestedUsers = new ArrayList<>();

        for (Object[] result : results) {
            UserSuggest user = new UserSuggest();

            user.setFcmId((String) result[0]);
            user.setFullName((String) result[1]);
            user.setDob(result[2] != null ? result[2].toString() : null);
            user.setAvatarUrl((String) result[3]);

            // Ánh xạ Gender
            if (result[4] != null) {
                user.setGender(Gender.valueOf(result[4].toString()));
            } else {
                user.setGender(Gender.OTHER);
            }

            // Ánh xạ Grade
            Grade grade = new Grade();
            grade.setId(result[5] != null ? ((Number) result[5]).intValue() : null);
            grade.setName((String) result[6]);
            user.setGrade(grade);

            // Ánh xạ Major
            Major major = new Major();
            major.setId(result[7] != null ? ((Number) result[7]).intValue() : null);
            major.setName((String) result[8]);
            major.setIconUrl((String) result[9]);
            user.setMajor(major);

            user.setBio((String) result[10]);
            user.setDistance(result[11] != null ? ((Number) result[11]).intValue() : null);

            // Ánh xạ commonHobbies từ chuỗi thành List<Hobbie>
            String hobbiesString = (String) result[12];
            List<Hobbie> commonHobbies = new ArrayList<>();
            if (hobbiesString != null) {
                String[] hobbiesArray = hobbiesString.split("\\|");
                for (String hobbyStr : hobbiesArray) {
                    String[] hobbyDetails = hobbyStr.split(":");
                    if (hobbyDetails.length == 3) {
                        Hobbie hobby = new Hobbie();
                        hobby.setId(Integer.parseInt(hobbyDetails[0].trim()));
                        hobby.setTitle(hobbyDetails[1].trim());
                        hobby.setIconUrl(hobbyDetails[2].trim());
                        commonHobbies.add(hobby);
                    }
                }
            }
            user.setCommonHobbies(commonHobbies);

            // Ánh xạ purposes
            String purposesConcat = (String) result[13];
            List<String> purposes = new ArrayList<>();
            if (purposesConcat != null) {
                purposes = Arrays.asList(purposesConcat.split("\\|"));
            }
            user.setPurpose(purposes);

            // Ánh xạ pictures
            String picturesConcat = (String) result[14];
            List<String> pictures = new ArrayList<>();
            if (picturesConcat != null) {
                pictures = Arrays.asList(picturesConcat.split("\\|"));
            }
            user.setPictures(pictures);

            suggestedUsers.add(user);
        }
        return suggestedUsers;
    }
}
