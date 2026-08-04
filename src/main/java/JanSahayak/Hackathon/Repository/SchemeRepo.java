package JanSahayak.Hackathon.Repository;

import JanSahayak.Hackathon.Entities.Scheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SchemeRepo extends JpaRepository<Scheme,Long> {
    boolean existsByName(String name);
    Optional<Scheme> findByName(String name);
}
