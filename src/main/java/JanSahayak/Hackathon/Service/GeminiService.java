package JanSahayak.Hackathon.Service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {

    private final Client client;

    public GeminiService(
            @Value("${gemini.api.key}") String apiKey) {

        this.client = Client.builder()
                .apiKey(apiKey)
                .build();
    }

    public String testConnection() {

        GenerateContentResponse response =
                client.models.generateContent(
                        "gemini-3.6-flash",
                        "Reply with exactly: GEMINI_CONNECTED",
                        null
                );

        return response.text();
    }
}