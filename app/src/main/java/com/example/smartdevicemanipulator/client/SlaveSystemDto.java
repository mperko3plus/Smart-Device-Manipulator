package com.example.smartdevicemanipulator.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SlaveSystemDto {
    private String uuid;
    private String name;
    private Integer remoteId;
    private String token;
    private Integer systemId;


    public SlaveSystemDto() {
    }

    public SlaveSystemDto(String uuid, String name, Integer remoteId, String token, Integer systemId) {
        this.uuid = uuid;
        this.name = name;
        this.remoteId = remoteId;
        this.token = token;
        this.systemId = systemId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(Integer remoteId) {
        this.remoteId = remoteId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }
}
