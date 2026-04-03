package DanceHub.Service;

import DanceHub.Repository.UtilisateurRepository;
import DanceHub.entity.Utilisateur;
import DanceHub.entity.Utilisateur.Role;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    // Créer un compte élève
    public Utilisateur creerCompte(Utilisateur utilisateur) {
        if (utilisateurRepository.existsByEmail(utilisateur.getEmail())) {
            throw new RuntimeException("Email déjà utilisé");
        }
        utilisateur.setRole(Role.ELEVE);
        utilisateur.setActif(false);
        return utilisateurRepository.save(utilisateur);
    }

    // Trouver par email
    public Optional<Utilisateur> trouverParEmail(String email) {
        return utilisateurRepository.findByEmail(email);
    }

    // Trouver par ID
    public Utilisateur trouverParId(Long id) {
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    // Lister tous les utilisateurs
    public List<Utilisateur> listerTous() {
        return utilisateurRepository.findAll();
    }

    // Activer un compte (par l'administrateur)
    public Utilisateur activerCompte(Long id) {
        Utilisateur utilisateur = trouverParId(id);
        utilisateur.setActif(true);
        return utilisateurRepository.save(utilisateur);
    }

    // Désactiver un compte
    public Utilisateur desactiverCompte(Long id) {
        Utilisateur utilisateur = trouverParId(id);
        utilisateur.setActif(false);
        return utilisateurRepository.save(utilisateur);
    }

    // Supprimer un utilisateur
    public void supprimerUtilisateur(Long id) {
        if (!utilisateurRepository.existsById(id)) {
            throw new RuntimeException("Utilisateur non trouvé");
        }
        utilisateurRepository.deleteById(id);
    }

    // Changer le rôle
    public Utilisateur changerRole(Long id, Role role) {
        Utilisateur utilisateur = trouverParId(id);
        utilisateur.setRole(role);
        return utilisateurRepository.save(utilisateur);
    }
}
