package JanSahayak.Hackathon.DTOs;
import JanSahayak.Hackathon.Enums.EligibilityField;
import JanSahayak.Hackathon.Enums.RuleOperator;
import lombok.*;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EligibilityRuleResponse {

    private Long id;

    private EligibilityField field;

    private RuleOperator operator;

    private String expectedValue;

    private boolean mandatory;
}