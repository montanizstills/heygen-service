package io.github.montanizstills.http;

import io.github.montanizstills.heygenAPI.HeygenUtils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public enum SimpleHttp {
    GET, POST, PUT, DELETE;

    public SimpleHttpResponse sendRequest(String uriInput, String requestBody) {

        String apiKey = new HeygenUtils().getHeygen_api_key();
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("HEYGEN_API_KEY is not set");
        }
        apiKey = apiKey.strip();
        // Never echo the key: HttpRequest's own header error includes the value, which ends up in logs and test reports.
        if (!apiKey.chars().allMatch(c -> c > 0x20 && c < 0x7F)) {
            throw new IllegalStateException("HEYGEN_API_KEY contains spaces or non-printable characters (e.g. arrow keys pressed while entering it)");
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(uriInput))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("x-api-key", apiKey);

        switch (this.name()) {
            case "GET":
                requestBuilder.GET();
                break;
            case "POST":
                requestBuilder.POST(HttpRequest.BodyPublishers.ofString(
                        requestBody != null ? requestBody : ""));
                break;
            case "PUT":
                requestBuilder.PUT(HttpRequest.BodyPublishers.ofString(
                        requestBody != null ? requestBody : ""));
                break;
            case "DELETE":
                requestBuilder.DELETE();
                break;
        }

        HttpRequest request = requestBuilder.build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new Error(e);
        }

        // Handle response as needed
        return new SimpleHttpResponse(response.body(),response.statusCode());

    }
}

