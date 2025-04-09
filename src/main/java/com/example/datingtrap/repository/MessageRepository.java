package com.example.datingtrap.repository;

import com.example.datingtrap.entity.Matches;
import com.example.datingtrap.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {
    @Query("SELECT m FROM Message m WHERE m.matches.id = :matchId")
    Page<Message> findMessagesByMatchId(
            @Param("matchId") Long matchId,
            Pageable pageable
    );
}
