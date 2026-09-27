package JanSahayak.Hackathon.Service;

import JanSahayak.Hackathon.DTOs.EligibilityRuleResponse;
import JanSahayak.Hackathon.DTOs.RequiredDocumentResponse;
import JanSahayak.Hackathon.DTOs.SchemeResponse;
import JanSahayak.Hackathon.Entities.Scheme;
import JanSahayak.Hackathon.Repository.EligibilityRuleRepository;
import JanSahayak.Hackathon.Repository.RequiredDocumentRepo;
import JanSahayak.Hackathon.Repository.SchemeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchemeService {

    private final SchemeRepo schemeRepository;
    private final EligibilityRuleRepository eligibilityRuleRepository;
    private final RequiredDocumentRepo requiredDocumentRepository;

    private SchemeResponse toResponse(Scheme scheme) {
        List<EligibilityRuleResponse> rules =
                eligibilityRuleRepository.findBySchemeId(scheme.getId())
                        .stream()
                        .map(rule -> EligibilityRuleResponse.builder()
                                .id(rule.getId())
                                .field(rule.getField())
                                .operator(rule.getOperator())
                                .expectedValue(rule.getExpectedValue())
                                .mandatory(rule.getMandatory())
                                .build())
                        .toList();

        List<RequiredDocumentResponse> documents =
                requiredDocumentRepository.findBySchemeId(scheme.getId())
                        .stream()
                        .map(document -> RequiredDocumentResponse.builder()
                                .id(document.getId())
                                .name(document.getName())
                                .description(document.getDescription())
                                .mandatory(document.isMandatory())
                                .build())
                        .toList();

        return SchemeResponse.builder()
                .id(scheme.getId())
                .name(scheme.getName())
                .description(scheme.getDescription())
                .benefits(scheme.getBenefits())
                .category(scheme.getCategory())
                .ministry(scheme.getMinistry())
                .schemeLevel(scheme.getSchemeLevel())
                .state(scheme.getState())
                .applicationUrl(scheme.getApplicationUrl())
                .applicationDeadline(scheme.getApplicationDeadline())
                .sourceUrl(scheme.getSourceUrl())
                .sourceType(scheme.getSourceType())
                .active(scheme.getActive())
                .lastUpdated(scheme.getLastUpdated())
                .eligibilityRules(rules)
                .requiredDocuments(documents)
                .build();
    }

    public SchemeResponse getSchemeById(Long id) {
        Scheme scheme = schemeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Scheme not found with id: " + id));

        return toResponse(scheme);
    }

    public List<SchemeResponse> getAllSchemes() {
        return schemeRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }
}
