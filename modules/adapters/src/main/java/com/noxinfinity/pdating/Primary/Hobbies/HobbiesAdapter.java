package com.noxinfinity.pdating.Primary.Hobbies;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.noxinfinity.pdating.Applications.Hobbies.IHobbiesApp;
import com.noxinfinity.pdating.GraphQL.Guard.ValidateToken;
import com.noxinfinity.pdating.graphql.types.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@DgsComponent
public class HobbiesAdapter {
    private final IHobbiesApp hobbiesApp;

    @Autowired
    public HobbiesAdapter(IHobbiesApp hobbiesApp) {
        this.hobbiesApp = hobbiesApp;
    }

    @DgsQuery()
    @ValidateToken
    public HobbiesResponse getAllHobbies() {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            UserFromGoogle decodedToken = (UserFromGoogle) request.getAttribute("decodedToken");
            List<Hobbies> hobbies = hobbiesApp.getAllHobbies();
            return new HobbiesResponse.Builder().message("Success").status(StatusEnum.SUCCESS).data(hobbies).build();
        } catch (Exception e) {
            return new HobbiesResponse.Builder().message(e.getMessage()).status(StatusEnum.FAILED).data(null).build();
        }
    }
}
