package com.example.datingtrap.repository;

import java.util.Optional;
import com.example.datingtrap.entity.Preference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferenceRepository extends JpaRepository<Preference, Long> {
    Optional<Preference> findByUserId(Long userId);
}