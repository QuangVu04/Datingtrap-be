package com.example.datingtrap.repository;

import com.example.datingtrap.entity.Matches;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Matches,Long> {
}
