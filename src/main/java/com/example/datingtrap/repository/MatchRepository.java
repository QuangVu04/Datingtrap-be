package com.example.datingtrap.repository;

import com.example.datingtrap.dto.ListMatchConvo;
import com.example.datingtrap.entity.Matches;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Matches,Long> {
    @Query(
            value = "SELECT m.id AS matchId, " +
                    "u.id AS partnerId, u.username AS partnerUsername, " +
                    "p.avatar_url AS partnerAvatarUrl, " +
                    "msg.message AS lastMessage, msg.created_at AS lastMessageTime " +
                    "FROM matches m " +
                    "LEFT JOIN messages msg ON m.id = msg.match_id AND msg.created_at = (" +
                    "  SELECT MAX(m2.created_at) FROM messages m2 WHERE m2.match_id = m.id" +
                    ") " +
                    "JOIN users u ON (m.user1_id = :userId AND u.id = m.user2_id) " +
                    "OR (m.user2_id = :userId AND u.id = m.user1_id) " +
                    "JOIN profiles p ON p.user_id = u.id " +
                    "WHERE m.user1_id = :userId OR m.user2_id = :userId " +
                    "ORDER BY COALESCE(msg.created_at, m.created_at) DESC " +
                    "LIMIT :limit OFFSET :offset",
            nativeQuery = true
    )
    List<ListMatchConvo> findMatchesByUserPaged(
            @Param("userId") Long userId,
            @Param("limit") int limit,
            @Param("offset") int offset
    );
}
