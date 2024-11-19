package com.noxinfinity.pdating.Applications.User;

import com.noxinfinity.pdating.Domains.UserDataManagement.UserData.IUserDataService;
import com.noxinfinity.pdating.graphql.types.StatusEnum;
import com.noxinfinity.pdating.graphql.types.UserData;
import com.noxinfinity.pdating.graphql.types.UserInfoSuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserQuery implements IUserQuery {

    private final IUserDataService userDataService;

    @Autowired
    public UserQuery(IUserDataService userDataService) {
        this.userDataService = userDataService;
    }

    public UserInfoSuccessResponse getUserInfoById(String id) {
        UserData userData = userDataService.getUserDataById(id);
        return new UserInfoSuccessResponse.Builder().message("Success").status(StatusEnum.SUCCESS).data(userData).build();
    }
}
