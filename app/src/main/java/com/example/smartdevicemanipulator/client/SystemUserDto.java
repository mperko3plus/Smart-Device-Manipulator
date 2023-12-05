package com.example.smartdevicemanipulator.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SystemUserDto {
    private Integer id;
    private String uuid;
    private String username;
    private String name;
    private String surname;
    private String role;
    private Boolean active;
    private Boolean confirmed;
    private List<SystemUserRoleDto> permissions;
    private String creator;
    private Date created;
    private String modifier;
    private Date modified;
    private String boxUser;
    private Integer systemId;

    public SystemUserDto() {
    }

    public SystemUserDto(Integer id, String uuid, String username, String name, String surname, String role, Boolean active, Boolean confirmed, List<SystemUserRoleDto> permissions, String creator, Date created, String modifier, Date modified, String boxUser, Integer systemId) {
        this.id = id;
        this.uuid = uuid;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.active = active;
        this.confirmed = confirmed;
        this.permissions = permissions;
        this.creator = creator;
        this.created = created;
        this.modifier = modifier;
        this.modified = modified;
        this.boxUser = boxUser;
        this.systemId = systemId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public List<SystemUserRoleDto> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<SystemUserRoleDto> permissions) {
        this.permissions = permissions;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getBoxUser() {
        return boxUser;
    }

    public void setBoxUser(String boxUser) {
        this.boxUser = boxUser;
    }

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }
}
