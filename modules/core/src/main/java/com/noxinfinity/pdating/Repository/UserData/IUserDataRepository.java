package com.noxinfinity.pdating.Repository.UserData;

import com.noxinfinity.pdating.Entities.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserDataRepository extends JpaRepository<UserData, String> {
}
