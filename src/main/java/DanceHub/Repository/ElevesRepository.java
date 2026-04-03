package DanceHub.Repository;

import DanceHub.entity.Eleves;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ElevesRepository extends JpaRepository<Eleves,Long> {
    List<Eleves> findByEmail(String email);
}
