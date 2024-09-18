package com.noxinfinity.pdating.Primary.AddToTestUser;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import com.noxinfinity.pdating.Applications.User.AddTester.Services.AddTesterService;
import org.springframework.beans.factory.annotation.Autowired;

@DgsComponent
public class AddToTestUserMutation {
    private final AddTesterService addTesterService;
    @Autowired
    public AddToTestUserMutation(AddTesterService addTesterService) {
        this.addTesterService = addTesterService;
    }

    @DgsMutation
    public Response addToTestUser(@InputArgument String name){
        this.addTesterService.AddTesterService(name);
        return new Response(200,"Ban khong phai quan dao xin chao ban");
    }

}
