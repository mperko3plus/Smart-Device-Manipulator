package hr.triplus.smartdevicemanipulator.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConfigDto {
    private String className;
    private Integer appVersion;
    private String uuid;
    private String specificDevClass;
    private String model;
    private String genericDevClass;
    private String name;
    private String iconType;

    public ConfigDto() {
    }

    public ConfigDto(String className, Integer appVersion, String uuid, String specificDevClass, String model, String genericDevClass, String name, String iconType) {
        this.className = className;
        this.appVersion = appVersion;
        this.uuid = uuid;
        this.specificDevClass = specificDevClass;
        this.model = model;
        this.genericDevClass = genericDevClass;
        this.name = name;
        this.iconType = iconType;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(Integer appVersion) {
        this.appVersion = appVersion;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSpecificDevClass() {
        return specificDevClass;
    }

    public void setSpecificDevClass(String specificDevClass) {
        this.specificDevClass = specificDevClass;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getGenericDevClass() {
        return genericDevClass;
    }

    public void setGenericDevClass(String genericDevClass) {
        this.genericDevClass = genericDevClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconType() {
        return iconType;
    }

    public void setIconType(String iconType) {
        this.iconType = iconType;
    }
}
