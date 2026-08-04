package JanSahayak.Hackathon.Service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import JanSahayak.Hackathon.DTOs.ExtractedEligibilityRule;

import java.util.List;

@Service
public class EligibilityExtractionService {

    private final Client client;
    private final ObjectMapper objectMapper;

    public EligibilityExtractionService(
            @Value("${gemini.api.key}") String apiKey) {

        this.client = Client.builder()
                .apiKey(apiKey)
                .build();

        this.objectMapper = new ObjectMapper();
    }

    public List<ExtractedEligibilityRule> extractEligibilityRules(String eligibilityText) {
        String prompt = """
                You are an eligibility-rule extraction system for Indian
                government schemes.

                Your job is ONLY to convert the provided eligibility text
                into structured JSON rules.

                DO NOT decide whether a user is eligible.
                DO NOT invent criteria.
                DO NOT infer criteria that are not explicitly stated.

                Allowed field values:
                AGE
                ANNUAL_INCOME
                GENDER
                STATE
                OCCUPATION
                EDUCATION
                CATEGORY
                MARKS_PERCENTAGE
                LAND_OWNER
                LAND_SIZE
                DISABILITY_PERCENTAGE

                Allowed operator values:
                EQUALS
                NOT_EQUALS
                GREATER_THAN
                GREATER_THAN_OR_EQUAL
                LESS_THAN
                LESS_THAN_OR_EQUAL
                IN
                NOT_IN

                Return ONLY a JSON array.

                Each object must have exactly:

                {
                  "field": string or null,
                  "operator": string or null,
                  "expectedValue": string or null,
                  "mandatory": boolean,
                  "rawRule": string,
                  "machineEvaluable": boolean
                }

                RULES:

                1. field must be one of the allowed field values or null.

                2. operator must be one of the allowed operator values or null.

                3. If a criterion cannot be represented safely using the
                   allowed fields and operators:
                   - field = null
                   - operator = null
                   - expectedValue = null
                   - machineEvaluable = false

                4. Preserve the original criterion in rawRule.

                5. Do not discard criteria merely because they are not
                   machine evaluable.

                6. Do not combine unrelated eligibility criteria.

                7. Never create information not present in the source.

                ELIGIBILITY TEXT:

                %s
                """.formatted(eligibilityText);

        GenerateContentResponse response =
                client.models.generateContent(
                        "gemini-3.6-flash",
                        prompt,
                        null
                );

        String json=response.text();
        try {

            // Gemini may occasionally wrap JSON in ```json ... ```
            json = json
                    .replace("```json", "")
                    .replace("```", "")
                    .trim();

            return objectMapper.readValue(
                    json,
                    new TypeReference<List<ExtractedEligibilityRule>>() {}
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to parse Gemini eligibility response",
                    e
            );
        }
    }
}