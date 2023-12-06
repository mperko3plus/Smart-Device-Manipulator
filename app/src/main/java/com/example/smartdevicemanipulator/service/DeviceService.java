package com.example.smartdevicemanipulator.service;

import android.util.Log;

import com.example.smartdevicemanipulator.client.Attribute;
import com.example.smartdevicemanipulator.client.AttributeValueDto;
import com.example.smartdevicemanipulator.client.DeviceDto;
import com.example.smartdevicemanipulator.client.RestObject;
import com.example.smartdevicemanipulator.client.ResultRest;
import com.example.smartdevicemanipulator.client.SystemWebDto;
import com.example.smartdevicemanipulator.client.V3Client;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class DeviceService {

    // I am aware that this is bad.
    public static DeviceService INSTANCE = new DeviceService();

    private final ConcurrentHashMap<String, DeviceDto> deviceDataByUuid = new ConcurrentHashMap<>();

    private DeviceService() {
    }

    private static final ObjectMapper mapper = new ObjectMapper();
    private final V3Client v3 = V3Client.v3;

    public void selectSystem(String uuid) {
        CompletableFuture<SystemWebDto> successFuture = CompletableFuture.supplyAsync(() -> {
            try {
                String response = v3.sendGetRequest("/zipato-web/v2/systems/select?uuid=" + uuid);
                return mapper.readValue(response, SystemWebDto.class);
            } catch (Exception ex) {
                Log.e("Failed to select system", ex.getMessage(), ex);
                return null;
            }
        });
        try {
            successFuture.get();
        } catch (Exception ex) {
            Log.e("Failed to select system", ex.getMessage(), ex);
        }
    }

    public List<DeviceDto> getDevices() {
        CompletableFuture<List<DeviceDto>> deviceFuture = CompletableFuture.supplyAsync(() -> {
            try {
                String response = v3.sendGetRequest("/zipato-web/v2/devices");
                return mapper.readValue(response, new TypeReference<List<DeviceDto>>() {
                });
            } catch (Exception ex) {
                return new ArrayList<>();
            }
        });
        try {
            return deviceFuture.get();
        } catch (Exception e) {
            Log.e("Failed to fetch devices", e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public DeviceDto getDeviceByUuid(String uuid) {
        return deviceDataByUuid.computeIfAbsent(uuid, this::fetchDeviceByUuid);
    }

    private DeviceDto fetchDeviceByUuid(String uuid) {
        Log.i("DeviceService", "Fetching device by uuid: " + uuid);
        CompletableFuture<DeviceDto> deviceFuture = CompletableFuture.supplyAsync(() -> {
            try {
                String response = v3.sendGetRequest("/zipato-web/v2/devices/" + uuid + "?full=true");
                DeviceDto deviceDto = mapper.readValue(response, DeviceDto.class);
                fetchAndSetAttributesToDevice(deviceDto);
                return deviceDto;
            } catch (Exception ex) {
                Log.e("Something went wrong when fetching devices", ex.getMessage(), ex);
                return null;
            }
        });
        try {
            return deviceFuture.get();
        } catch (Exception ex) {
            Log.e("Failed to fetch device by uuid: " + uuid, ex.getMessage(), ex);
            return null;
        }
    }

    private void fetchAndSetAttributesToDevice(DeviceDto deviceDto) throws IOException, NoSuchAlgorithmException {
        List<Attribute> attributes = getAttributes();
        attributes = attributes.stream().filter(attribute -> attribute.getDevice().getUuid().equals(deviceDto.getUuid())).collect(Collectors.toList());
        List<Attribute> attributeValues = getAttributeValues(true);
        for (Attribute attribute : attributes) {
            for (Attribute attributeValue : attributeValues) {
                if (attributeValue.getUuid().equals(attribute.getUuid())) {
                    attribute.setValue(attributeValue.getValue());
                    break;
                }
            }
        }
        deviceDto.setAttributes(attributes);
    }

    private void fetchAndSetAttributesToDeviceAsync(DeviceDto deviceDto) {
        CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> {
            try {
                fetchAndSetAttributesToDevice(deviceDto);
                return true;
            } catch (Exception ex) {
                Log.e("Failed to fetch and set attributes for device with uuid: " + deviceDto.getUuid(), ex.getMessage(), ex);
                return false;
            }
        });
        try {
            Boolean success = future.get();
            if (!success) {
                Log.e("Device attribute fail", "Failed to set attributes to device with uuid" + deviceDto.getUuid());
            }
        } catch (Exception ex) {
            Log.e("Failed to fetch and set attributes for device with uuid: " + deviceDto.getUuid(), ex.getMessage(), ex);
        }
    }

    public void setOnOff(String deviceUuid, boolean on) {
        DeviceDto deviceDto = getDeviceByUuid(deviceUuid);
        List<Attribute> attributes = deviceDto.getAttributes();
        for (Attribute attribute : attributes) {
            String attr = attribute.getDefinition().getAttribute();
            String attributeType = attribute.getDefinition().getAttributeType();
            String cluster = attribute.getDefinition().getCluster();
            Boolean writable = attribute.getDefinition().getWritable();
            if (attr != null && attributeType != null && cluster != null && writable != null && attr.equals("state") && attributeType.equals("BOOLEAN") && cluster.equals("com.zipato.cluster.OnOff") && writable) {
                setAttribute(attribute.getUuid(), new AttributeValueDto(on ? "true" : "false", null, null, null));
                setAttributeUpdatedAsync(attribute);
                break;
            }
        }
//        fetchAndSetAttributesToDeviceAsync(deviceDto);
    }

    private void setAttributeUpdatedAsync(Attribute attribute) {
        CompletableFuture<Boolean> success = CompletableFuture.supplyAsync(() -> {
            Attribute attributeTemp = getAttribute(attribute.getUuid());
            if (attributeTemp == null) {
                return false;
            }
            attribute.setValue(attributeTemp.getValue());
            return true;
        });
        try {
            if (!success.get()) {
                Log.e("Failed to set attribute async", "Attribute fail");
            }
        } catch (Exception ex) {
            Log.e("Failed to set attribute async", ex.getMessage(), ex);
        }
    }

    public boolean getOnOff(String deviceUuid, boolean fetchAttributes) {
        DeviceDto deviceDto = getDeviceByUuid(deviceUuid);
        if (fetchAttributes) {
            fetchAndSetAttributesToDeviceAsync(deviceDto);
        }
        List<Attribute> attributes = deviceDto.getAttributes();
        for (Attribute attribute : attributes) {
            String attributeType = attribute.getDefinition().getAttributeType();
            String cluster = attribute.getDefinition().getCluster();
            if (attributeType != null && cluster != null && attributeType.equals("BOOLEAN") && (cluster.equals("com.zipato.cluster.OnOff") || cluster.equals("com.zipato.cluster.Notifications"))) {
                return Boolean.parseBoolean(attribute.getValue().getValue());
            }
        }
        throw new RuntimeException("Failed to fetch on/off state for device with uuid: " + deviceUuid);
    }

    public int getIntensity(String deviceUuid, boolean fetchAttributes) {
        DeviceDto deviceDto = getDeviceByUuid(deviceUuid);
        if (fetchAttributes) {
            fetchAndSetAttributesToDeviceAsync(deviceDto);
        }
        List<Attribute> attributes = deviceDto.getAttributes();
        for (Attribute attribute : attributes) {
            String attributeType = attribute.getDefinition().getAttributeType();
            String cluster = attribute.getDefinition().getCluster();
            Boolean writable = attribute.getDefinition().getWritable();
            if (attributeType != null && cluster != null && writable != null && attributeType.equals("NUMBER") && cluster.equals("com.zipato.cluster.LevelControl") && writable) {
                return Integer.parseInt(attribute.getValue().getValue());
            }
        }
        throw new RuntimeException("Failed to fetch intensity state for device with uuid: " + deviceUuid);
    }

    public void setIntensity(String deviceUuid, int intensity) {
        DeviceDto deviceDto = getDeviceByUuid(deviceUuid);
        List<Attribute> attributes = deviceDto.getAttributes();
        for (Attribute attribute : attributes) {
            String attributeType = attribute.getDefinition().getAttributeType();
            String cluster = attribute.getDefinition().getCluster();
            Boolean writable = attribute.getDefinition().getWritable();
            if (attributeType != null && cluster != null && writable != null && attributeType.equals("NUMBER") && cluster.equals("com.zipato.cluster.LevelControl") && writable) {
                setAttribute(attribute.getUuid(), new AttributeValueDto(String.valueOf(intensity), null, null, null));
                setAttributeUpdatedAsync(attribute);
                break;
            }
        }
//        fetchAndSetAttributesToDeviceAsync(deviceDto);
    }

    public RestObject synchronize() throws IOException, NoSuchAlgorithmException {
        String response = v3.sendGetRequest("/zipato-web/v2/box/synchronize?ifNeeded=false&wait=true&timeout=180&_dc=1701702072041");
        return mapper.readValue(response, ResultRest.class);
    }

    private List<Attribute> getAttributes() {
        try {
//            String response = v3.sendGetRequest("/zipato-web/v2/attributes/full?network=false&device=true&endpoint=false&clusterEndpoint=false&definition=true&config=true&room=false&icons=true&value=false&parent=false&children=false&full=false&type=false");
            String response = v3.sendGetRequest("/zipato-web/v2/attributes/full?full=true");
            return mapper.readValue(response, new TypeReference<List<Attribute>>() {
            });
        } catch (Exception ex) {
            Log.e("Something went wrong when fetching attributes", ex.getMessage(), ex);
            return new ArrayList<>();
        }
    }

    private Attribute getAttribute(String attributeUuid) {
        try {
//            String response = v3.sendGetRequest("/zipato-web/v2/attributes/full?network=false&device=true&endpoint=false&clusterEndpoint=false&definition=true&config=true&room=false&icons=true&value=false&parent=false&children=false&full=false&type=false");
            String response = v3.sendGetRequest("/zipato-web/v2/attributes/" + attributeUuid + "?full=true");
            return mapper.readValue(response, Attribute.class);
        } catch (Exception ex) {
            Log.e("Something went wrong when fetching attributes", ex.getMessage(), ex);
            return null;
        }
    }

    private Attribute getAttributeAsync(String attributeUuid) {
        CompletableFuture<Attribute> futureAttribute = CompletableFuture.supplyAsync(() -> getAttribute(attributeUuid));
        try {
            return futureAttribute.get();
        } catch (Exception ex) {
            Log.e("Something went wrong when fetching attribute with uuid:" + attributeUuid, "Failed to fetch attribute");
            return null;
        }
    }

    private List<Attribute> getAttributeValues(boolean update) throws IOException, NoSuchAlgorithmException {
        String response = v3.sendGetRequest("/zipato-web/v2/attributes/values?update=" + update);
        return mapper.readValue(response, new TypeReference<List<Attribute>>() {
        });
    }

    public AttributeValueDto getAttributeValue(String attributeUuid) throws IOException, NoSuchAlgorithmException {
        String response = v3.sendGetRequest("/zipato-web/v2/attributes/" + attributeUuid + "/value");
        return mapper.readValue(response, AttributeValueDto.class);
    }

    private void setAttribute(String attributeUuid, AttributeValueDto attributeValue) {
        CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> {
            try {
                String body = mapper.writeValueAsString(attributeValue);
                v3.sendPutRequest("/zipato-web/v2/attributes/" + attributeUuid + "/value", body);
                return true;
            } catch (Exception ex) {
                Log.e("Failed to set attribute", ex.getMessage(), ex);
                return false;
            }
        });
        try {
            if (!future.get()) {
                Log.e("Failed to set attribute", "Failed to set attribute with uuid: " + attributeUuid);
            }
        } catch (Exception ex) {
            Log.e("Failed to set attribute", ex.getMessage(), ex);
        }
    }
}
