package JanSahayak.Hackathon.Repository;

import JanSahayak.Hackathon.Entities.EligibilityRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EligibilityRuleRepository extends JpaRepository<EligibilityRule,Long> {
List<EligibilityRule> findBySchemeId(Long schemeId);
    boolean existsBySchemeId(Long schemeId);
}
