package com.example.smartdevicemanipulator.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ControllerRoleDto {
    private String permission;
    private String provisionPackage;
    private Date fromDate;
    private Date toDate;
    private Double limit;


    public ControllerRoleDto() {
    }

    public ControllerRoleDto(String permission, String provisionPackage, Date fromDate, Date toDate, Double limit) {
        this.permission = permission;
        this.provisionPackage = provisionPackage;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.limit = limit;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getProvisionPackage() {
        return provisionPackage;
    }

    public void setProvisionPackage(String provisionPackage) {
        this.provisionPackage = provisionPackage;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Double getLimit() {
        return limit;
    }

    public void setLimit(Double limit) {
        this.limit = limit;
    }
}
