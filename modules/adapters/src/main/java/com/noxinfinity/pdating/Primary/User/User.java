package com.noxinfinity.pdating.Primary.User;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.noxinfinity.pdating.Applications.User.IUserQuery;
import com.noxinfinity.pdating.GraphQL.Guard.ValidateToken;
import com.noxinfinity.pdating.graphql.types.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@DgsComponent
public class User {
    private final IUserQuery userQuery;

    @Autowired
    public User(IUserQuery userQuery) {
        this.userQuery = userQuery;
    }

    @DgsQuery()
    @ValidateToken
    public UserInfoResponse getUserInfo() {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            UserFromGoogle decodedToken = (UserFromGoogle) request.getAttribute("decodedToken");
            return userQuery.getUserInfoById(decodedToken.getFcm_id());
        } catch (Exception e) {
            return new UserInfoFailedResponse.Builder().message(e.getMessage()).status(StatusEnum.FAILED).build();
        }
    }
}
