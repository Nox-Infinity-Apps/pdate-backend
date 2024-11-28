package com.noxinfinity.pdating.Domains.UserDataManagement.UserData;

import com.noxinfinity.pdating.graphql.types.*;

import java.util.List;

public interface IUserDataService {
    Boolean createOrUpdateUserDataFromGoogleReturnIsNew(UserFromGoogle user);
    UserData getUserDataById(String id);
    com.noxinfinity.pdating.Entities.UserData updateUserDataById(String id , UpdateUserInfo body) throws Exception;
    UserData updateFcmTokenAndLocation(String id, UpdateFcmTokenAndLocation input) throws Exception;

    //Purpose
    List<PurposeResponse> getAllPurpose() throws Exception;
    List<PurposeResponse> updateUserPurpose(String id, List<Integer> purposeIds) throws Exception;
}
