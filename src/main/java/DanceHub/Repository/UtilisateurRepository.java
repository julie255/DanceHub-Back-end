package DanceHub.Repository;

import DanceHub.entity.Utilisateur;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import DanceHub.entity.Utilisateur.Role;
import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByEmail(String email);
    Page<Utilisateur> findByRole(Role role, Pageable pageable);
    boolean existsByEmail(String email);

}
