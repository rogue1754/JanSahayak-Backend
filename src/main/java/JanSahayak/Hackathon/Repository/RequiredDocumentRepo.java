package JanSahayak.Hackathon.Repository;

import JanSahayak.Hackathon.Entities.RequiredDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface RequiredDocumentRepo extends JpaRepository<RequiredDocument,Long> {
 List<RequiredDocument> findBySchemeId(Long SchemeId);
 boolean existsBySchemeId(Long schemeId);
}
