package com.noxinfinity.pdating.Applications.User.AddTester.Services;

import com.noxinfinity.pdating.Domains.TesterManagement.Services.TestManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddTesterService {
    private TestManagementService testManagementService;
    @Autowired
    public AddTesterService(TestManagementService testManagementService) {
        this.testManagementService = testManagementService;
    }
    public void AddTesterService(String name) {
        if(name != "QuanDao"){
            this.testManagementService.addNewTesterUser(name);
        }
    }
}
