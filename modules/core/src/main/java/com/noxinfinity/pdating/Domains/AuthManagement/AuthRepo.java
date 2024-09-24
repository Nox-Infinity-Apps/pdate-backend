package com.noxinfinity.pdating.Domains.AuthManagement;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface AuthRepo extends JpaRepository<Auth, Integer> {
    Auth findByUsername(String username);

    @NotNull Auth save(@NotNull Auth auth);

    Auth findByUserId(UUID userId);

    @Modifying
    @Transactional
    @Query("update Auth a set a.password = ?1 where a.userId = ?2")
    int updatePasswordByUserId(@NotNull String password, @NotNull UUID userId);
}