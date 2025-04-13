package com.example.datingtrap.repository;

import com.example.datingtrap.entity.UserHobby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserHobbyRepository extends JpaRepository<UserHobby, Long> {
    void deleteByUserId(Long userId);
}
