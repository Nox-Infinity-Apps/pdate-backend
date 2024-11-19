package com.noxinfinity.pdating.Repository.Auth;

import com.noxinfinity.pdating.Entities.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuthRespository extends JpaRepository<Auth, String> {
}
