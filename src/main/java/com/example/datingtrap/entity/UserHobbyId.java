package com.example.datingtrap.entity;

import java.io.Serializable;
import java.util.Objects;

public class UserHobbyId implements Serializable {
    private Long user;
    private Long hobby;

    public UserHobbyId() {}

    public UserHobbyId(Long user, Long hobby) {
        this.user = user;
        this.hobby = hobby;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserHobbyId)) return false;
        UserHobbyId that = (UserHobbyId) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(hobby, that.hobby);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, hobby);
    }
}
