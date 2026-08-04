package JanSahayak.Hackathon.Controller;
import JanSahayak.Hackathon.DTOs.CreateSchemeRequest;
import JanSahayak.Hackathon.DTOs.SchemeResponse;
import JanSahayak.Hackathon.Service.SchemeService;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schemes")
@RequiredArgsConstructor
public class SchemeController {

    private final SchemeService schemeService;
    @PostMapping
    public ResponseEntity<SchemeResponse> createScheme(
            @RequestBody CreateSchemeRequest request) {

        SchemeResponse response =
                schemeService.createScheme(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<SchemeResponse> getSchemeById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                schemeService.getSchemeById(id)
        );
    }
    @GetMapping
    public ResponseEntity<List<SchemeResponse>> getAllSchemes() {

        return ResponseEntity.ok(
                schemeService.getAllSchemes()
        );
    }
}
