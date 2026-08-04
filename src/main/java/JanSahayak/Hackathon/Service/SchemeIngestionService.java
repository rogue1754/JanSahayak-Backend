package JanSahayak.Hackathon.Service;

import JanSahayak.Hackathon.DTOs.DocumentSeedDTO;
import JanSahayak.Hackathon.DTOs.ExtractedEligibilityRule;
import JanSahayak.Hackathon.DTOs.SchemeSeedDTO;
import JanSahayak.Hackathon.Entities.RequiredDocument;
import JanSahayak.Hackathon.Entities.Scheme;
import JanSahayak.Hackathon.Repository.RequiredDocumentRepo;
import JanSahayak.Hackathon.Repository.SchemeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchemeIngestionService {

    private final SchemeRepo schemeRepo;
    private final RequiredDocumentRepo requiredDocumentRepo;

    private final EligibilityExtractionService eligibilityExtractionService;
    private final EligibilityRuleService eligibilityRuleService;

    @Transactional
    public Scheme ingest(SchemeSeedDTO dto) {

        // 1. Save basic scheme
        Scheme scheme = Scheme.builder()
                .name(dto.getName())
                .slug(dto.getSlug())
                .description(dto.getDescription())
                .benefits(dto.getBenefits())
                .category(dto.getCategory())
                .ministry(dto.getMinistry())
                .schemeLevel(dto.getSchemeLevel())
                .state(dto.getState())
                .applicationUrl(dto.getApplicationUrl())
                .applicationDeadline(dto.getApplicationDeadline())
                .sourceUrl(dto.getSourceUrl())
                .sourceType(dto.getSourceType())
                .active(dto.getActive())
                .build();

        scheme = schemeRepo.save(scheme);

        // 2. Save required documents
        if (dto.getDocuments() != null) {

            for (DocumentSeedDTO documentDTO : dto.getDocuments()) {

                RequiredDocument document = RequiredDocument.builder()
                        .scheme(scheme)
                        .name(documentDTO.getName())
                        .description(documentDTO.getDescription())
                        .mandatory(documentDTO.isMandatory())
                        .build();

                requiredDocumentRepo.save(document);
            }
        }

// 3. Handle eligibility rules

        if (dto.getEligibilityRules() != null
                && !dto.getEligibilityRules().isEmpty()) {

            // Rules already exist in JSON -> NO GEMINI CALL
            eligibilityRuleService.saveExtractedRules(
                    scheme,
                    dto.getEligibilityRules()
            );

        } else if (dto.getEligibilityText() != null
                && !dto.getEligibilityText().isBlank()) {

            // Raw eligibility text -> use Gemini
            List<ExtractedEligibilityRule> extractedRules =
                    eligibilityExtractionService.extractEligibilityRules(
                            dto.getEligibilityText()
                    );

            eligibilityRuleService.saveExtractedRules(
                    scheme,
                    extractedRules
            );
        }
        return scheme;
    }
}