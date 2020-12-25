package org.pismery.javacourse.rpc.core.http;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;
import java.util.Objects;

public class OkHttpClient {
    private static final MediaType JSON_TYPE = MediaType.get("application/json; charset=utf-8");

    public static String request(String url, String requestBody) throws IOException {
        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(JSON_TYPE, requestBody))
                .build();
        return Objects.requireNonNull(client.newCall(request).execute().body()).string();
    }

}
