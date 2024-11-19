package com.noxinfinity.pdating.Domains.UserDataManagement.UserData;

import com.noxinfinity.pdating.graphql.types.UserData;
import com.noxinfinity.pdating.graphql.types.UserFromGoogle;

public interface IUserDataService {
    Boolean createOrUpdateUserDataFromGoogleReturnIsNew(UserFromGoogle user);
    UserData getUserDataById(String id);
}
