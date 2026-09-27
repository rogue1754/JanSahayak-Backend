package JanSahayak.Hackathon.Service;

import JanSahayak.Hackathon.DTOs.ExtractedEligibilityRule;
import JanSahayak.Hackathon.Entities.EligibilityRule;
import JanSahayak.Hackathon.Entities.Scheme;
import JanSahayak.Hackathon.Repository.EligibilityRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EligibilityRuleService {

    private final EligibilityRuleRepository eligibilityRuleRepo;

    public void saveExtractedRules(
            Scheme scheme,
            List<ExtractedEligibilityRule> extractedRules) {

        List<EligibilityRule> rules = extractedRules.stream()
                .map(extracted -> EligibilityRule.builder()

                        .scheme(scheme)

                        .field(extracted.getField())
                        .operator(extracted.getOperator())
                        .expectedValue(extracted.getExpectedValue())

                        .mandatory(extracted.getMandatory())
                        .rawRule(extracted.getRawRule())

                        .build())
                .toList();

         eligibilityRuleRepo.saveAll(rules);
    }
}