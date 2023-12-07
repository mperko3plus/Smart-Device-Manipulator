package hr.triplus.smartdevicemanipulator.client;

public enum DeviceTypeEnum {
    ACCELEROMETER("Accelerometer"),
    AIR_PRESSURE_METER("Air pressure meter"),
    ALARM("Alarm"),
    AUDIO_PLAYER("Audio player"),
    AUTOMATIC_DOOR("Automatic door"),
    AUTOMATIC_SLIDING_DOOR("Automatic sliding door"),
    AWNINGS("Awnings"),
    BINARY_INPUT_SENSOR("Binary input sensor"),
    BLINDS("Blinds"),
    BOILER("Boiler"),
    BOLLARD("Bollard"),
    CAMERA("Camera"),
    CHARGER_CONNECTION("Charger connection"),
    CLOUD_STATUS("Cloud status"),
    CO_SENSOR("CO sensor"),
    DOOR("Door"),
    DOOR_LOCK("Door lock"),
    DRAPERIES("Draperies"),
    ELECTRIC_FAILURE_SENSOR("Electric failure sensor"),
    FIRE_SENSOR("Fire sensor"),
    FLOOD_SENSOR("Flood sensor"),
    FREEZE_DETECTOR("Freeze detector"),
    FREEZER_BREAKDOWN_SENSOR("Freezer breakdown sensor"),
    GARAGE_DOOR_SENSOR("Garage door sensor"),
    GARAGE_SECTIONAL_DOOR("Garage sectional door"),
    GARAGE_SWINGING_DOOR("Garage swinging door"),
    GAS_LEAK_SENSOR("Gas leak sensor"),
    GAS_SENSOR("Gas sensor"),
    GATE("Gate"),
    GENERIC_METER("Generic meter"),
    GENERIC_ON_OFF("Generic OnOff"),
    GENERIC_SENSOR("Generic sensor"),
    GLASSBREAK_SENSOR("Glassbreak sensor"),
    GROUP("Group"),
    HOT_WATER_TANK("Hot water tank"),
    HTTP_DEVICE("HTTP device"),
    IR_EXTENDER("IR extender"),
    IR_EXTENDER_CONTROL("IR extender control"),
    KEYFOB("Keyfob"),
    KEYPAD("Keypad"),
    LIGHT("Light"),
    LIGHT_METER("Light meter"),
    LIGHT_SENSOR("Light sensor"),
    METER_VENTILATION("Meter ventilation"),
    MOISTRE_METER("Moistre meter"),
    MOTION_SENSOR("Motion sensor"),
    NOISE_LEVEL("Noise level"),
    NOISE_SAFETY("Noise safety"),
    ON_OFF_SWITCH("OnOff switch"),
    PANNELS("Pannels"),
    PERIMETER_SENSOR("Perimeter sensor"),
    PILOT_WIRE("Pilot wire"),
    PLUGIN_DEVICE("Plugin device"),
    RADIATOR("Radiator"),
    RAIN_METER("Rain meter"),
    RAIN_SENSOR("Rain sensor"),
    RAMP("Ramp"),
    RGB_CONTROL("RGB controll"),
    RGBW_BULB("RGBW bulb"),
    ROTATING_SIREN("Rotating siren"),
    SCENE("Scene"),
    SCREEN("Screen"),
    SHUTTER_OPENING_SENSOR("Shutter opening sensor"),
    SHUTTERS("Shutters"),
    SIREN("Siren"),
    SLIDING_GATE("Sliding gate"),
    SUN_METER("Sun meter"),
    SUN_POSITION_METER("Sun position meter"),
    SWITCH_WITH_METER("Switch with meter"),
    TEMPERATURE_AND_HUMIDITY_METER("Temperature and humidity meter"),
    TEMPERATURE_METER("Temperature meter"),
    THERMOSTAT("Thermostat"),
    THERMOSTAT2("Thermostat2"),
    TOUCH_CONTROL("Touch control"),
    TOUCH_SENSOR("Touch sensor"),
    UNDEFINED("Undefined"),
    VALVE_DEVICE("Valve device"),
    VENTILATION("Ventilaton"),
    VOLUME_METER("Volume meter"),
    WATER_LEAK_SENSOR("Water leak sensor"),
    WATERING_DEVICE("Watering device"),
    WEATHER_STATION("Weather station"),
    WIND_METER("Wind meter"),
    WIND_SENSOR("Wind sensor"),
    WINDOW("Window"),
    WINDSPEED_METER("Windspeed meter");

    private final String deviceType;

    DeviceTypeEnum(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceType() {
        return deviceType;
    }

    // Custom method to convert a string to enum based on custom value
    public static DeviceTypeEnum fromString(String value) {
        for (DeviceTypeEnum enumType : DeviceTypeEnum.values()) {
            if (enumType.deviceType.equals(value)) {
                return enumType;
            }
        }
        throw new IllegalArgumentException("No enum constant with custom value: " + value);
    }

}
