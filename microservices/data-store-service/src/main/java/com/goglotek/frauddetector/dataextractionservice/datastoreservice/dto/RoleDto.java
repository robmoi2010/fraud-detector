package com.goglotek.frauddetector.dataextractionservice.datastoreservice.dto;

import java.util.List;

import com.goglotek.frauddetector.dataextractionservice.datastoreservice.model.Role;

public class RoleDto {
    private long count;
    private List<Role> roles;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

}
