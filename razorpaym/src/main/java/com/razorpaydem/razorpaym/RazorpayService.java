package com.razorpaydem.razorpaym;
import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RazorpayService {

    private final OkHttpClient httpClient = new OkHttpClient();

    @Value("${razorpay.key}")
    private String razorpayKey;

    @Value("${razorpay.secret}")
    private String razorpaySecret;

    public String createOrder(int amount, String currency) throws IOException {
        String url = "https://api.razorpay.com/v1/orders";

        // Create the JSON request body
        String json = "{\n" +
                "  \"amount\": " + amount + ",\n" +
                "  \"currency\": \"" + currency + "\",\n" +
                "  \"payment_capture\": 1\n" +
                "}";

        RequestBody body = RequestBody.create(
                json,
                MediaType.get("application/json; charset=utf-8")
        );

        // Create the request
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Authorization", Credentials.basic(razorpayKey, razorpaySecret))
                .build();

        // Execute the request
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return response.body().string();
        }
    }
}

