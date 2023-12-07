package hr.triplus.smartdevicemanipulator.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttributeValueDto {

    private String value;

    private Date timestamp;

    private String pendingValue;

    private Date   pendingTimestamp;

    public AttributeValueDto(String value, Date timestamp, String pendingValue, Date pendingTimestamp) {
        this.value = value;
        this.timestamp = timestamp;
        this.pendingValue = pendingValue;
        this.pendingTimestamp = pendingTimestamp;
    }

    public AttributeValueDto() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getPendingValue() {
        return pendingValue;
    }

    public void setPendingValue(String pendingValue) {
        this.pendingValue = pendingValue;
    }

    public Date getPendingTimestamp() {
        return pendingTimestamp;
    }

    public void setPendingTimestamp(Date pendingTimestamp) {
        this.pendingTimestamp = pendingTimestamp;
    }

    @Override
    public String toString() {
        return "AttributeValueDto{" +
                "value='" + value + '\'' +
                ", timestamp=" + timestamp +
                ", pendingValue='" + pendingValue + '\'' +
                ", pendingTimestamp=" + pendingTimestamp +
                '}';
    }
}

