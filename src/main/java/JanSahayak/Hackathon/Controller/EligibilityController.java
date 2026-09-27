package JanSahayak.Hackathon.Controller;

import JanSahayak.Hackathon.DTOs.EligibilityRequest;
import JanSahayak.Hackathon.DTOs.RankedSchemeDTO;
import JanSahayak.Hackathon.Service.SchemeRankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eligibility")
@RequiredArgsConstructor
public class EligibilityController {

    private final SchemeRankingService schemeRankingService;
    @PostMapping("/match")
    public ResponseEntity<List<RankedSchemeDTO>> matchSchemes(
            @RequestBody EligibilityRequest request) {

        return ResponseEntity.ok(
                schemeRankingService.getRankedSchemes(request)
        );
    }
}