package JanSahayak.Hackathon.Controller;
import JanSahayak.Hackathon.DTOs.SchemeResponse;
import JanSahayak.Hackathon.Service.SchemeService;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schemes")
@RequiredArgsConstructor
public class SchemeController {

    private final SchemeService schemeService;
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
