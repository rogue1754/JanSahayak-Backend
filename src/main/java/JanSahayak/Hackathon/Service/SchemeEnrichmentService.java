package JanSahayak.Hackathon.Service;

import JanSahayak.Hackathon.DTOs.DocumentSeedDTO;
import JanSahayak.Hackathon.DTOs.ExtractedEligibilityRule;
import JanSahayak.Hackathon.DTOs.SchemeEnrichmentDTO;
import JanSahayak.Hackathon.Entities.EligibilityRule;
import JanSahayak.Hackathon.Entities.RequiredDocument;
import JanSahayak.Hackathon.Entities.Scheme;
import JanSahayak.Hackathon.Repository.EligibilityRuleRepository;
import JanSahayak.Hackathon.Repository.RequiredDocumentRepo;
import JanSahayak.Hackathon.Repository.SchemeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SchemeEnrichmentService {

    private final SchemeRepo schemeRepo;
    private final EligibilityRuleRepository eligibilityRuleRepository;
    private final RequiredDocumentRepo requiredDocumentRepo;

    @Transactional
    public void enrich(SchemeEnrichmentDTO dto) {

        // 1. Find EXISTING scheme
        Scheme scheme = schemeRepo.findByName(dto.getSchemeName())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Scheme not found: " + dto.getSchemeName()
                        )
                );


        // =====================================================
        // 2. UPDATE SCHEME METADATA
        // =====================================================

        if (dto.getDescription() != null) {
            scheme.setDescription(dto.getDescription());
        }

        if (dto.getBenefits() != null) {
            scheme.setBenefits(dto.getBenefits());
        }

        if (dto.getCategory() != null) {
            scheme.setCategory(dto.getCategory());
        }

        if (dto.getMinistry() != null) {
            scheme.setMinistry(dto.getMinistry());
        }

        if (dto.getSchemeLevel() != null) {
            scheme.setSchemeLevel(dto.getSchemeLevel());
        }

        /*
         * State schemes -> set state.
         * Central schemes -> state should normally remain null.
         */
        if (dto.getState() != null) {
            scheme.setState(dto.getState());
        }

        if (dto.getApplicationUrl() != null) {
            scheme.setApplicationUrl(dto.getApplicationUrl());
        }

        if (dto.getApplicationDeadline() != null) {
            scheme.setApplicationDeadline(dto.getApplicationDeadline());
        }

        if (dto.getSourceUrl() != null) {
            scheme.setSourceUrl(dto.getSourceUrl());
        }

        if (dto.getSourceType() != null) {
            scheme.setSourceType(dto.getSourceType());
        }

        if (dto.getActive() != null) {
            scheme.setActive(dto.getActive());
        }

        schemeRepo.save(scheme);


        // =====================================================
        // 3. ADD ELIGIBILITY RULES ONLY IF NONE EXIST
        // =====================================================

        if (!eligibilityRuleRepository.existsBySchemeId(scheme.getId())
                && dto.getEligibilityRules() != null) {

            for (ExtractedEligibilityRule ruleDTO :
                    dto.getEligibilityRules()) {

                EligibilityRule rule = EligibilityRule.builder()
                        .scheme(scheme)
                        .field(ruleDTO.getField())
                        .operator(ruleDTO.getOperator())
                        .expectedValue(ruleDTO.getExpectedValue())
                        .mandatory(ruleDTO.getMandatory())
                        .rawRule(ruleDTO.getRawRule())
                        .build();

                eligibilityRuleRepository.save(rule);
            }
        }


        // =====================================================
        // 4. ADD DOCUMENTS ONLY IF NONE EXIST
        // =====================================================

        if (!requiredDocumentRepo.existsBySchemeId(scheme.getId())
                && dto.getDocuments() != null) {

            for (DocumentSeedDTO documentDTO : dto.getDocuments()) {

                RequiredDocument document =
                        RequiredDocument.builder()
                                .scheme(scheme)
                                .name(documentDTO.getName())
                                .description(documentDTO.getDescription())
                                .mandatory(documentDTO.isMandatory())
                                .build();

                requiredDocumentRepo.save(document);
            }
        }

        System.out.println(
                "ENRICHED: " + scheme.getName()
        );
    }
}