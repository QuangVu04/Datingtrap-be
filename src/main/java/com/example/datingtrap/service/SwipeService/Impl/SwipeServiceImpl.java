package com.example.datingtrap.service.SwipeService.Impl;

import com.example.datingtrap.dto.*;
import com.example.datingtrap.entity.*;
import com.example.datingtrap.enums.SwipeType;
import com.example.datingtrap.repository.ProfileRepository;
import com.example.datingtrap.repository.SwipeRepository;
import com.example.datingtrap.repository.UserRepository;
import com.example.datingtrap.service.Matchservice.MatchService;
import com.example.datingtrap.service.SwipeService.SwipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SwipeServiceImpl implements SwipeService {

    @Autowired
    private MatchService matchService;

    @Autowired
    private SwipeRepository swipeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    public ApiResponse swipe(SwipeDTO dto) {

        if (swipeRepository.existsBySwiperIdAndSwipedId(dto.getSwiperId(), dto.getSwipedId())) {
            return new ApiResponse("False", "Already swiped!");
        }

        User swiper = userRepository.findById(dto.getSwiperId())
                .orElseThrow(() -> new RuntimeException("Swiper not found"));
        User swiped = userRepository.findById(dto.getSwipedId())
                .orElseThrow(() -> new RuntimeException("Swiped not found"));

        Swipe swipe = Swipe.builder()
                .swiper(swiper)
                .swiped(swiped)
                .type(dto.getType())
                .build();

        swipeRepository.save(swipe);

        // Check nếu người kia cũng đã LIKE lại thì tạo MATCH
        if (dto.getType() == SwipeType.LIKE) {
            Optional<Swipe> other = swipeRepository.findBySwiperIdAndSwipedIdAndType(
                    dto.getSwipedId(), dto.getSwiperId(), SwipeType.LIKE);

            if (other.isPresent()) {
                CreateMatchRequest createMatchRequest = CreateMatchRequest.builder()
                        .userIdA(swiper.getId())
                        .userIdB(swiped.getId())
                        .build();
                // Gọi MatchService để tạo match
                 matchService.createMatch(createMatchRequest);
                return new ApiResponse("TRUE", "It's a MATCH!");
            }
        }

        return new ApiResponse("TRUE", "Swipe saved");
    }

    /**
     * Get a paginated list of potential matches for a user to swipe on,
     * filtered by preferences and ordered by hobby matches
     *
     * @param userId the ID of the user requesting recommendations
     * @param page page number (0-based)
     * @param size number of items per page
     * @return paginated list of users to swipe on
     */
    public Page<UserSwipeDTO> getSwipeRecommendations(Long userId, int page, int size) {
        // Create Pageable object for pagination
        Pageable pageable = PageRequest.of(page, size);

        // Get the current user
        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Get the user's preferences
        Preference userPreference = currentUser.getPreference();
        if (userPreference == null) {
            throw new RuntimeException("User preferences not found");
        }

        // Get current user's profile for location-based filtering
        Profiles userProfile = currentUser.getProfile();
        if (userProfile == null) {
            throw new RuntimeException("User profile not found");
        }

        // Get current user's hobbies for matching
        Set<Hobby> userHobbies = currentUser.getHobbies();

        // Find users who the current user has already swiped on
        List<Long> swipedUserIds = swipeRepository.findBySwiperId(userId)
                .stream()
                .map(swipe -> swipe.getSwiped().getId())
                .collect(Collectors.toList());

        // Add current user ID to exclude from results
        swipedUserIds.add(userId);

        // Get all users who haven't been swiped on yet
        List<User> potentialMatches = userRepository.findByIdNotIn(swipedUserIds);

        // Filter users based on preferences and calculate hobby matches
        List<UserSwipeDTO> allFilteredMatches = potentialMatches.stream()
                .filter(user -> matchesPreferences(user, userPreference, userProfile))
                .map(user -> {
                    // Calculate number of shared hobbies
                    int hobbyMatchCount = countSharedHobbies(userHobbies, user.getHobbies());

                    // Create DTO with match score
                    UserSwipeDTO dto = mapToUserSwipeDto(user);
                    dto.setHobbyMatchCount(hobbyMatchCount);
                    return dto;
                })
                .sorted(Comparator.comparing(UserSwipeDTO::getHobbyMatchCount).reversed())
                .collect(Collectors.toList());

        // Apply pagination
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), allFilteredMatches.size());

        // Create a Page object with the paginated content
        return new PageImpl<>(
                allFilteredMatches.subList(start, end),
                pageable,
                allFilteredMatches.size()
        );
    }

    /**
     * Check if a user matches the current user's preferences
     */
    private boolean matchesPreferences(User potentialMatch, Preference userPreference, Profiles userProfile) {
        Profiles matchProfile = potentialMatch.getProfile();

        // Skip if the potential match doesn't have a profile
        if (matchProfile == null) {
            return false;
        }

        // Gender preference check
        String interestedGender = userPreference.getInterestedGender();
        if (!"both".equalsIgnoreCase(interestedGender) &&
                !matchProfile.getGender().equalsIgnoreCase(interestedGender)) {
            return false;
        }

        // Age preference check
        if (matchProfile.getBirthDate() != null) {
            int age = Period.between(matchProfile.getBirthDate(), LocalDate.now()).getYears();
            if (age < userPreference.getMinAge() || age > userPreference.getMaxAge()) {
                return false;
            }
        }

        // Distance check (simplified - actual implementation would use geospatial calculations)
//        if (userPreference.getMaxDistance() != null && userProfile.getLocation() != null &&
//                matchProfile.getLocation() != null) {
//
//            // This is a simplified placeholder - in a real implementation,
//            // you would use a geospatial library to calculate actual distance
//            double distance = calculateDistance(userProfile.getLocation(), matchProfile.getLocation());
//            if (distance > userPreference.getMaxDistance()) {
//                return false;
//            }
//        }

        // Check if the potential match's preferences also align with current user
        Preference matchPreference = potentialMatch.getPreference();
        if (matchPreference != null) {
            // Check if current user's gender matches potential match's interest
            if (!"both".equalsIgnoreCase(matchPreference.getInterestedGender()) &&
                    !userProfile.getGender().equalsIgnoreCase(matchPreference.getInterestedGender())) {
                return false;
            }

            // Check if current user's age is within potential match's preference
            if (userProfile.getBirthDate() != null) {
                int userAge = Period.between(userProfile.getBirthDate(), LocalDate.now()).getYears();
                if (userAge < matchPreference.getMinAge() || userAge > matchPreference.getMaxAge()) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Count shared hobbies between two users
     */
    private int countSharedHobbies(Set<Hobby> userHobbies, Set<Hobby> potentialMatchHobbies) {
        if (userHobbies == null || potentialMatchHobbies == null) {
            return 0;
        }

        int count = 0;
        for (Hobby hobby : userHobbies) {
            for (Hobby matchHobby : potentialMatchHobbies) {
                if (hobby.getId().equals(matchHobby.getId())) {
                    count++;
                    break;
                }
            }
        }

        return count;
    }

    /**
     * Simplified distance calculation between two locations
     * In a real implementation, use a geospatial library for accurate calculations
     */
    private double calculateDistance(String location1, String location2) {
        // Placeholder - in a real app, parse coordinates and use Haversine formula
        // or a geospatial library for accurate distance calculation

        // For now, returning a random value for demonstration
        return Math.random() * 100;
    }

    /**
     * Map User entity to UserSwipeDto for API response
     */
    private UserSwipeDTO mapToUserSwipeDto(User user) {
        UserSwipeDTO dto = new UserSwipeDTO();
        dto.setId(user.getId());

        if (user.getProfile() != null) {
            dto.setFullName(user.getProfile().getFullName());
            dto.setAge(user.getProfile().getAge());
            dto.setBio(user.getProfile().getBio());
            dto.setGender(user.getProfile().getGender());
            dto.setJob(user.getProfile().getJob());
            dto.setLocation(user.getProfile().getLocation());
            dto.setAvatarUrl(user.getProfile().getAvatarUrl());
        }

        // Map hobbies to strings
        if (user.getHobbies() != null) {
            List<String> hobbies = user.getHobbies().stream()
                    .map(Hobby::getName)
                    .collect(Collectors.toList());
            dto.setHobbies(hobbies);
        }

        return dto;
    }
}
