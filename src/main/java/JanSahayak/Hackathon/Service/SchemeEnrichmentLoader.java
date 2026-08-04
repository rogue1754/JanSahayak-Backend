package JanSahayak.Hackathon.Service;

import JanSahayak.Hackathon.DTOs.SchemeEnrichmentDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchemeEnrichmentLoader {
    private final SchemeEnrichmentService schemeEnrichmentService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void loadBatch1() {

        try {

            InputStream inputStream =
                    new ClassPathResource(
                            "data/batch-1-enrichment.json"
                    ).getInputStream();

            List<SchemeEnrichmentDTO> schemes =
                    objectMapper.readValue(
                            inputStream,
                            new TypeReference<List<SchemeEnrichmentDTO>>() {}
                    );

            int success = 0;
            int failed = 0;

            for (SchemeEnrichmentDTO scheme : schemes) {

                try {

                    schemeEnrichmentService.enrich(scheme);

                    System.out.println(
                            "ENRICHED: " + scheme.getSchemeName()
                    );

                    success++;

                } catch (Exception e) {

                    System.err.println(
                            "FAILED: " + scheme.getSchemeName()
                    );

                    System.err.println(
                            "Reason: " + e.getMessage()
                    );

                    failed++;
                }
            }

            System.out.println("----------------------------");
            System.out.println("BATCH 1 COMPLETE");
            System.out.println("SUCCESS: " + success);
            System.out.println("FAILED: " + failed);
            System.out.println("----------------------------");

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to load enrichment batch",
                    e
            );
        }
    }
}