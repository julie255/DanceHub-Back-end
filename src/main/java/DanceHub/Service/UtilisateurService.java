package DanceHub.Service;

import DanceHub.Repository.UtilisateurRepository;
import DanceHub.dto.RegisterRequest;
import DanceHub.entity.Utilisateur;
import DanceHub.entity.Utilisateur.Role;
import DanceHub.exception.ConflictException;
import DanceHub.exception.NotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    public UtilisateurService(UtilisateurRepository utilisateurRepository, PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Utilisateur creerCompte(RegisterRequest request) {
        if (utilisateurRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Email deja utilise");
        }
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(request.getNom());
        utilisateur.setPrenom(request.getPrenom());
        utilisateur.setEmail(request.getEmail());
        utilisateur.setPassword(passwordEncoder.encode(request.getPassword()));
        utilisateur.setRole(Role.ELEVE);
        utilisateur.setActif(false);
        return utilisateurRepository.save(utilisateur);
    }

    public Optional<Utilisateur> trouverParEmail(String email) {
        return utilisateurRepository.findByEmail(email);
    }

    public Utilisateur trouverParId(Long id) {
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utilisateur non trouve"));
    }

    public List<Utilisateur> listerTous() {
        return utilisateurRepository.findAll();
    }

    public Utilisateur activerCompte(Long id) {
        Utilisateur utilisateur = trouverParId(id);
        utilisateur.setActif(true);
        return utilisateurRepository.save(utilisateur);
    }

    public Utilisateur desactiverCompte(Long id) {
        Utilisateur utilisateur = trouverParId(id);
        utilisateur.setActif(false);
        return utilisateurRepository.save(utilisateur);
    }

    public void supprimerUtilisateur(Long id) {
        if (!utilisateurRepository.existsById(id)) {
            throw new NotFoundException("Utilisateur non trouve");
        }
        utilisateurRepository.deleteById(id);
    }

    public Utilisateur changerRole(Long id, Role role) {
        Utilisateur utilisateur = trouverParId(id);
        utilisateur.setRole(role);
        return utilisateurRepository.save(utilisateur);
    }
}
