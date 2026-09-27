package JanSahayak.Hackathon.Service;

import JanSahayak.Hackathon.DTOs.DocumentSeedDTO;
import JanSahayak.Hackathon.DTOs.SchemeSeedDTO;
import JanSahayak.Hackathon.Entities.RequiredDocument;
import JanSahayak.Hackathon.Entities.Scheme;
import JanSahayak.Hackathon.Repository.RequiredDocumentRepo;
import JanSahayak.Hackathon.Repository.SchemeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class SchemeIngestionService {

    private final SchemeRepo schemeRepo;
    private final RequiredDocumentRepo requiredDocumentRepo;

    private final EligibilityRuleService eligibilityRuleService;

    @Transactional
    public void ingest(SchemeSeedDTO dto) {

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

// 3. Save structured eligibility rules
        if (dto.getEligibilityRules() != null
                && !dto.getEligibilityRules().isEmpty()) {

            eligibilityRuleService.saveExtractedRules(
                    scheme,
                    dto.getEligibilityRules()
            );
        }
    }
}