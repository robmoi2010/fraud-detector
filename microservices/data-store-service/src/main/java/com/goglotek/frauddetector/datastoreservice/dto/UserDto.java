package com.goglotek.frauddetector.datastoreservice.dto;

import java.util.List;

import com.goglotek.frauddetector.datastoreservice.model.Users;

public class UserDto {
    private long count;
    private List<Users> user;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<Users> getUser() {
        return user;
    }

    public void setUser(List<Users> user) {
        this.user = user;
    }

}
