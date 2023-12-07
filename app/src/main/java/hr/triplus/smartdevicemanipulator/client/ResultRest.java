package hr.triplus.smartdevicemanipulator.client;

import com.fasterxml.jackson.annotation.JsonAnyGetter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ResultRest extends RestObject {
    private UUID transactionId;
    private Map<String, Object> data;

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(final UUID p_transactionId) {
        transactionId = p_transactionId;
    }

    @JsonAnyGetter
    public Map<String, Object> getJsonData() {
        if (data == null) {
            return null;
        }
        data.remove("transactionId");
        data.remove("success");
        data.remove("error");
        data.remove("errors");
        return data;
    }

    public void addData(Map<String, ?> p_map) {
        if (data == null) {
            data = new HashMap<>();
        }
        data.putAll(p_map);
    }
}
