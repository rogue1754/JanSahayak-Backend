package JanSahayak.Hackathon.Controller;

import JanSahayak.Hackathon.DTOs.DocumentSeedDTO;
import JanSahayak.Hackathon.DTOs.ExtractedEligibilityRule;
import JanSahayak.Hackathon.DTOs.SchemeSeedDTO;
import JanSahayak.Hackathon.Entities.Scheme;
import JanSahayak.Hackathon.Enums.SchemeCategory;
import JanSahayak.Hackathon.Enums.SchemeLevel;
import JanSahayak.Hackathon.Enums.SourceType;
import JanSahayak.Hackathon.Service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class GeminiTestController {
    @Autowired
    private final SchemeSeedLoader schemeSeedLoader;
    @Autowired
    private final SchemeIngestionService schemeIngestionService;
    @Autowired
    private final MySchemeClient mySchemeClient;
    @Autowired
    private final GeminiService geminiService;
    @Autowired
    private final WebPageFetcherService webPageFetcherService;
    @Autowired
    private final EligibilityExtractionService eligibilityExtractionService;

    @GetMapping("/gemini")
    public ResponseEntity<String> testGemini() {

        return ResponseEntity.ok(
                geminiService.testConnection()
        );
    }
    @GetMapping("/fetch")
    public ResponseEntity<String> testFetch() {

        String url =
                "https://www.india.gov.in/category/money-taxes/subcategory/banking-insurance/details/stand-up-india-scheme";

        return ResponseEntity.ok(
                webPageFetcherService.fetchPage(url)
        );
    }
    @PostMapping("/test-ingestion")
    public Scheme testIngestion() {

        SchemeSeedDTO dto = SchemeSeedDTO.builder()
                .name("Test Student Scholarship")
                .slug("test-student-scholarship")
                .description("Scholarship for eligible students.")
                .benefits("Financial assistance for education.")
                .category(SchemeCategory.EDUCATION)
                .ministry("Test Ministry")
                .schemeLevel(SchemeLevel.CENTRAL)
                .state(null)
                .applicationUrl("https://example.com/apply")
                .sourceUrl("https://example.com")
                .sourceType(SourceType.PENDING_REVIEW)
                .active(true)

                .eligibilityText("""
                    The applicant must be at least 18 years old.
                    The applicant's annual family income must be less than
                    or equal to 250000 rupees.
                    The applicant must have scored at least 60 percent marks.
                    """)

                .documents(List.of(
                        DocumentSeedDTO.builder()
                                .name("Aadhaar Card")
                                .description("Identity proof")
                                .mandatory(true)
                                .build(),

                        DocumentSeedDTO.builder()
                                .name("Income Certificate")
                                .description("Proof of annual family income")
                                .mandatory(true)
                                .build()
                ))

                .build();

        return schemeIngestionService.ingest(dto);
    }
    @GetMapping("/myscheme")
    public ResponseEntity<String> testMyScheme() {

        return ResponseEntity.ok(
                mySchemeClient.getSchemeBySlug("sui")
        );
    }
    @GetMapping("/extract-eligibility")
    public ResponseEntity<List<ExtractedEligibilityRule>> testEligibilityExtraction() {

        String eligibilityText = """
            Finance is provided for Greenfield Enterprises.
            If the applicant is a male, he must be from SC / ST category.
            The age of the applicant must be at least 18 years.
            The applicant must not be in default to any bank/financial institution.
            """;

        return ResponseEntity.ok(
                eligibilityExtractionService
                        .extractEligibilityRules(eligibilityText)
        );
    }
    @PostMapping("/seed")
    public ResponseEntity<String> seedSchemes() {

        schemeSeedLoader.loadSchemes();

        return ResponseEntity.ok("Schemes seeded successfully");
    }
}