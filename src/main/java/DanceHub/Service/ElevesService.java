package DanceHub.Service;

import DanceHub.Repository.ElevesRepository;
import DanceHub.Repository.UtilisateurRepository;
import DanceHub.dto.EleveRequest;
import DanceHub.entity.Eleves;
import DanceHub.entity.Utilisateur;
import DanceHub.exception.ConflictException;
import DanceHub.exception.NotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElevesService {

    private final ElevesRepository elevesRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final AccessControlService accessControlService;

    public ElevesService(
            ElevesRepository elevesRepository,
            UtilisateurRepository utilisateurRepository,
            AccessControlService accessControlService
    ) {
        this.elevesRepository = elevesRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.accessControlService = accessControlService;
    }

    public Eleves creerEleve(EleveRequest request, Authentication authentication) {
        if (elevesRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Un eleve avec cet email existe deja");
        }

        if (elevesRepository.existsByUtilisateur_IdUtilisateur(request.getUtilisateurId())) {
            throw new ConflictException("Cet utilisateur a deja un profil eleve");
        }

        accessControlService.assertSelfOrAdmin(authentication, request.getUtilisateurId());

        Eleves eleve = new Eleves();
        applyRequest(eleve, request);
        return elevesRepository.save(eleve);
    }

    public List<Eleves> listerTous(Authentication authentication) {
        accessControlService.assertAdmin(authentication);
        return elevesRepository.findAll();
    }

    public Eleves trouverParId(Long id, Authentication authentication) {
        Eleves eleve = elevesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Eleve non trouve"));
        accessControlService.assertCanAccessEleve(authentication, eleve);
        return eleve;
    }

    public Eleves trouverParUtilisateur(Long utilisateurId, Authentication authentication) {
        accessControlService.assertSelfOrAdmin(authentication, utilisateurId);
        return elevesRepository.findByUtilisateur_IdUtilisateur(utilisateurId)
                .orElseThrow(() -> new NotFoundException("Aucun eleve lie a cet utilisateur"));
    }

    public Eleves modifierEleve(Long id, EleveRequest eleveMisAJour, Authentication authentication) {
        Eleves eleve = elevesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Eleve non trouve"));
        accessControlService.assertCanAccessEleve(authentication, eleve);

        if (!eleve.getEmail().equalsIgnoreCase(eleveMisAJour.getEmail()) && elevesRepository.existsByEmail(eleveMisAJour.getEmail())) {
            throw new ConflictException("Un eleve avec cet email existe deja");
        }

        if (eleve.getUtilisateur() != null && !eleve.getUtilisateur().getIdUtilisateur().equals(eleveMisAJour.getUtilisateurId())) {
            accessControlService.assertAdmin(authentication);
        }

        applyRequest(eleve, eleveMisAJour);
        return elevesRepository.save(eleve);
    }

    public void supprimerEleve(Long id, Authentication authentication) {
        Eleves eleve = elevesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Eleve non trouve"));
        accessControlService.assertCanAccessEleve(authentication, eleve);
        if (!elevesRepository.existsById(id)) {
            throw new NotFoundException("Eleve non trouve");
        }
        elevesRepository.deleteById(id);
    }

    private void applyRequest(Eleves eleve, EleveRequest request) {
        Utilisateur utilisateur = utilisateurRepository.findById(request.getUtilisateurId())
                .orElseThrow(() -> new NotFoundException("Utilisateur associe introuvable"));

        eleve.setNom(request.getNom());
        eleve.setPrenom(request.getPrenom());
        eleve.setEmail(request.getEmail());
        eleve.setTelephone(request.getTelephone());
        eleve.setDateNaissance(request.getDateNaissance());
        eleve.setUtilisateur(utilisateur);
    }
}
