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
        // Reject only what HttpRequest would reject (control characters, DEL, chars above 0xFF), so no key format HeyGen
        // might adopt is blocked here; HeyGen itself decides validity. HttpRequest's own error echoes the key into logs.
        if (!apiKey.chars().allMatch(c -> c == '\t' || (c >= 0x20 && c != 0x7F && c <= 0xFF))) {
            throw new IllegalStateException("HEYGEN_API_KEY contains control characters (e.g. arrow keys pressed while entering it)");
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

