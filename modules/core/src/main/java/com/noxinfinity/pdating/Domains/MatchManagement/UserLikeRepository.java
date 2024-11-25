package com.noxinfinity.pdating.Domains.MatchManagement;

import com.noxinfinity.pdating.Entities.UserLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserLikeRepository extends JpaRepository<UserLikes, Long> {
    boolean existsByCurrentUserIdAndTargetUserId(String currentUserId, String targetUserId);

    Optional<UserLikes> findByCurrentUserIdAndTargetUserId(String current, String target);
}
