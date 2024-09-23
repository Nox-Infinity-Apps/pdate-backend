package com.noxinfinity.pdating.Domains.AuthManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepo extends JpaRepository<Auth, Integer> {
    Auth findByUsername(String username);
    Auth save(Auth auth);
}
