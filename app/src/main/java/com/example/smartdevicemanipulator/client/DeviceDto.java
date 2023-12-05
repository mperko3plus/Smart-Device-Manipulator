package com.example.smartdevicemanipulator.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceDto {
    private String name;
    private String link;
    private String uuid;

    public DeviceDto() {
    }

    public DeviceDto(String name, String link, String uuid) {
        this.name = name;
        this.link = link;
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "DeviceDto{" +
                "name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
