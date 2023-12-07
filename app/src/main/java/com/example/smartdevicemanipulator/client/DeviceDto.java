package com.example.smartdevicemanipulator.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceDto {
    private String name;
    private String link;
    private String uuid;
    private IconDto icon;
    private NetworkDto network;
    private DeviceStateDto state;
    private ConfigDto config;
    private DescriptorDto descriptor;
    private DeviceDescriptorInfoDto deviceDescriptorInfo;
    private List<Attribute> attributes;

    public DeviceDto() {
    }

    public DeviceDto(String name, String link, String uuid, IconDto icon, NetworkDto network, DeviceStateDto state, ConfigDto config, DescriptorDto descriptor, DeviceDescriptorInfoDto deviceDescriptorInfo, List<Attribute> attributes) {
        this.name = name;
        this.link = link;
        this.uuid = uuid;
        this.icon = icon;
        this.network = network;
        this.state = state;
        this.config = config;
        this.descriptor = descriptor;
        this.deviceDescriptorInfo = deviceDescriptorInfo;
        this.attributes = attributes;
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

    public IconDto getIcon() {
        return icon;
    }

    public void setIcon(IconDto icon) {
        this.icon = icon;
    }

    public DeviceStateDto getState() {
        return state;
    }

    public void setState(DeviceStateDto state) {
        this.state = state;
    }

    public ConfigDto getConfig() {
        return config;
    }

    public void setConfig(ConfigDto config) {
        this.config = config;
    }

    public DescriptorDto getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(DescriptorDto descriptor) {
        this.descriptor = descriptor;
    }

    public DeviceDescriptorInfoDto getDeviceDescriptorInfo() {
        return deviceDescriptorInfo;
    }

    public void setDeviceDescriptorInfo(DeviceDescriptorInfoDto deviceDescriptorInfo) {
        this.deviceDescriptorInfo = deviceDescriptorInfo;
    }

    public NetworkDto getNetwork() {
        return network;
    }

    public void setNetwork(NetworkDto network) {
        this.network = network;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "DeviceDto{" +
                "name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", uuid='" + uuid + '\'' +
                ", icon=" + icon +
                ", network=" + network +
                ", state=" + state +
                ", config=" + config +
                ", descriptor=" + descriptor +
                ", deviceDescriptorInfo=" + deviceDescriptorInfo +
                ", attributes=" + attributes +
                '}';
    }
}
