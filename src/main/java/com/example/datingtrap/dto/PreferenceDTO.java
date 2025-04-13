package com.example.datingtrap.dto;

import lombok.*;
import com.example.datingtrap.entity.Preference;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreferenceDTO {
    private Integer id;
    private String interestedGender;
    private Integer maxDistance;
    private Integer minAge;
    private Integer maxAge;
    private String datingPurpose;

    public PreferenceDTO(Preference preference) {
        this.id = preference.getId();
        this.interestedGender = preference.getInterestedGender();
        this.maxDistance = preference.getMaxDistance();
        this.minAge = preference.getMinAge();
        this.maxAge = preference.getMaxAge();
        this.datingPurpose = preference.getDatingPurpose();
    }
}
