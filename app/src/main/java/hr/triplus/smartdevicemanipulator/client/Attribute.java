package hr.triplus.smartdevicemanipulator.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Attribute {
    private String uuid;
    private String name;
    private AttributeDefinition definition;
    private AttributeConfigDto config;
    private DeviceDto device;
    private Integer attributeId;
    private AttributeValueDto value;

    public Attribute() {
    }

    public Attribute(String uuid, String name, AttributeDefinition definition, AttributeConfigDto config, DeviceDto device, Integer attributeId, AttributeValueDto value) {
        this.uuid = uuid;
        this.name = name;
        this.definition = definition;
        this.config = config;
        this.device = device;
        this.attributeId = attributeId;
        this.value = value;
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

    public AttributeValueDto getValue() {
        return value;
    }

    public void setValue(AttributeValueDto value) {
        this.value = value;
    }

    public AttributeDefinition getDefinition() {
        return definition;
    }

    public void setDefinition(AttributeDefinition definition) {
        this.definition = definition;
    }

    public AttributeConfigDto getConfig() {
        return config;
    }

    public void setConfig(AttributeConfigDto config) {
        this.config = config;
    }

    public DeviceDto getDevice() {
        return device;
    }

    public void setDevice(DeviceDto device) {
        this.device = device;
    }

    public Integer getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Integer attributeId) {
        this.attributeId = attributeId;
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", definition=" + definition +
                ", config=" + config +
                ", device=" + device +
                ", attributeId=" + attributeId +
                ", value=" + value +
                '}';
    }
}
