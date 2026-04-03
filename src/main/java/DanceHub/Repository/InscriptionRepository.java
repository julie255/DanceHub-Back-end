package DanceHub.Repository;

import DanceHub.entity.Inscription;
import DanceHub.entity.Statut;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface InscriptionRepository extends JpaRepository<Inscription,Long> {
    List<Inscription> findByDateInscriptionAndStatut(LocalDate  dateInscription,Statut statut);
}
