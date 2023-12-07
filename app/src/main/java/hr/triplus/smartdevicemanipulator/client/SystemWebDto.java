package hr.triplus.smartdevicemanipulator.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SystemWebDto {
    private Integer id;
    private String uuid;
    private String name;
    private String surname;
    private String description;
    private String comment;
    private SystemBox.SystemType type;
    private String category;
    private Integer categoryId;
    private String realm;
    private Integer realmId;
    private String master;
    private String owner;
    private Map<String, Object> config;
    private List<SystemRoleDto> systemPermissions;
    private List<SystemUserDto> users;
    private Map<String, Object> members;
    private List<ControllerDto> controllers;
    private List<String> slaveControllers;
    private List<SlaveSystemDto> slaves;
    private List<String> slaveSystems;
    private Set<TagDto> systemTags;
    private SystemUserRoleGroupRoleDto defaultDevicePermission;
    private String creator;
    private Date created;
    private String modifier;
    private Date modified;
    private Boolean subsystem;
    private String dealer;
    private String virtualController;

    public SystemWebDto() {
    }

    public SystemWebDto(Integer id, String uuid, String name, String surname, String description, String comment, SystemBox.SystemType type, String category, Integer categoryId, String realm, Integer realmId, String master, String owner, Map<String, Object> config, List<SystemRoleDto> systemPermissions, List<SystemUserDto> users, Map<String, Object> members, List<ControllerDto> controllers, List<String> slaveControllers, List<SlaveSystemDto> slaves, List<String> slaveSystems, Set<TagDto> systemTags, SystemUserRoleGroupRoleDto defaultDevicePermission, String creator, Date created, String modifier, Date modified, Boolean subsystem, String dealer, String virtualController) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.surname = surname;
        this.description = description;
        this.comment = comment;
        this.type = type;
        this.category = category;
        this.categoryId = categoryId;
        this.realm = realm;
        this.realmId = realmId;
        this.master = master;
        this.owner = owner;
        this.config = config;
        this.systemPermissions = systemPermissions;
        this.users = users;
        this.members = members;
        this.controllers = controllers;
        this.slaveControllers = slaveControllers;
        this.slaves = slaves;
        this.slaveSystems = slaveSystems;
        this.systemTags = systemTags;
        this.defaultDevicePermission = defaultDevicePermission;
        this.creator = creator;
        this.created = created;
        this.modifier = modifier;
        this.modified = modified;
        this.subsystem = subsystem;
        this.dealer = dealer;
        this.virtualController = virtualController;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public SystemBox.SystemType getType() {
        return type;
    }

    public void setType(SystemBox.SystemType type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public Integer getRealmId() {
        return realmId;
    }

    public void setRealmId(Integer realmId) {
        this.realmId = realmId;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Map<String, Object> getConfig() {
        return config;
    }

    public void setConfig(Map<String, Object> config) {
        this.config = config;
    }

    public List<SystemRoleDto> getSystemPermissions() {
        return systemPermissions;
    }

    public void setSystemPermissions(List<SystemRoleDto> systemPermissions) {
        this.systemPermissions = systemPermissions;
    }

    public List<SystemUserDto> getUsers() {
        return users;
    }

    public void setUsers(List<SystemUserDto> users) {
        this.users = users;
    }

    public Map<String, Object> getMembers() {
        return members;
    }

    public void setMembers(Map<String, Object> members) {
        this.members = members;
    }

    public List<ControllerDto> getControllers() {
        return controllers;
    }

    public void setControllers(List<ControllerDto> controllers) {
        this.controllers = controllers;
    }

    public List<String> getSlaveControllers() {
        return slaveControllers;
    }

    public void setSlaveControllers(List<String> slaveControllers) {
        this.slaveControllers = slaveControllers;
    }

    public List<SlaveSystemDto> getSlaves() {
        return slaves;
    }

    public void setSlaves(List<SlaveSystemDto> slaves) {
        this.slaves = slaves;
    }

    public List<String> getSlaveSystems() {
        return slaveSystems;
    }

    public void setSlaveSystems(List<String> slaveSystems) {
        this.slaveSystems = slaveSystems;
    }

    public Set<TagDto> getSystemTags() {
        return systemTags;
    }

    public void setSystemTags(Set<TagDto> systemTags) {
        this.systemTags = systemTags;
    }

    public SystemUserRoleGroupRoleDto getDefaultDevicePermission() {
        return defaultDevicePermission;
    }

    public void setDefaultDevicePermission(SystemUserRoleGroupRoleDto defaultDevicePermission) {
        this.defaultDevicePermission = defaultDevicePermission;
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

    public Boolean getSubsystem() {
        return subsystem;
    }

    public void setSubsystem(Boolean subsystem) {
        this.subsystem = subsystem;
    }

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }

    public String getVirtualController() {
        return virtualController;
    }

    public void setVirtualController(String virtualController) {
        this.virtualController = virtualController;
    }

    @Override
    public String toString() {
        return "SystemWebDto{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", description='" + description + '\'' +
                ", comment='" + comment + '\'' +
                ", type=" + type +
                ", category='" + category + '\'' +
                ", categoryId=" + categoryId +
                ", realm='" + realm + '\'' +
                ", realmId=" + realmId +
                ", master='" + master + '\'' +
                ", owner='" + owner + '\'' +
                ", config=" + config +
                ", systemPermissions=" + systemPermissions +
                ", users=" + users +
                ", members=" + members +
                ", controllers=" + controllers +
                ", slaveControllers=" + slaveControllers +
                ", slaves=" + slaves +
                ", slaveSystems=" + slaveSystems +
                ", systemTags=" + systemTags +
                ", defaultDevicePermission=" + defaultDevicePermission +
                ", creator='" + creator + '\'' +
                ", created=" + created +
                ", modifier='" + modifier + '\'' +
                ", modified=" + modified +
                ", subsystem=" + subsystem +
                ", dealer='" + dealer + '\'' +
                ", virtualController='" + virtualController + '\'' +
                '}';
    }
}