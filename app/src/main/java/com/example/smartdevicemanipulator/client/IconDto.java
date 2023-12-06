package com.example.smartdevicemanipulator.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IconDto {
    private String link;
    private DeviceTypeEnum name;
    private String endpointType;
    private String relativeUrl;


    public IconDto() {
    }

    public IconDto(String link, DeviceTypeEnum name, String endpointType, String relativeUrl) {
        this.link = link;
        this.name = name;
        this.endpointType = endpointType;
        this.relativeUrl = relativeUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public DeviceTypeEnum getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name != null ? DeviceTypeEnum.fromString(name) : null;
    }

    public String getEndpointType() {
        return endpointType;
    }

    public void setEndpointType(String endpointType) {
        this.endpointType = endpointType;
    }

    public String getRelativeUrl() {
        return relativeUrl;
    }

    public void setRelativeUrl(String relativeUrl) {
        this.relativeUrl = relativeUrl;
    }
}
