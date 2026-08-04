package JanSahayak.Hackathon.Service;

import JanSahayak.Hackathon.DTOs.EligibilityRequest;
import JanSahayak.Hackathon.DTOs.RankedSchemeDTO;
import JanSahayak.Hackathon.Entities.EligibilityRule;
import JanSahayak.Hackathon.Entities.Scheme;
import JanSahayak.Hackathon.Enums.EligibilityField;
import JanSahayak.Hackathon.Repository.EligibilityRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchemeRankingService {

    private final EligibilityMatchingService eligibilityMatchingService;
    private final EligibilityRuleRepository eligibilityRuleRepo;
    private final EligibilityRuleEvaluator ruleEvaluator;

    public List<RankedSchemeDTO> getRankedSchemes(
            EligibilityRequest user
    ) {

        // Only schemes the user is already eligible for
        List<Scheme> eligibleSchemes =
                eligibilityMatchingService.findEligibleSchemes(user);

        List<RankedSchemeDTO> rankedSchemes = new ArrayList<>();

        for (Scheme scheme : eligibleSchemes) {

            List<EligibilityRule> rules =
                    eligibilityRuleRepo.findBySchemeId(scheme.getId());

            int relevanceScore=0;

            int matchedCriteria = 0;
            int totalCriteria = 0;

            for (EligibilityRule rule : rules) {

                // Ignore rules Gemini could not safely structure
                if (rule.getField() == null ||
                        rule.getOperator() == null) {
                    continue;
                }

                totalCriteria++;

                if (ruleEvaluator.evaluate(rule, user)) {
                    matchedCriteria++;
                    relevanceScore+=getWeight(rule.getField());
                }
            }

            int score = Math.min(relevanceScore,100);

            rankedSchemes.add(
                    RankedSchemeDTO.builder()
                            .scheme(scheme)
                            .matchScore(score)
                            .matchedCriteria(matchedCriteria)
                            .totalCriteria(totalCriteria)
                            .build()
            );
        }

        // Highest score first
        rankedSchemes.sort(
                Comparator.comparingInt(
                        RankedSchemeDTO::getMatchScore
                ).reversed()
        );

        return rankedSchemes;
    }

    private int getWeight(EligibilityField field) {

        return switch (field) {

            // Very strong targeting
            case CATEGORY -> 20;

            case DISABLED -> 20;

            case MINORITY -> 20;

            case STUDENT -> 15;

            // Geographic targeting
            case STATE -> 15;

            case CITY -> 10;

            // Personal targeting
            case GENDER -> 15;

            case EMPLOYMENT_STATUS -> 15;

            // Financial
            case ANNUAL_INCOME -> 10;

            // Education
            case HIGHEST_EDUCATION -> 10;

            // Broad criterion
            case AGE -> 5;
        };
    }
}