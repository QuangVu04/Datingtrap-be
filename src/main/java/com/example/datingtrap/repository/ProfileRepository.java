package com.example.datingtrap.repository;

import com.example.datingtrap.entity.Profiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ProfileRepository extends JpaRepository<Profiles, Long> {
}