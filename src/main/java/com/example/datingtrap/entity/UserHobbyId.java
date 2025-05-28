package com.example.datingtrap.entity;

import java.io.Serializable;
import java.util.Objects;

public class UserHobbyId implements Serializable {
    private Long user;
    private Integer hobby;

    public UserHobbyId() {}

    public UserHobbyId(Long user, Integer hobby) {
        this.user = user;
        this.hobby = hobby;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public Integer getHobby() {
        return hobby;
    }

    public void setHobby(Integer hobby) {
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
