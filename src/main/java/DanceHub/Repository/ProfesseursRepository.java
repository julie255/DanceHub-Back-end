package DanceHub.Repository;

import DanceHub.entity.Professeurs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfesseursRepository extends JpaRepository<Professeurs, Long> {
    Optional<Professeurs> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<Professeurs> findByUtilisateur_IdUtilisateur(Long utilisateurId);
    boolean existsByUtilisateur_IdUtilisateur(Long utilisateurId);
}
