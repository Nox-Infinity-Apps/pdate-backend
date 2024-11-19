package com.noxinfinity.pdating.Domains.UserDataManagement.UserData;

import com.noxinfinity.pdating.Entities.Auth;
import com.noxinfinity.pdating.Entities.Enums.AuthProvider;
import com.noxinfinity.pdating.Entities.UserData;
import com.noxinfinity.pdating.Entities.UserHobbies;
import com.noxinfinity.pdating.Entities.UserPics;
import com.noxinfinity.pdating.Repository.Auth.IAuthRespository;
import com.noxinfinity.pdating.Repository.UserData.IUserDataRepository;
import com.noxinfinity.pdating.graphql.types.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserDataService implements IUserDataService {
    private final IUserDataRepository _user;
    private final IAuthRespository _auth;

    @Autowired
    public UserDataService(IUserDataRepository userDataRepository, IAuthRespository authRepository) {
        this._user = userDataRepository;
        this._auth = authRepository;
    }
    public Boolean createOrUpdateUserDataFromGoogleReturnIsNew(UserFromGoogle user) {
        //Tìm xem user đã tồn tại chưa
        UserData userData = _user.findById(user.getFcm_id()).orElse(null);
        if (userData == null) {
            // Nếu chưa tồn tại thì tạo mới
            userData = new UserData();
            userData.setUserId(user.getFcm_id());
            userData.setFullName(user.getFullName());
            userData.setAvatarUrl(user.getAvatar());
            _user.save(userData);

            Auth auth = new Auth();
            auth.setFcmId(user.getFcm_id());
            auth.setEmail(user.getEmail());
            auth.setProvider(AuthProvider.valueOf(user.getProvider().toUpperCase()));
            auth.setUserData(userData);
            _auth.save(auth);
            return true;
        } else {
            // Nếu đã tồn tại thì cập nhật thông tin
            userData.setFullName(user.getFullName());
            userData.setAvatarUrl(user.getAvatar());
            _user.save(userData);

            Auth auth = _auth.findById(user.getFcm_id()).orElse(null);
            if (auth == null) {
                auth = new Auth();
                auth.setFcmId(user.getFcm_id());
                auth.setEmail(user.getEmail());
                auth.setProvider(AuthProvider.valueOf(user.getProvider().toUpperCase()));
                auth.setUserData(userData);
                _auth.save(auth);
            } else {
                auth.setEmail(user.getEmail());
                auth.setProvider(AuthProvider.valueOf(user.getProvider().toUpperCase()));
                auth.setUserData(userData);
                _auth.save(auth);
            }
            return false;
        }
    }

    public com.noxinfinity.pdating.graphql.types.UserData getUserDataById(String id) {
        com.noxinfinity.pdating.graphql.types.UserData userData = new com.noxinfinity.pdating.graphql.types.UserData();
        UserData user = _user.findById(id).orElse(null);
        if (user != null) {
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
        } else {
            return null;
        }
    }


    private List<Hobbies> mapUserHobbiesToHobbies(List<UserHobbies> userHobbiesList) {
        return userHobbiesList.stream()
                .map(userHobby -> new Hobbies.Builder()
                        .id(Math.toIntExact(userHobby.getHobbies().getId()))
                        .title(userHobby.getHobbies().getTitle())
                        .iconUrl(userHobby.getHobbies().getIconUrl())
                        .build())
                .collect(Collectors.toList());
    }

}
