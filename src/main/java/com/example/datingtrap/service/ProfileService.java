package com.example.datingtrap.service;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

import com.example.datingtrap.dto.*;
import com.example.datingtrap.entity.*;
import com.example.datingtrap.exception.ResourceNotFoundException;
import com.example.datingtrap.repository.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final HobbyRepository hobbyRepository;
    private final PreferenceRepository preferenceRepository;
    private final UserHobbyRepository userHobbyRepository;

    public ProfileDTO getProfileByUserId(Long userId) {
        Profiles profile = profileRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found with id: " + userId));
        return new ProfileDTO(profile);
    }

    public ProfileDTO updateProfile(Long userId, ProfileUpdateDTO dto) {
        Profiles profile = profileRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found with id: " + userId));
        User user = profile.getUser();

        // Update profile info
        profile.setAge(dto.getAge());
        profile.setBio(dto.getBio());
        profile.setGender(dto.getGender());
        profile.setJob(dto.getJob());
        profile.setLocation(dto.getLocation());
        profile.setAvatarUrl(dto.getAvatarUrl());
        profile.setBirthDate(dto.getBirthDate());

        // Update or create preference
        PreferenceDTO prefDto = dto.getPreference();
        Preference preference = preferenceRepository.findByUserId(userId)
                .orElse(new Preference());

        preference.setUser(user);
        preference.setInterestedGender(prefDto.getInterestedGender());
        preference.setMinAge(prefDto.getMinAge());
        preference.setMaxAge(prefDto.getMaxAge());
        preference.setMaxDistance(prefDto.getMaxDistance());
        preference.setDatingPurpose(prefDto.getDatingPurpose());

        preferenceRepository.save(preference);

        // Update hobbies
//        userHobbyRepository.deleteByUserId(userId); // Xoá hết cũ trước khi thêm mới
//
//        List<Long> hobbyIds = dto.getHobbyIds();
//        Set<Hobby> hobbies = hobbyIds.stream()
//                .map(id -> hobbyRepository.findById(id)
//                        .orElseThrow(() -> new ResourceNotFoundException("Hobby not found: " + id)))
//                .collect(Collectors.toSet());
//
//        for (Hobby hobby : hobbies) {
//            UserHobby userHobby = new UserHobby();
//            userHobby.setUser(user);
//            userHobby.setHobby(hobby);
//            userHobbyRepository.save(userHobby);
//        }
        List<String> hobbyNames = dto.getHobbies(); // danh sách tên hobby từ DTO
        Set<Hobby> newHobbies = new HashSet<>();

        for (String name : hobbyNames) {
            Hobby hobby = hobbyRepository.findByName(name)
                    .orElseGet(() -> {
                        Hobby newHobby = new Hobby();
                        newHobby.setName(name);
                        return hobbyRepository.save(newHobby);
                    });
            newHobbies.add(hobby);
        }

        user.setHobbies(newHobbies);

        profileRepository.save(profile);
        return new ProfileDTO(profile);
    }

    public ProfileDTO registerNewUserWithProfile(RegisterRequestDTO dto) {
        // Create user
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setFireBase(dto.getFireBaseId());

        // Create profile
        Profiles profile = new Profiles();
        profile.setUser(user);
        profile.setFullName(dto.getFullName());
        profile.setAge(dto.getAge());
        profile.setBio(dto.getBio());
        profile.setGender(dto.getGender());
        profile.setJob(dto.getJob());
        profile.setLocation(dto.getLocation());
        profile.setAvatarUrl(dto.getAvatarUrl());
        profile.setBirthDate(dto.getBirthDate());

        user.setProfile(profile); // Gán profile cho user

        // Save preference
        PreferenceDTO prefDto = dto.getPreference();
        Preference preference = Preference.builder()
                .user(user)
                .interestedGender(prefDto.getInterestedGender())
                .minAge(prefDto.getMinAge())
                .maxAge(prefDto.getMaxAge())
                .maxDistance(prefDto.getMaxDistance())
                .datingPurpose(prefDto.getDatingPurpose())
                .build();

        user.setPreference(preference); // Gán preference cho user

        // Save hobbies
        List<Long> hobbyIds = dto.getHobbyIds();
        Set<Hobby> hobbies = hobbyIds.stream()
                .map(id -> hobbyRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Hobby not found: " + id)))
                .collect(Collectors.toSet());

        user.setHobbies(hobbies);

        // Save user (cascades profile, preference, hobbies)
        userRepository.save(user);

        return new ProfileDTO(profile);
    }
}
