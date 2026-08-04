package JanSahayak.Hackathon.Service;

import JanSahayak.Hackathon.DTOs.SchemeSeedDTO;
import JanSahayak.Hackathon.Repository.SchemeRepo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchemeSeedLoader {
    private final SchemeIngestionService schemeIngestionService;
    private final SchemeRepo schemeRepo;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void loadSchemes() {

        try {

            InputStream inputStream =
                    new ClassPathResource(
                            "data/schemes-seed.json"
                    ).getInputStream();

            List<SchemeSeedDTO> schemes =
                    objectMapper.readValue(
                            inputStream,
                            new TypeReference<List<SchemeSeedDTO>>() {}
                    );

            for (SchemeSeedDTO scheme : schemes) {

                try {

                    if (schemeRepo.existsByName(scheme.getName())) {
                        System.out.println(
                                "SKIPPING: " + scheme.getName()
                        );
                        continue;
                    }

                    schemeIngestionService.ingest(scheme);

                    System.out.println(
                            "SEEDED: " + scheme.getName()
                    );

                } catch (Exception e) {

                    System.err.println(
                            "FAILED: " + scheme.getName()
                    );

                    System.err.println(
                            "Reason: " + e.getMessage()
                    );
                }
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to load scheme seed data",
                    e
            );
        }
    }
}