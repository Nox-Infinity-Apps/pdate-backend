package com.noxinfinity.pdating.Domains.UserManagement;

import com.noxinfinity.pdating.Entities.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, String> {
    @Query(value = "SELECT u.fcm_id, u.fullname, u.dob, u.avatar_url, u.grade_id, g.name AS grade_name, u.major_id, m.name AS major_name, m.icon_url, u.bio, " +
            "       (6371 * acos(cos(radians(:currentLat)) * cos(radians(ul.lat)) " +
            "       * cos(radians(ul.lng) - radians(:currentLng)) + sin(radians(:currentLat)) * sin(radians(ul.lat)))) AS distance, " +
            "       GROUP_CONCAT(h.id, ':', h.title, ':', h.icon_url SEPARATOR '|') AS common_hobbies " +
            "FROM user_data u " +
            "JOIN user_location ul ON u.fcm_id = ul.fcm_id " +
            "LEFT JOIN grades g ON u.grade_id = g.id " +
            "LEFT JOIN majors m ON u.major_id = m.id " +
            "JOIN user_hobbies uh ON u.fcm_id = uh.fcm_id " +
            "JOIN hobbies h ON uh.hobby_id = h.id " +
            "WHERE u.fcm_id != :currentUserId " +
            "  AND uh.hobby_id IN (SELECT uh1.hobby_id FROM user_hobbies uh1 WHERE uh1.fcm_id = :currentUserId) " +
            "GROUP BY u.fcm_id, ul.lat, ul.lng, g.name, m.name, m.icon_url " +
            "ORDER BY distance " +
            "LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Object[]> findSuggestedUsers(
            @Param("currentUserId") String currentUserId,
            @Param("currentLat") double currentLat,
            @Param("currentLng") double currentLng,
            @Param("limit") int limit,
            @Param("offset") int offset
    );
}
