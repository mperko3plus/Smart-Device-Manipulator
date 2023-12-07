package hr.triplus.smartdevicemanipulator.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceStateDto {
    private String onlineState;
    private Integer batteryLevel;
    private Boolean mainsPower;
    private Boolean online;
    private Boolean trouble;

    public DeviceStateDto() {
    }

    public DeviceStateDto(String onlineState, Integer batteryLevel, Boolean mainsPower, Boolean online, Boolean trouble) {
        this.onlineState = onlineState;
        this.batteryLevel = batteryLevel;
        this.mainsPower = mainsPower;
        this.online = online;
        this.trouble = trouble;
    }

    public String getOnlineState() {
        return onlineState;
    }

    public void setOnlineState(String onlineState) {
        this.onlineState = onlineState;
    }

    public Integer getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(Integer batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public Boolean getMainsPower() {
        return mainsPower;
    }

    public void setMainsPower(Boolean mainsPower) {
        this.mainsPower = mainsPower;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public Boolean getTrouble() {
        return trouble;
    }

    public void setTrouble(Boolean trouble) {
        this.trouble = trouble;
    }
}
