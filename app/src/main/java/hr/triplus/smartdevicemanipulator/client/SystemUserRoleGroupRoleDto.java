package hr.triplus.smartdevicemanipulator.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SystemUserRoleGroupRoleDto {
    private String role;
    private Integer level;

    public SystemUserRoleGroupRoleDto() {
    }

    public SystemUserRoleGroupRoleDto(String role, Integer level) {
        this.role = role;
        this.level = level;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
