package JanSahayak.Hackathon.Service;

import JanSahayak.Hackathon.DTOs.CreateSchemeRequest;
import JanSahayak.Hackathon.DTOs.EligibilityRuleResponse;
import JanSahayak.Hackathon.DTOs.RequiredDocumentResponse;
import JanSahayak.Hackathon.DTOs.SchemeResponse;
import JanSahayak.Hackathon.Entities.EligibilityRule;
import JanSahayak.Hackathon.Entities.RequiredDocument;
import JanSahayak.Hackathon.Entities.Scheme;
import JanSahayak.Hackathon.Repository.EligibilityRuleRepository;
import JanSahayak.Hackathon.Repository.RequiredDocumentRepo;
import JanSahayak.Hackathon.Repository.SchemeRepo;
import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchemeService {

    @Autowired
    private final SchemeRepo schemeRepository;
    @Autowired
    private final EligibilityRuleRepository eligibilityRuleRepository;
    @Autowired
    private final RequiredDocumentRepo requiredDocumentRepository;
    @Transactional
    public SchemeResponse createScheme(CreateSchemeRequest request) {

        Scheme scheme = Scheme.builder()
                .name(request.getName())
                .description(request.getDescription())
                .benefits(request.getBenefits())
                .category(request.getCategory())
                .ministry(request.getMinistry())
                .schemeLevel(request.getSchemeLevel())
                .state(request.getState())
                .applicationUrl(request.getApplicationUrl())
                .applicationDeadline(request.getApplicationDeadline())
                .sourceUrl(request.getSourceUrl())
                .sourceType(request.getSourceType())
                .active(request.isActive())
                .build();

        Scheme savedScheme = schemeRepository.save(scheme);
        if (request.getEligibilityRules() != null) {
            List<EligibilityRule> rules =
                    request.getEligibilityRules()
                            .stream()
                            .map(ruleRequest ->
                                    EligibilityRule.builder()
                                            .scheme(savedScheme)
                                            .field(ruleRequest.getField())
                                            .operator(ruleRequest.getOperator())
                                            .expectedValue(ruleRequest.getExpectedValue())
                                            .mandatory(ruleRequest.isMandatory())
                                            .build()
                            )
                            .toList();

            eligibilityRuleRepository.saveAll(rules);
        }

        if (request.getRequiredDocuments() != null) {

            List<RequiredDocument> documents =
                    request.getRequiredDocuments()
                            .stream()
                            .map(documentRequest ->
                                    RequiredDocument.builder()
                                            .scheme(savedScheme)
                                            .name(documentRequest.getName())
                                            .description(documentRequest.getDescription())
                                            .mandatory(documentRequest.isMandatory())
                                            .build()
                            )
                            .toList();
            requiredDocumentRepository.saveAll(documents);
        }
        return toResponse(savedScheme);
    }
    private SchemeResponse toResponse(Scheme scheme) {

        List<EligibilityRuleResponse> rules =
                eligibilityRuleRepository
                        .findBySchemeId(scheme.getId())
                        .stream()
                        .map(rule ->
                                EligibilityRuleResponse.builder()
                                        .id(rule.getId())
                                        .field(rule.getField())
                                        .operator(rule.getOperator())
                                        .expectedValue(rule.getExpectedValue())
                                        .mandatory(rule.getMandatory())
                                        .build()
                        )
                        .toList();


        List<RequiredDocumentResponse> documents =
                requiredDocumentRepository
                        .findBySchemeId(scheme.getId())
                        .stream()
                        .map(document ->
                                RequiredDocumentResponse.builder()
                                        .id(document.getId())
                                        .name(document.getName())
                                        .description(document.getDescription())
                                        .mandatory(document.isMandatory())
                                        .build()
                        )
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
                .orElseThrow(() ->
                        new RuntimeException("Scheme not found with id: " + id)
                );

        return toResponse(scheme);
    }
    public List<SchemeResponse> getAllSchemes() {

        return schemeRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }
}
