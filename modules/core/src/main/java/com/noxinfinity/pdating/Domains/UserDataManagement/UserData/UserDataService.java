package com.noxinfinity.pdating.Domains.UserDataManagement.UserData;

import com.noxinfinity.pdating.Entities.*;
import com.noxinfinity.pdating.Entities.Enums.AuthProvider;
import com.noxinfinity.pdating.Entities.Hobbies;
import com.noxinfinity.pdating.Entities.UserData;
import com.noxinfinity.pdating.Repository.Auth.IAuthRespository;
import com.noxinfinity.pdating.Repository.Other.*;
import com.noxinfinity.pdating.Repository.UserData.IUserDataRepository;
import com.noxinfinity.pdating.ThirdServices.StreamChat;
import com.noxinfinity.pdating.graphql.types.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDataService implements IUserDataService {
    private final IUserDataRepository _user;
    private final IAuthRespository _auth;
    private final IGradeRespository _grade;
    private final IMajorRespository _major;
    private final IHobbiesRespository _hobbies;
    private final IUserLocationRepository _location;
    private final IPurpose _purpose;


    @Autowired
    public UserDataService(IUserDataRepository userDataRepository,
                           IAuthRespository authRepository ,
                           IGradeRespository gradeRespository ,
                           IMajorRespository majorRespository,
                           IHobbiesRespository hobbiesRespository,
                            IUserLocationRepository locationRepository,
                            IPurpose purposeRepository,
                           StreamChat streamChat) {
        this._user = userDataRepository;
        this._auth = authRepository;
        this._grade = gradeRespository;
        this._major = majorRespository;
        this._hobbies = hobbiesRespository;
        this._location = locationRepository;
        this._purpose = purposeRepository;
    }
    public Boolean createOrUpdateUserDataFromGoogleReturnIsNew(UserFromGoogle user)  {
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

            // Picture
            userData.setPictures(user.getUserPics() != null ?
                    user.getUserPics().stream()
                            .map(picture -> new Pics.Builder()
                                    .id(picture.getId() != null ? Math.toIntExact(picture.getId()) : null)
                                    .url(picture.getImageUrl() != null ? picture.getImageUrl() : "")
                                    .build())
                            .collect(Collectors.toList())
                    : null);
            return userData;
        } else {
            return null;
        }
    }

    @Override
    public UserData updateUserDataById(String id, UpdateUserInfo body) throws Exception {
        // Check if `id` or `body` is null
        if (id == null || body == null) {
            throw new Exception("Invalid input: ID or request body is null.");
        }

        // Find the user by ID
        UserData user = _user.findById(id).orElse(null);
        if (user == null) {
            throw new Exception("Không tồn tại user");
        }

        // Update basic information with null checks
        if (body.getAvatar() != null) {
            user.setAvatarUrl(body.getAvatar());
        }
        if (body.getBio() != null) {
            user.setBio(body.getBio());
        }
        if (body.getDob() != null) {
            user.setDob(new SimpleDateFormat("dd/MM/yyyy").parse(body.getDob()));
        }
        if (body.getFullName() != null) {
            user.setFullName(body.getFullName());
        }
        if (body.getGrade() != null) {
            user.setGrade(_grade.findById(Long.valueOf(body.getGrade())).orElse(null));
        }
        if (body.getMajor() != null) {
            user.setMajor(_major.findById(Long.valueOf(body.getMajor())).orElse(null));
        }

        // Update auth details with null checks
        Auth auth = user.getAuth();
        if (auth != null) {
            if (body.getFcmNotificationToken() != null) {
                auth.setFcmNotificationToken(body.getFcmNotificationToken());
            }
            if (body.getEmail() != null) {
                auth.setEmail(body.getEmail());
            }
            if (body.getProvider() != null) {
                auth.setProvider(AuthProvider.valueOf(body.getProvider().toUpperCase()));
            }
            _auth.save(auth);
        }

        // Update location with null checks
        if (body.getLocation() != null) {
            UserLocation location = user.getLocation();
            if (location == null) {
                location = new UserLocation();
            }
            if (body.getLocation().getLat() != null) {
                location.setLat(body.getLocation().getLat());
            }
            if (body.getLocation().getLng() != null) {
                location.setLng(body.getLocation().getLng());
            }
            location.setUserData(user);
            user.setLocation(location);
        }

        // Update hobbies with null checks
        if (body.getHobbies() != null) {
            List<Long> hobbyIds = body.getHobbies().stream()
                    .map(Integer::longValue)
                    .toList(); // List of IDs from body
            List<Hobbies> hobbiesFromDb = _hobbies.findAllById(hobbyIds);
            if (hobbiesFromDb.size() != hobbyIds.size()) {
                throw new Exception("Sở thích không tồn tại trong danh sách.");
            }

            // Clear current hobbies and add new ones
            List<UserHobbies> currentHobbies = user.getHobbies();
            currentHobbies.clear();

            for (Hobbies hobby : hobbiesFromDb) {
                UserHobbies userHobby = new UserHobbies();
                userHobby.setUserData(user);
                userHobby.setHobbies(hobby);
                currentHobbies.add(userHobby);
            }

            user.setHobbies(currentHobbies);
        }

        // Update other fields with null checks
        user.setIsActivated(1); // Assuming activation is mandatory
        user.setGender(body.getGender() != null ?
                com.noxinfinity.pdating.Entities.Enums.Gender.fromValue(body.getGender().toString()) :
                com.noxinfinity.pdating.Entities.Enums.Gender.OTHER);
        user.setUpdatedAt(new java.util.Date());

        // Save updated user data
        _user.save(user);

        return user;
    }

    @Override
    public com.noxinfinity.pdating.graphql.types.UserData updateFcmTokenAndLocation(String id, UpdateFcmTokenAndLocation input) throws Exception {
        com.noxinfinity.pdating.graphql.types.UserData userData = new com.noxinfinity.pdating.graphql.types.UserData();
        UserData user = _user.findById(id).orElse(null);
        if (user == null) {
            throw new Exception("Không tồn tại user");
        } else {
            if(input.getFcmNotificationToken() != null){
                // Cập nhật Auth
                Auth auth = user.getAuth();
                auth.setFcmNotificationToken(input.getFcmNotificationToken());
                _auth.save(auth);
            }
            if(input.getLocation() != null){
                // Cập nhật Location
                UserLocation location = user.getLocation();
                if (location == null) {
                    location = new UserLocation();
                }
                location.setLat(input.getLocation().getLat());
                location.setLng(input.getLocation().getLng());
                location.setUserData(user);
                user.setLocation(location);
            }
            // Lưu UserData
            _user.save(user);
            return null;
        }
    }

    @Override
    public List<PurposeResponse> getAllPurpose() throws Exception {
        List<Purpose> purposes = _purpose.findAll();
        return purposes.stream()
                .map(purpose -> new PurposeResponse.Builder()
                        .id(purpose.getId() != null ? Math.toIntExact(purpose.getId()) : null)
                        .name(purpose.getTitle() != null ? purpose.getTitle() : "")
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<PurposeResponse> updateUserPurpose(String id, List<Integer> purposeIds) throws Exception {
        UserData user = _user.findById(id).orElse(null);
        List<Long> idPurposes = purposeIds.stream().map(Long::valueOf).collect(Collectors.toList());
        if (user == null) {
            throw new Exception("Không tồn tại user");
        }
        List<Purpose> purposes = _purpose.findAllById(idPurposes);
        if (purposes.size() != purposeIds.size()) {
            throw new Exception("Mục tiêu không tồn tại trong danh sách.");
        }
        user.setPurposes(purposes);
        _user.save(user);
        return null;
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
