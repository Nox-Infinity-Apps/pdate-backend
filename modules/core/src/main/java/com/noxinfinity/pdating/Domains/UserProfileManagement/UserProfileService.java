package com.noxinfinity.pdating.Domains.UserProfileManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {
    private UserProfileRepo userProfileRepo;
    @Autowired
    public UserProfileService(UserProfileRepo userProfileRepo) {
        this.userProfileRepo = userProfileRepo;
    }
    public UserProfile save(UserProfile userProfile) {
        return userProfileRepo.save(userProfile);
    }
}
