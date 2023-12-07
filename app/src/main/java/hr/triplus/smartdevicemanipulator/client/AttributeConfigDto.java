package hr.triplus.smartdevicemanipulator.client;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.ALWAYS)
public class AttributeConfigDto {


    private String              name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private TypeTagDto          typeTag;

    private boolean             master;


    private boolean             hidden;


    private boolean             reported;


    private Integer             expire;


    private String              compression;


    private String              type;


    private String              unit;


    private Map<String, String> enumValues;


    private Double              scale;

    @JsonInclude(JsonInclude.Include.NON_NULL)

    private Integer             precision;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer             alarmType;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer             valueCount;

    private Integer             room;

    @JsonInclude(JsonInclude.Include.NON_NULL)

    private Double             maxVal;

    @JsonInclude(JsonInclude.Include.NON_NULL)

    private Double             minVal;

    @JsonIgnore

    private Map<String, Object> data = new HashMap<String, Object>();

    @JsonAnyGetter
    public Map<String, Object> getData() {
        return data;
    }

    @JsonAnySetter
    public void setData(final String p_property, final Object p_value) {
        data.put(p_property, p_value);
    }

    public TypeTagDto getTypeTag() {
        return typeTag;
    }

    public void setTypeTag(final TypeTagDto p_typeTag) {
        typeTag = p_typeTag;
    }

    public boolean isMaster() {
        return master;
    }

    public void setMaster(final boolean p_master) {
        master = p_master;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(final boolean p_hidden) {
        hidden = p_hidden;
    }

    public boolean isReported() {
        return reported;
    }

    public void setReported(final boolean p_reported) {
        reported = p_reported;
    }

    public Integer getExpire() {
        return expire;
    }

    public void setExpire(final Integer p_expire) {
        expire = p_expire;
    }

    public String getCompression() {
        return compression;
    }

    public void setCompression(final String p_compression) {
        compression = p_compression;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(final String p_unit) {
        unit = p_unit;
    }

    public Map<String, String> getEnumValues() {
        return enumValues;
    }

    public void setEnumValues(final Map<String, String> p_enumValues) {
        enumValues = p_enumValues;
    }

    public Double getScale() {
        return scale;
    }

    public void setScale(final Double p_scale) {
        scale = p_scale;
    }

    public Integer getPrecision() {
        return precision;
    }

    public void setPrecision(final Integer p_precision) {
        precision = p_precision;
    }

    public Integer getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(final Integer p_alarmType) {
        alarmType = p_alarmType;
    }

    public Integer getValueCount() {
        return valueCount;
    }

    public void setValueCount(final Integer p_valueCount) {
        valueCount = p_valueCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String p_name) {
        name = p_name;
    }

    public Integer getRoom() {
        return room;
    }

    public void setRoom(Integer p_room) {
        room = p_room;
    }

    public String getType() {
        return type;
    }

    public void setType(String p_type) {
        type = p_type;
    }

    public Double getMaxVal() {
        return maxVal;
    }

    public void setMaxVal(Double maxVal) {
        this.maxVal = maxVal;
    }

    public Double getMinVal() {
        return minVal;
    }

    public void setMinVal(Double minVal) {
        this.minVal = minVal;
    }

    @Override
    public String toString() {
        return "AttributeConfigDto{" +
                "name='" + name + '\'' +
                ", typeTag=" + typeTag +
                ", master=" + master +
                ", hidden=" + hidden +
                ", reported=" + reported +
                ", expire=" + expire +
                ", compression='" + compression + '\'' +
                ", type='" + type + '\'' +
                ", unit='" + unit + '\'' +
                ", enumValues=" + enumValues +
                ", scale=" + scale +
                ", precision=" + precision +
                ", alarmType=" + alarmType +
                ", valueCount=" + valueCount +
                ", room=" + room +
                ", maxVal=" + maxVal +
                ", minVal=" + minVal +
                ", data=" + data +
                '}';
    }
}

