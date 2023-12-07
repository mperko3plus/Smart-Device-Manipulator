package hr.triplus.smartdevicemanipulator.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SystemZipato {
    public enum ControllerRole {
        MASTER,
        SLAVE,
        CLUSTER,
        MEMBER,
        UNASSIGNED,
        VIRTUAL
    }
}
