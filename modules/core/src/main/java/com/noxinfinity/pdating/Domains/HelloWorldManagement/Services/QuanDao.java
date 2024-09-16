package com.noxinfinity.pdating.Domains.HelloWorldManagement.Services;

import org.springframework.stereotype.Service;

@Service
public class QuanDao {
    public String bark(String name) {
        return "Quan Dao da say hello to "+name+"!";
    }
}
