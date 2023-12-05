package com.example.smartdevicemanipulator.client;

import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class V3Client {
    public static V3Client v3 = new V3Client();
    private String jSessionId;
    private static final OkHttpClient client = new OkHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String URL_PREFIX = "https://admin.zipato.com";
    private static final String SESSION_INIT_URI = "/zipato-web/v2/user/init";
    private static final String SESSION_NOP_URI = "/zipato-web/v2/user/nop";
    private static final String SESSION_LOGIN_URI = "/zipato-web/v2/user/login";

    private static final String USERNAME = "msrecec+123456@3plus.hr";
    private static final String PASSWORD = "P3rk4nR4stur4";

    public V3Client() {
        this.jSessionId = null;
    }

    public SystemWebDto selectSystem() {
        try {
            String uuid = "c3ebc59c-a79a-497f-a54a-5f5db7e0143e";
            String response = sendGetRequest("/zipato-web/v2/systems/select?uuid=" + uuid);
            return mapper.readValue(response, SystemWebDto.class);
        } catch (Exception ex) {
            Log.e("Failed to select system", ex.getMessage(), ex);
            return null;
        }
    }

    public List<DeviceDto> getDevices() {
        try {
            String response = sendGetRequest("/zipato-web/v2/devices");
            return mapper.readValue(response, new TypeReference<List<DeviceDto>>() {
            });
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    public RestObject synchronize() throws IOException, NoSuchAlgorithmException {
        String response = sendGetRequest("/zipato-web/v2/box/synchronize?ifNeeded=false&wait=true&timeout=180&_dc=1701702072041");
        return mapper.readValue(response, ResultRest.class);
    }

    public List<Attribute> getAttributes() throws IOException, NoSuchAlgorithmException {
        String response = sendGetRequest("/zipato-web/v2/attributes/full?network=false&device=true&endpoint=false&clusterEndpoint=false&definition=true&config=true&room=false&icons=true&value=false&parent=false&children=false&full=false&type=false");
        return mapper.readValue(response, new TypeReference<List<Attribute>>() {
        });
    }

    public List<Attribute> getAttributeValues(boolean update) throws IOException, NoSuchAlgorithmException {
        String response = sendGetRequest("/zipato-web/v2/attributes/values?update=" + update);
        return mapper.readValue(response, new TypeReference<List<Attribute>>() {
        });
    }

    public AttributeValueDto getAttributeValue(String attributeUuid) throws IOException, NoSuchAlgorithmException {
        String response = sendGetRequest("/zipato-web/v2/attributes/" + attributeUuid + "/value");
        return mapper.readValue(response, AttributeValueDto.class);
    }

    public void setAttribute(String attributeUuid, AttributeValueDto attributeValue) throws IOException, NoSuchAlgorithmException {
        String body = mapper.writeValueAsString(attributeValue);
        sendPutRequest("/zipato-web/v2/attributes/" + attributeUuid + "/value", body);
    }


    // Helper methods

    private String sendPostRequest(String uri, String request) throws IOException, NoSuchAlgorithmException {
        return sendRequest(uri, "POST", request);
    }

    private String sendPutRequest(String uri, String request) throws IOException, NoSuchAlgorithmException {
        return sendRequest(uri, "PUT", request);
    }

    private String sendGetRequest(String uri, String request) throws IOException, NoSuchAlgorithmException {
        return sendRequest(uri, "GET", request);
    }

    private String sendGetRequest(String uri) throws IOException, NoSuchAlgorithmException {
        return sendRequest(uri, "GET", null);
    }

    private String sendRequest(String uri, String method, String body) throws IOException, NoSuchAlgorithmException {
        Response response = send(uri, method, body);
        if (!response.isSuccessful()) {
            if (response.code() == 401) {
                login();
                response = send(uri, method, body);
            }
            if (!response.isSuccessful()) {
                if ((response.code() % 500 >= 0) && (response.code() % 500 <= 10)) {
                    throw new V3UnreachableException("V3 service unreachable");
                }
                throw new IllegalArgumentException("Request unsuccessful: " + response.body().string());
            }
        }
        String res = response.body().string();
        return res;
    }

    Response send(String uri, String method, String body) throws IOException, NoSuchAlgorithmException {
        if (this.jSessionId == null) {
            login();
        }
        RequestBody rb = body == null ? null : RequestBody.create(body, MediaType.get("application/json"));
        Request request = new Request.Builder()
                .method(method, rb)
                .url(URL_PREFIX + uri)
                .addHeader("Cookie", "JSESSIONID=" + this.jSessionId)
                .build();
        Call call = client.newCall(request);
        return call.execute();
    }

    private synchronized String login() throws IOException, NoSuchAlgorithmException {
        if (this.jSessionId != null) {
            if (nop(this.jSessionId)) {
                return this.jSessionId;
            }
        }
        ZipatoSession session = sessionInit();
        String token = Sha1Utils.sha1(session.getNonce() + Sha1Utils.sha1(PASSWORD));
        HttpUrl.Builder urlBuilder = HttpUrl.parse(URL_PREFIX + SESSION_LOGIN_URI).newBuilder();
        urlBuilder.addQueryParameter("username", USERNAME);
        urlBuilder.addQueryParameter("token", token);
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .get()
                .url(url)
                .addHeader("Cookie", "JSESSIONID=" + session.getJsessionid())
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        String json = Objects.requireNonNull(response.body()).string();
        session = mapper.readValue(json, ZipatoSession.class);
        if (session.getError() != null || (session.getSuccess() != null && !session.getSuccess())) {
            throw new RuntimeException("Failed to login " + (session.getError() != null ? session.getError() : ""));
        }
        this.jSessionId = session.getJsessionid();
        return session.getJsessionid();
    }

    private boolean nop(String jSessionId) throws IOException {
        Request request = new Request.Builder()
                .get()
                .url(URL_PREFIX + SESSION_NOP_URI)
                .addHeader("Cookie", "JSESSIONID=" + jSessionId)
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        return response.code() == 200;
    }

    private ZipatoSession sessionInit() throws IOException {
        Request request = new Request.Builder()
                .get()
                .url(URL_PREFIX + SESSION_INIT_URI)
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        String json = Objects.requireNonNull(response.body()).string();
        return mapper.readValue(json, ZipatoSession.class);
    }

}
