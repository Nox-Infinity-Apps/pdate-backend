package com.noxinfinity.pdating.Primary.HelloWorld;

import com.noxinfinity.pdating.Applications.User.SayHello.Services.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Arguments;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class HelloWorldQuery {
    final HelloWorldService helloWorldService;

    @Autowired
    public HelloWorldQuery(HelloWorldService helloWorldService){
        this.helloWorldService = helloWorldService;
    }

    @QueryMapping
    public String helloWorld(@Arguments HelloWorldDTO input2) {
        return helloWorldService.helloWorld(input2.name());
    }
}
