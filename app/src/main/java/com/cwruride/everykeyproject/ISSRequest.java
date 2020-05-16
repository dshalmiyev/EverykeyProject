package com.cwruride.everykeyproject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class ISSRequest {

    private double longitude;
    private double latitude;
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private OkHttpClient client = new OkHttpClient();

    private ISSRequest(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public static ISSRequest makeRequest(double longitude, double latitude) {
        return new ISSRequest(longitude, latitude);
    }

    String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public String sendRequest() throws IOException {
        String response = this.get("http://api.open-notify.org/iss-pass.json?lat=" + this.latitude + "&lon=" + this.longitude);
        System.out.println(response);
        return response;
    }
}
