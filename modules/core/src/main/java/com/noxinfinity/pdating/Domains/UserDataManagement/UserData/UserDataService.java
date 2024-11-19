package com.noxinfinity.pdating.Domains.UserDataManagement.UserData;

import com.noxinfinity.pdating.Entities.Auth;
import com.noxinfinity.pdating.Entities.Enums.AuthProvider;
import com.noxinfinity.pdating.Entities.UserData;
import com.noxinfinity.pdating.Repository.Auth.IAuthRespository;
import com.noxinfinity.pdating.Repository.UserData.IUserDataRepository;
import com.noxinfinity.pdating.graphql.types.UserFromGoogle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
}
