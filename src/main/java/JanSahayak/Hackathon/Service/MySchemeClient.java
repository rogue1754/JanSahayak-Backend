package JanSahayak.Hackathon.Service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class MySchemeClient {

    private static final String BASE_URL =
            "https://api.myscheme.gov.in/schemes/v6/public/schemes";

    private final HttpClient httpClient = HttpClient.newHttpClient();

    public String getSchemeBySlug(String slug) {

        String url = BASE_URL + "?slug=" + slug + "&lang=en";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .GET()
                .build();

        try {

            HttpResponse<String> response =
                    httpClient.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            if (response.statusCode() != 200) {
                throw new RuntimeException(
                        "myScheme API failed. Status: "
                                + response.statusCode()
                );
            }

            return response.body();

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to connect to myScheme API",
                    e
            );

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            throw new RuntimeException(
                    "myScheme request interrupted",
                    e
            );
        }
    }
}