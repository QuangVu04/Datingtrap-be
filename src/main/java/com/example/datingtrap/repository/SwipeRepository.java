package com.example.datingtrap.repository;

import com.example.datingtrap.entity.Swipe;
import com.example.datingtrap.enums.SwipeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SwipeRepository extends JpaRepository<Swipe,Long> {
    boolean existsBySwiperIdAndSwipedId(Long swiperId, Long swipedId);

    Optional<Swipe> findBySwiperIdAndSwipedId(Long swiperId, Long swipedId);

    Optional<Swipe> findBySwiperIdAndSwipedIdAndType(Long swiperId, Long swipedId, SwipeType type);

    Optional<Swipe> findBySwiperId(Long swiperId);
}
