package com.noxinfinity.pdating.Applications.User.SayHello.Services;

import com.noxinfinity.pdating.Domains.HelloWorldManagement.Services.QuanDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloWorldService {

    QuanDao quanDao;

    @Autowired
    public HelloWorldService(QuanDao quandao){
        this.quanDao = quandao;
    }

    public String helloWorld(String name) {
        return quanDao.bark(name);
    }
}
