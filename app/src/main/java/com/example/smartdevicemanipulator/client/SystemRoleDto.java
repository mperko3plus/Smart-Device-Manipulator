package com.example.smartdevicemanipulator.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SystemRoleDto {
    private Integer roleId;
    private Integer provPackageId;
    private String permission;
    private String provisionPackage;
    private Date fromDate;
    private Date toDate;
    private Double limit;
    private Integer level;
    private Integer systemId;
    private String activationType;
    private String activationProduct;

    public SystemRoleDto() {
    }

    public SystemRoleDto(Integer roleId, Integer provPackageId, String permission, String provisionPackage, Date fromDate, Date toDate, Double limit, Integer level, Integer systemId, String activationType, String activationProduct) {
        this.roleId = roleId;
        this.provPackageId = provPackageId;
        this.permission = permission;
        this.provisionPackage = provisionPackage;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.limit = limit;
        this.level = level;
        this.systemId = systemId;
        this.activationType = activationType;
        this.activationProduct = activationProduct;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getProvPackageId() {
        return provPackageId;
    }

    public void setProvPackageId(Integer provPackageId) {
        this.provPackageId = provPackageId;
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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }

    public String getActivationType() {
        return activationType;
    }

    public void setActivationType(String activationType) {
        this.activationType = activationType;
    }

    public String getActivationProduct() {
        return activationProduct;
    }

    public void setActivationProduct(String activationProduct) {
        this.activationProduct = activationProduct;
    }
}
