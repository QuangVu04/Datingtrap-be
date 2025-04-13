package com.example.datingtrap.repository;

import com.example.datingtrap.entity.Hobby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface HobbyRepository extends JpaRepository<Hobby, Long> {

}