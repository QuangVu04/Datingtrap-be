package com.example.datingtrap.repository;

import com.example.datingtrap.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.id NOT IN :userIds")
    List<User> findByIdNotIn(@Param("userIds") List<Long> userIds);

    Optional<User> findByFireBase(String fireBase);

}
