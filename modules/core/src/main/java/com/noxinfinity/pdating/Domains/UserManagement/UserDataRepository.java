package com.noxinfinity.pdating.Domains.UserManagement;

import com.noxinfinity.pdating.Entities.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserDataRepository extends JpaRepository<UserData, String> {

}
