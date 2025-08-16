package com.goglotek.frauddetector.dataextractionservice.datastoreservice.dto;

import java.util.List;

import com.goglotek.frauddetector.dataextractionservice.datastoreservice.model.GroupAccounts;

public class GroupAccountsDto {
    private long count;
    private List<GroupAccounts> groupAccounts;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<GroupAccounts> getGroupAccounts() {
        return groupAccounts;
    }

    public void setGroupAccounts(List<GroupAccounts> groupAccounts) {
        this.groupAccounts = groupAccounts;
    }

}
