package com.noxinfinity.pdating.Domains.UserManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    private final UsersRepo usersRepo;

    @Autowired
    public UsersService(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }
    public Users save(Users user) {
        return usersRepo.save(user);
    }
//    public Users find(String username) {
//
//    }
}
