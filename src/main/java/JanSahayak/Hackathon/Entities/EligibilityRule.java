package JanSahayak.Hackathon.Entities;

import JanSahayak.Hackathon.Enums.EligibilityField;
import JanSahayak.Hackathon.Enums.RuleOperator;
import jakarta.persistence.*;
import jakarta.persistence.Enumerated;
import lombok.*;
@Entity
@Table(name = "eligibility_rules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EligibilityRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scheme_id", nullable = false)
    private Scheme scheme;

    @Enumerated(EnumType.STRING)
    private EligibilityField field;

    @Enumerated(EnumType.STRING)
    private RuleOperator operator;

    private String expectedValue;

    @Builder.Default
    @Column(nullable = false)
    private Boolean mandatory = true;

    // Original eligibility sentence from source
    @Column(columnDefinition = "TEXT")
    private String rawRule;

}