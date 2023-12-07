package hr.triplus.smartdevicemanipulator.client;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TypeTagDto {

    private String name;
    private String localName;

    public TypeTagDto() {
        super();
    }

    public TypeTagDto(final String[] p_names) {
        super();
        name = p_names[0];
        localName = p_names[1];
    }

    public String getName() {
        return name;
    }

    public void setName(final String p_name) {
        name = p_name;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(final String p_localName) {
        localName = p_localName;
    }

}

