package com.noxinfinity.pdating.Domains.UserDataManagement.UserData;

import com.noxinfinity.pdating.graphql.types.UserFromGoogle;

public interface IUserDataService {
    Boolean createOrUpdateUserDataFromGoogleReturnIsNew(UserFromGoogle user);
}
