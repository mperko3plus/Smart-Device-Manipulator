package com.example.smartdevicemanipulator.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ControllerDto {
    private Integer id;
    private String serial;
    private Boolean cluster;
    private Boolean cloud;
    private Boolean master;
    private String name;
    private String description;
    private String manufacturer;
    private String model;
    private String config;
    private Map<String, Object> state;
    private Date created;
    private String creator;
    private String modifier;
    private Date modified;
    private List<ControllerRoleDto> permissions;
    private Integer remoteId;
    private String token;
    private SystemZipato.ControllerRole controllerRole;
    private Integer systemId;

    public ControllerDto() {
    }

    public ControllerDto(Integer id, String serial, Boolean cluster, Boolean cloud, Boolean master, String name, String description, String manufacturer, String model, String config, Map<String, Object> state, Date created, String creator, String modifier, Date modified, List<ControllerRoleDto> permissions, Integer remoteId, String token, SystemZipato.ControllerRole controllerRole, Integer systemId) {
        this.id = id;
        this.serial = serial;
        this.cluster = cluster;
        this.cloud = cloud;
        this.master = master;
        this.name = name;
        this.description = description;
        this.manufacturer = manufacturer;
        this.model = model;
        this.config = config;
        this.state = state;
        this.created = created;
        this.creator = creator;
        this.modifier = modifier;
        this.modified = modified;
        this.permissions = permissions;
        this.remoteId = remoteId;
        this.token = token;
        this.controllerRole = controllerRole;
        this.systemId = systemId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Boolean getCluster() {
        return cluster;
    }

    public void setCluster(Boolean cluster) {
        this.cluster = cluster;
    }

    public Boolean getCloud() {
        return cloud;
    }

    public void setCloud(Boolean cloud) {
        this.cloud = cloud;
    }

    public Boolean getMaster() {
        return master;
    }

    public void setMaster(Boolean master) {
        this.master = master;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public Map<String, Object> getState() {
        return state;
    }

    public void setState(Map<String, Object> state) {
        this.state = state;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
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

    public List<ControllerRoleDto> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<ControllerRoleDto> permissions) {
        this.permissions = permissions;
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

    public SystemZipato.ControllerRole getControllerRole() {
        return controllerRole;
    }

    public void setControllerRole(SystemZipato.ControllerRole controllerRole) {
        this.controllerRole = controllerRole;
    }

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }
}
