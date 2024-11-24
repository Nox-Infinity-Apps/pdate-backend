package com.noxinfinity.pdating.Domains.UserDataManagement.UserData;

import com.noxinfinity.pdating.Entities.*;
import com.noxinfinity.pdating.Entities.Enums.AuthProvider;
import com.noxinfinity.pdating.Entities.Hobbies;
import com.noxinfinity.pdating.Entities.UserData;
import com.noxinfinity.pdating.Repository.Auth.IAuthRespository;
import com.noxinfinity.pdating.Repository.Other.IGradeRespository;
import com.noxinfinity.pdating.Repository.Other.IHobbiesRespository;
import com.noxinfinity.pdating.Repository.Other.IMajorRespository;
import com.noxinfinity.pdating.Repository.UserData.IUserDataRepository;
import com.noxinfinity.pdating.graphql.types.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserDataService implements IUserDataService {
    private final IUserDataRepository _user;
    private final IAuthRespository _auth;
    private final IGradeRespository _grade;
    private final IMajorRespository _major;
    private final IHobbiesRespository _hobbies;

    @Autowired
    public UserDataService(IUserDataRepository userDataRepository, IAuthRespository authRepository , IGradeRespository gradeRespository , IMajorRespository majorRespository, IHobbiesRespository hobbiesRespository) {
        this._user = userDataRepository;
        this._auth = authRepository;
        this._grade = gradeRespository;
        this._major = majorRespository;
        this._hobbies = hobbiesRespository;
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

    @Override
    public com.noxinfinity.pdating.graphql.types.UserData updateUserDataById(String id, UpdateUserInfo body) throws Exception {
        com.noxinfinity.pdating.graphql.types.UserData userData = new com.noxinfinity.pdating.graphql.types.UserData();
        UserData user = _user.findById(id).orElse(null);
        if (user == null) {
            throw new Exception("Không tồn tại user");
        } else {
            // Cập nhật thông tin cơ bản
            user.setAvatarUrl(body.getAvatar());
            user.setBio(body.getBio());
            user.setDob(new SimpleDateFormat("dd/MM/yyyy").parse(body.getDob()));
            user.setFullName(body.getFullName());
            user.setGrade(_grade.findById(Long.valueOf(body.getGrade())).orElse(null));
            user.setMajor(_major.findById(Long.valueOf(body.getMajor())).orElse(null));

            // Cập nhật Auth
            Auth auth = user.getAuth();
            auth.setFcmNotificationToken(body.getFcmNotificationToken());
            auth.setEmail(body.getEmail());
            auth.setProvider(AuthProvider.valueOf(body.getProvider().toUpperCase()));
            _auth.save(auth);

            // Cập nhật Location
            UserLocation location = new UserLocation();
            location.setLat(body.getLocation().getLat());
            location.setLng(body.getLocation().getLng());
            user.setLocation(location);

            // Cập nhật Hobbies
            List<Long> hobbyIds = body.getHobbies().stream()
                    .map(Integer::longValue)
                    .toList();; // Danh sách id từ body
            List<Hobbies> hobbiesFromDb = _hobbies.findAllById(hobbyIds); // Lấy danh sách hobbies từ DB
            if (hobbiesFromDb.size() != hobbyIds.size()) {
                throw new Exception("Sở thích không tồn tại trong danh sách.");
            }

            // Xóa các hobbies cũ
            List<UserHobbies> currentHobbies = user.getHobbies();
            currentHobbies.clear();

            // Thêm hobbies mới
            for (Hobbies hobby : hobbiesFromDb) {
                UserHobbies userHobby = new UserHobbies();
                userHobby.setUserData(user);
                userHobby.setHobbies(hobby);
                currentHobbies.add(userHobby);
            }

            user.setHobbies(currentHobbies);

            // Lưu UserData
            _user.save(user);
            return null;
        }
    }



    private List<com.noxinfinity.pdating.graphql.types.Hobbies> mapUserHobbiesToHobbies(List<UserHobbies> userHobbiesList) {
        return userHobbiesList.stream()
                .map(userHobby -> new com.noxinfinity.pdating.graphql.types.Hobbies.Builder()
                        .id(Math.toIntExact(userHobby.getHobbies().getId()))
                        .title(userHobby.getHobbies().getTitle())
                        .iconUrl(userHobby.getHobbies().getIconUrl())
                        .build())
                .collect(Collectors.toList());
    }

}
