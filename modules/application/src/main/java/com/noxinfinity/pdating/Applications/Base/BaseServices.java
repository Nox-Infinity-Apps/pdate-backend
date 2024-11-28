package com.noxinfinity.pdating.Applications.Base;

import com.noxinfinity.pdating.Entities.UserHobbies;
import com.noxinfinity.pdating.graphql.types.*;
import org.apache.catalina.User;

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
            user.setGender(result[4] != null ? Gender.valueOf((String) result[4]) : Gender.OTHER);

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

            // Ánh xạ commonHobbies
            String commonHobbiesConcat = (String) result[12];
            if (commonHobbiesConcat != null) {
                List<Hobbie> commonHobbies = Arrays.stream(commonHobbiesConcat.split("\\|"))
                        .map(hobbyStr -> {
                            String[] hobbyDetails = hobbyStr.split(":");
                            Hobbie hobby = new Hobbie();
                            hobby.setId(Integer.parseInt(hobbyDetails[0].trim()));
                            hobby.setTitle(hobbyDetails[1].trim());

                            StringBuilder url = new StringBuilder();
                            for(int i = 2; i < hobbyDetails.length; i++) {
                                url.append(hobbyDetails[i].trim());
                            }
                            hobby.setIconUrl(url.toString());
                            return hobby;
                        })
                        .collect(Collectors.toList());
                user.setCommonHobbies(commonHobbies);
            } else {
                user.setCommonHobbies(new ArrayList<>());
            }

            // Ánh xạ allHobbies
            String allHobbiesConcat = (String) result[13];
            if (allHobbiesConcat != null) {
                List<Hobbie> allHobbies = Arrays.stream(allHobbiesConcat.split("\\|"))
                        .map(hobbyStr -> {
                            String[] hobbyDetails = hobbyStr.split(":");
                            Hobbie hobby = new Hobbie();
                            hobby.setId(Integer.parseInt(hobbyDetails[0].trim()));
                            hobby.setTitle(hobbyDetails[1].trim());
                            StringBuilder url = new StringBuilder();
                            for(int i = 2; i < hobbyDetails.length; i++) {
                                url.append(hobbyDetails[i].trim());
                            }
                            hobby.setIconUrl(url.toString());
                            return hobby;
                        })
                        .collect(Collectors.toList());
                user.setAllHobbies(allHobbies);
            } else {
                user.setAllHobbies(new ArrayList<>());
            }

            // Ánh xạ purposes
            String purposesConcat = (String) result[14];
            if (purposesConcat != null) {
                user.setPurpose(Arrays.asList(purposesConcat.split("\\|")));
            } else {
                user.setPurpose(new ArrayList<>());
            }

            // Ánh xạ pictures
            String picturesConcat = (String) result[15];
            if (picturesConcat != null) {
                user.setPictures(Arrays.asList(picturesConcat.split("\\|")));
            } else {
                user.setPictures(new ArrayList<>());
            }

            suggestedUsers.add(user);
        }
        return suggestedUsers;
    }

    public static UserData mapUser(com.noxinfinity.pdating.Entities.UserData user){
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

        //Purposes
        userData.setPurpose(user.getPurposes() != null ?
                user.getPurposes().stream()
                        .map(purpose -> new PurposeResponse.Builder()
                                .id(purpose.getId() != null ? Math.toIntExact(purpose.getId()) : null)
                                .name(purpose.getTitle() != null ? purpose.getTitle() : "")
                                .build())
                        .collect(Collectors.toList())
                : null);
        userData.setIsActivated(user.getIsActivated() != null ? user.getIsActivated() : 0);
        userData.setIsVerified(false);
        userData.setGender(user.getGender() != null ? Gender.valueOf(user.getGender().toValue()) : Gender.OTHER);
        return userData;
    }

    public static List<UserData> mapUsers(List<com.noxinfinity.pdating.Entities.UserData> users){
        List<UserData> userDataList = new ArrayList<>();
        for (com.noxinfinity.pdating.Entities.UserData userData : users){
            userDataList.add(mapUser(userData));
        }
        return userDataList;
    }
}
