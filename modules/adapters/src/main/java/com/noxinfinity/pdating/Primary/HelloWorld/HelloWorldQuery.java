package com.noxinfinity.pdating.Primary.HelloWorld;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.noxinfinity.pdating.Applications.User.SayHello.Services.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Arguments;

@DgsComponent
public class HelloWorldQuery {
    final HelloWorldService helloWorldService;

    @Autowired
    public HelloWorldQuery(HelloWorldService helloWorldService){
        this.helloWorldService = helloWorldService;
    }

    @DgsQuery
    public String helloWorld(@InputArgument(name="input") HelloWorldInput input2) {
        return helloWorldService.helloWorld(input2.name());
    }
}
