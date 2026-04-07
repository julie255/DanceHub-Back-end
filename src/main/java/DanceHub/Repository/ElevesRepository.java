package DanceHub.Repository;

import DanceHub.entity.Eleves;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ElevesRepository extends JpaRepository<Eleves, Long> {
    Optional<Eleves> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<Eleves> findByUtilisateur_IdUtilisateur(Long utilisateurId);
    boolean existsByUtilisateur_IdUtilisateur(Long utilisateurId);
}
