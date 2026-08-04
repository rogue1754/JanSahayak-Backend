package JanSahayak.Hackathon.Controller;

import JanSahayak.Hackathon.Service.SchemeEnrichmentLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/enrichment")
@RequiredArgsConstructor
public class SchemeEnrichmentController {

    private final SchemeEnrichmentLoader schemeEnrichmentLoader;

    @PostMapping("/batch1")
    public ResponseEntity<String> enrichBatch1() {

        schemeEnrichmentLoader.loadBatch1();

        return ResponseEntity.ok(
                "Batch 1 enrichment completed."
        );
    }
}