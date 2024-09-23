package com.noxinfinity.pdating.Domains.UserProfileManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserProfileRepo extends JpaRepository<UserProfile, UUID> {
    UserProfile save(UserProfile userProfile);
}
