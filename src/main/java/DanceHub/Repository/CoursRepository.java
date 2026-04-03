package DanceHub.Repository;

import DanceHub.entity.Cours;
import DanceHub.entity.Niveau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface CoursRepository extends JpaRepository<Cours, Long> {
    List<Cours> findByNiveau(Niveau niveau);
    List<Cours> findByNomCoursContaining(String nom);

    @Query("SELECT c FROM Cours c WHERE SIZE(c.inscriptions) < c.capaciteMax")
    List<Cours> findCoursDisponibles();
}