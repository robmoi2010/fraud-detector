package com.goglotek.frauddetector.dataprocessorservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataPayload {
    private Integer page;
    private Integer limit;
    @JsonProperty("order_by")
    private String orderBy;
    private String direction;

    private FilterModel filterModel;

    public FilterModel getFilterModel() {
        return filterModel;
    }

    public void setFilterModel(FilterModel filterModel) {
        this.filterModel = filterModel;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
