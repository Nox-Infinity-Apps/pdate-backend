package com.noxinfinity.pdating.Domains.MatchManagement;

import com.noxinfinity.pdating.Entities.UserMatched;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMatchRepository  extends JpaRepository<UserMatched, Long> {
}
