package JanSahayak.Hackathon.Service;

import JanSahayak.Hackathon.DTOs.EligibilityRequest;
import JanSahayak.Hackathon.Entities.EligibilityRule;
import JanSahayak.Hackathon.Entities.Scheme;
import JanSahayak.Hackathon.Repository.EligibilityRuleRepository;
import JanSahayak.Hackathon.Repository.SchemeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EligibilityMatchingService {

    private final SchemeRepo schemeRepo;
    private final EligibilityRuleRepository eligibilityRuleRepo;
    private final EligibilityRuleEvaluator ruleEvaluator;

    public List<Scheme> findEligibleSchemes(EligibilityRequest user) {

        List<Scheme> schemes = schemeRepo.findAll();
        List<Scheme> eligibleSchemes = new ArrayList<>();

        for (Scheme scheme : schemes) {

            // Ignore inactive schemes
            if (Boolean.FALSE.equals(scheme.getActive())) {
                continue;
            }

            List<EligibilityRule> rules =
                    eligibilityRuleRepo.findBySchemeId(scheme.getId());

            // No eligibility information
            if (rules.isEmpty()) {
                continue;
            }

            boolean eligible = true;

            for (EligibilityRule rule : rules) {

                // Raw/informational rule
                if (rule.getField() == null ||
                        rule.getOperator() == null) {
                    continue;
                }

                if (!ruleEvaluator.evaluate(rule, user)) {
                    eligible = false;
                    break;
                }
            }

            // Recommend only if we actually evaluated something
            if (eligible) {
                eligibleSchemes.add(scheme);
            }
        }

        return eligibleSchemes;
    }
}