package DanceHub.Repository;

import DanceHub.entity.Inscription;
import DanceHub.entity.Statut;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface InscriptionRepository extends JpaRepository<Inscription, Long> {
    List<Inscription> findByDateInscriptionAndStatut(LocalDate dateInscription, Statut statut);
    List<Inscription> findByEleves_IdEleves(Long eleveId);
    List<Inscription> findByCours_IdCours(Long coursId);
    boolean existsByEleves_IdElevesAndCours_IdCours(Long eleveId, Long coursId);
}
