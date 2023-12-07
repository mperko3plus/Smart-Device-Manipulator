package hr.triplus.smartdevicemanipulator.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SystemUserRoleDto {
    private Boolean role;
    private String permission;
    private Date fromDate;
    private Date toDate;
    private Integer level;
    private String creator;
    private Date created;
    private String modifier;
    private Date modified;

    public SystemUserRoleDto() {
    }

    public SystemUserRoleDto(Boolean role, String permission, Date fromDate, Date toDate, Integer level, String creator, Date created, String modifier, Date modified) {
        this.role = role;
        this.permission = permission;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.level = level;
        this.creator = creator;
        this.created = created;
        this.modifier = modifier;
        this.modified = modified;
    }

    public Boolean getRole() {
        return role;
    }

    public void setRole(Boolean role) {
        this.role = role;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
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
}
