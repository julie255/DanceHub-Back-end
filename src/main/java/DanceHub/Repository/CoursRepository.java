package DanceHub.Repository;

import DanceHub.entity.Cours;
import DanceHub.entity.Niveau;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CoursRepository extends JpaRepository<Cours, Long> {
    @Override
    @EntityGraph(attributePaths = {"professeurs", "inscriptions"})
    List<Cours> findAll();

    @Override
    @EntityGraph(attributePaths = {"professeurs", "inscriptions"})
    Optional<Cours> findById(Long id);

    @EntityGraph(attributePaths = {"professeurs", "inscriptions"})
    List<Cours> findByNiveau(Niveau niveau);

    @EntityGraph(attributePaths = {"professeurs", "inscriptions"})
    List<Cours> findByNomCoursContaining(String nom);

    // La disponibilite repose sur les inscriptions confirmees, pas celles en attente ou annulees.
    @EntityGraph(attributePaths = {"professeurs", "inscriptions"})
    @Query("""
            SELECT DISTINCT c
            FROM Cours c
            LEFT JOIN c.inscriptions i
            GROUP BY c
            HAVING SUM(CASE WHEN i.statut = DanceHub.entity.Statut.CONFIRMEE THEN 1 ELSE 0 END) < c.capaciteMax
            """)
    List<Cours> findCoursDisponibles();
}
