package com.noxinfinity.pdating.Domains.UserManagement;

import com.noxinfinity.pdating.Entities.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, String> {
    @Query(value = "SELECT u.fcm_id, u.fullname, u.dob, u.avatar_url, u.gender, u.grade_id, g.name AS grade_name, u.major_id, m.name AS major_name, m.icon_url, u.bio, " +
            "       (6371 * acos(cos(radians(:currentLat)) * cos(radians(ul.lat)) " +
            "       * cos(radians(ul.lng) - radians(:currentLng)) + sin(radians(:currentLat)) * sin(radians(ul.lat)))) AS distance, " +
            "       GROUP_CONCAT(DISTINCT CASE WHEN uh.hobby_id IN (SELECT hobby_id FROM user_hobbies WHERE fcm_id = :currentUserId) THEN CONCAT(h.id, ':', h.title, ':', h.icon_url) END SEPARATOR '|') AS common_hobbies, " +
            "       GROUP_CONCAT(DISTINCT CONCAT(h.id, ':', h.title, ':', h.icon_url) SEPARATOR '|') AS all_hobbies, " +
            "       GROUP_CONCAT(DISTINCT p.title SEPARATOR '|') AS purposes, " +
            "       GROUP_CONCAT(DISTINCT upic.image_url SEPARATOR '|') AS user_pics " +
            "FROM user_data u " +
            "JOIN user_location ul ON u.fcm_id = ul.fcm_id " +
            "LEFT JOIN grades g ON u.grade_id = g.id " +
            "LEFT JOIN majors m ON u.major_id = m.id " +
            "LEFT JOIN user_hobbies uh ON u.fcm_id = uh.fcm_id " +
            "LEFT JOIN hobbies h ON uh.hobby_id = h.id " +
            "LEFT JOIN user_purposes up ON u.fcm_id = up.user_id " +
            "LEFT JOIN purpose p ON up.purpose_id = p.id " +
            "LEFT JOIN user_pics upic ON u.fcm_id = upic.fcm_id " +
            "WHERE u.fcm_id != :currentUserId " +
            "  AND u.is_activated = 1 " +
            "  AND NOT EXISTS (SELECT 1 FROM user_likes ul " +
            "                  WHERE ul.current_user_id = :currentUserId AND ul.target_user_id = u.fcm_id) " +
            "  AND NOT EXISTS (SELECT 1 FROM user_dont_cared udc " +
            "                  WHERE udc.user_id = :currentUserId AND udc.dont_care_user_id = u.fcm_id) " +
            "GROUP BY u.fcm_id, ul.lat, ul.lng, g.name, m.name, m.icon_url " +
            "ORDER BY " +
            "   distance " +
            "LIMIT 10 OFFSET :offset", nativeQuery = true)
    List<Object[]> findSuggestedUsers(
            @Param("currentUserId") String currentUserId,
            @Param("currentLat") double currentLat,
            @Param("currentLng") double currentLng,
            @Param("offset") int offset
    );

    @Query(value = "SELECT u.fcm_id, u.fullname, u.dob, u.avatar_url, u.gender, u.grade_id, g.name AS grade_name, u.major_id, m.name AS major_name, m.icon_url, u.bio, " +
            "       (6371 * acos(cos(radians(:currentLat)) * cos(radians(ul.lat)) " +
            "       * cos(radians(ul.lng) - radians(:currentLng)) + sin(radians(:currentLat)) * sin(radians(ul.lat)))) AS distance, " +
            "       GROUP_CONCAT(DISTINCT CASE WHEN uh.hobby_id IN (SELECT hobby_id FROM user_hobbies WHERE fcm_id = :currentUserId) THEN CONCAT(h.id, ':', h.title, ':', h.icon_url) END SEPARATOR '|') AS common_hobbies, " +
            "       GROUP_CONCAT(DISTINCT CONCAT(h.id, ':', h.title, ':', h.icon_url) SEPARATOR '|') AS all_hobbies, " +
            "       GROUP_CONCAT(DISTINCT p.title SEPARATOR '|') AS purposes, " +
            "       GROUP_CONCAT(DISTINCT upic.image_url SEPARATOR '|') AS user_pics " +
            "FROM user_data u " +
            "JOIN user_location ul ON u.fcm_id = ul.fcm_id " +
            "LEFT JOIN grades g ON u.grade_id = g.id " +
            "LEFT JOIN majors m ON u.major_id = m.id " +
            "LEFT JOIN user_hobbies uh ON u.fcm_id = uh.fcm_id " +
            "LEFT JOIN hobbies h ON uh.hobby_id = h.id " +
            "LEFT JOIN user_purposes up ON u.fcm_id = up.user_id " +
            "LEFT JOIN purpose p ON up.purpose_id = p.id " +
            "LEFT JOIN user_pics upic ON u.fcm_id = upic.fcm_id " +
            "WHERE u.fcm_id != :currentUserId " +
            "  AND u.is_activated = 1 " +
            "  AND NOT EXISTS (SELECT 1 FROM user_likes ul " +
            "                  WHERE ul.current_user_id = :currentUserId AND ul.target_user_id = u.fcm_id) " +
            "  AND NOT EXISTS (SELECT 1 FROM user_dont_cared udc " +
            "                  WHERE udc.user_id = :currentUserId AND udc.dont_care_user_id = u.fcm_id) " +
            "  AND EXISTS (SELECT 1 FROM user_purposes up_match " +
            "              JOIN purpose p_match ON up_match.purpose_id = p_match.id " +
            "              WHERE up_match.user_id = :currentUserId AND p_match.title = :purpose AND up_match.purpose_id = up.purpose_id) " +
            "GROUP BY u.fcm_id, ul.lat, ul.lng, g.name, m.name, m.icon_url " +
            "ORDER BY " +
            "   distance " +
            "LIMIT 10 OFFSET :offset", nativeQuery = true)
    List<Object[]> findSuggestedUsersByPurpose(
            @Param("currentUserId") String currentUserId,
            @Param("currentLat") double currentLat,
            @Param("currentLng") double currentLng,
            @Param("offset") int offset,
            @Param("purpose") String purpose
    );

    @Query(value = "SELECT u.fcm_id, u.fullname, u.dob, u.avatar_url, u.gender, u.grade_id, g.name AS grade_name, u.major_id, m.name AS major_name, m.icon_url, u.bio, " +
            "       (6371 * acos(cos(radians(:currentLat)) * cos(radians(ul.lat)) " +
            "       * cos(radians(ul.lng) - radians(:currentLng)) + sin(radians(:currentLat)) * sin(radians(ul.lat)))) AS distance, " +
            "       GROUP_CONCAT(DISTINCT CASE WHEN uh.hobby_id IN (SELECT hobby_id FROM user_hobbies WHERE fcm_id = :currentUserId) THEN CONCAT(h.id, ':', h.title, ':', h.icon_url) END SEPARATOR '|') AS common_hobbies, " +
            "       GROUP_CONCAT(DISTINCT CONCAT(h.id, ':', h.title, ':', h.icon_url) SEPARATOR '|') AS all_hobbies, " +
            "       GROUP_CONCAT(DISTINCT p.title SEPARATOR '|') AS purposes, " +
            "       GROUP_CONCAT(DISTINCT upic.image_url SEPARATOR '|') AS user_pics " +
            "FROM user_data u " +
            "JOIN user_location ul ON u.fcm_id = ul.fcm_id " +
            "LEFT JOIN grades g ON u.grade_id = g.id " +
            "LEFT JOIN majors m ON u.major_id = m.id " +
            "LEFT JOIN user_hobbies uh ON u.fcm_id = uh.fcm_id " +
            "LEFT JOIN hobbies h ON uh.hobby_id = h.id " +
            "LEFT JOIN user_purposes up ON u.fcm_id = up.user_id " +
            "LEFT JOIN purpose p ON up.purpose_id = p.id " +
            "LEFT JOIN user_pics upic ON u.fcm_id = upic.fcm_id " +
            "WHERE u.fcm_id != :currentUserId " +
            "  AND NOT EXISTS (SELECT 1 FROM user_likes ul " +
            "                  WHERE ul.current_user_id = :currentUserId AND ul.target_user_id = u.fcm_id) " +
            "  AND NOT EXISTS (SELECT 1 FROM user_dont_cared udc " +
            "                  WHERE udc.user_id = :currentUserId AND udc.dont_care_user_id = u.fcm_id) " +
            "   AND u.major_id = :majorId " +
            "   AND u.is_activated = 1 " +
            "GROUP BY u.fcm_id, ul.lat, ul.lng, g.name, m.name, m.icon_url " +
            "ORDER BY " +
            "   (CASE WHEN COUNT(uh_match.hobby_id) > 0 THEN 0 ELSE 1 END), " +
            "   distance " +
            "LIMIT 10 OFFSET :offset", nativeQuery = true)
    List<Object[]> findSuggestedUsersByMajor(
            @Param("currentUserId") String currentUserId,
            @Param("currentLat") double currentLat,
            @Param("currentLng") double currentLng,
            @Param("offset") int offset,
            @Param("majorId") long majorId
    );

    @Query(value = "SELECT u.fcm_id, u.fullname, u.dob, u.avatar_url, u.gender, u.grade_id, g.name AS grade_name, u.major_id, m.name AS major_name, m.icon_url, u.bio, " +
            "       (6371 * acos(cos(radians(:currentLat)) * cos(radians(ul.lat)) " +
            "       * cos(radians(ul.lng) - radians(:currentLng)) + sin(radians(:currentLat)) * sin(radians(ul.lat)))) AS distance, " +
            "       GROUP_CONCAT(DISTINCT CASE WHEN uh.hobby_id IN (SELECT hobby_id FROM user_hobbies WHERE fcm_id = :currentUserId) THEN CONCAT(h.id, ':', h.title, ':', h.icon_url) END SEPARATOR '|') AS common_hobbies, " +
            "       GROUP_CONCAT(DISTINCT CONCAT(h.id, ':', h.title, ':', h.icon_url) SEPARATOR '|') AS all_hobbies, " +
            "       GROUP_CONCAT(DISTINCT p.title SEPARATOR '|') AS purposes, " +
            "       GROUP_CONCAT(DISTINCT upic.image_url SEPARATOR '|') AS user_pics " +
            "FROM user_data u " +
            "JOIN user_location ul ON u.fcm_id = ul.fcm_id " +
            "LEFT JOIN grades g ON u.grade_id = g.id " +
            "LEFT JOIN majors m ON u.major_id = m.id " +
            "LEFT JOIN user_hobbies uh ON u.fcm_id = uh.fcm_id " +
            "LEFT JOIN hobbies h ON uh.hobby_id = h.id " +
            "LEFT JOIN user_purposes up ON u.fcm_id = up.user_id " +
            "LEFT JOIN purpose p ON up.purpose_id = p.id " +
            "LEFT JOIN user_pics upic ON u.fcm_id = upic.fcm_id " +
            "WHERE u.fcm_id != :currentUserId " +
            "   AND u.is_activated = 1 " +
            "  AND NOT EXISTS (SELECT 1 FROM user_likes ul " +
            "                  WHERE ul.current_user_id = :currentUserId AND ul.target_user_id = u.fcm_id) " +
            "  AND NOT EXISTS (SELECT 1 FROM user_dont_cared udc " +
            "                  WHERE udc.user_id = :currentUserId AND udc.dont_care_user_id = u.fcm_id) " +
            "   AND distance < 50000 " +
            "GROUP BY u.fcm_id, ul.lat, ul.lng, g.name, m.name, m.icon_url " +
            "ORDER BY " +
            "   distance " +
            "LIMIT 10 OFFSET :offset", nativeQuery = true)
    List<Object[]> findSuggestedUsersNearBy(
            @Param("currentUserId") String currentUserId,
            @Param("currentLat") double currentLat,
            @Param("currentLng") double currentLng,
            @Param("offset") int offset
    );

}
