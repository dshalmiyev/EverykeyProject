package com.cwruride.everykeyproject;

import org.jetbrains.annotations.NotNull;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class ISSRequest {

    private double longitude;
    private double latitude;
    private String output;
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
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                output = response.body().string();
            }
        });
        return output;
    }

    public String sendRequest() throws IOException {
        String response = this.get("http://api.open-notify.org/iss-pass.json?lat=" + this.latitude + "&lon=" + this.longitude);
        System.out.println(response);
        return response;
    }
}
