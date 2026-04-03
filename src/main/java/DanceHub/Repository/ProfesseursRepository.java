package DanceHub.Repository;

import DanceHub.entity.Professeurs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfesseursRepository extends JpaRepository<Professeurs,Long> {
    List<Professeurs> findByEmail(String email);
}
