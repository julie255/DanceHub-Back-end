package DanceHub.Service;

import DanceHub.Repository.ProfesseursRepository;
import DanceHub.Repository.UtilisateurRepository;
import DanceHub.dto.ProfesseurRequest;
import DanceHub.entity.Professeurs;
import DanceHub.entity.Utilisateur;
import DanceHub.exception.ConflictException;
import DanceHub.exception.NotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfesseursService {

    private final ProfesseursRepository professeursRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final AccessControlService accessControlService;

    public ProfesseursService(
            ProfesseursRepository professeursRepository,
            UtilisateurRepository utilisateurRepository,
            AccessControlService accessControlService
    ) {
        this.professeursRepository = professeursRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.accessControlService = accessControlService;
    }

    public Professeurs creerProfesseur(ProfesseurRequest request) {
        if (professeursRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Un professeur avec cet email existe deja");
        }

        if (professeursRepository.existsByUtilisateur_IdUtilisateur(request.getUtilisateurId())) {
            throw new ConflictException("Cet utilisateur a deja un profil professeur");
        }

        Professeurs professeur = new Professeurs();
        applyRequest(professeur, request);
        return professeursRepository.save(professeur);
    }

    public List<Professeurs> listerTous(Authentication authentication) {
        accessControlService.assertAdmin(authentication);
        return professeursRepository.findAll();
    }

    public Professeurs trouverParId(Long id, Authentication authentication) {
        Professeurs professeur = professeursRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Professeur non trouve"));
        accessControlService.assertCanAccessProfesseur(authentication, professeur);
        return professeur;
    }

    public Professeurs trouverParUtilisateur(Long utilisateurId, Authentication authentication) {
        accessControlService.assertSelfOrAdmin(authentication, utilisateurId);
        return professeursRepository.findByUtilisateur_IdUtilisateur(utilisateurId)
                .orElseThrow(() -> new NotFoundException("Aucun professeur lie a cet utilisateur"));
    }

    public Professeurs modifierProfesseur(Long id, ProfesseurRequest professeurMisAJour, Authentication authentication) {
        Professeurs professeur = professeursRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Professeur non trouve"));
        accessControlService.assertCanAccessProfesseur(authentication, professeur);

        if (!professeur.getEmail().equalsIgnoreCase(professeurMisAJour.getEmail()) && professeursRepository.existsByEmail(professeurMisAJour.getEmail())) {
            throw new ConflictException("Un professeur avec cet email existe deja");
        }

        if (professeur.getUtilisateur() != null && !professeur.getUtilisateur().getIdUtilisateur().equals(professeurMisAJour.getUtilisateurId())) {
            accessControlService.assertAdmin(authentication);
        }

        applyRequest(professeur, professeurMisAJour);
        return professeursRepository.save(professeur);
    }

    public void supprimerProfesseur(Long id, Authentication authentication) {
        Professeurs professeur = professeursRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Professeur non trouve"));
        accessControlService.assertCanAccessProfesseur(authentication, professeur);
        if (!professeursRepository.existsById(id)) {
            throw new NotFoundException("Professeur non trouve");
        }
        professeursRepository.deleteById(id);
    }

    private void applyRequest(Professeurs professeur, ProfesseurRequest request) {
        Utilisateur utilisateur = utilisateurRepository.findById(request.getUtilisateurId())
                .orElseThrow(() -> new NotFoundException("Utilisateur associe introuvable"));

        professeur.setNom(request.getNom());
        professeur.setPrenom(request.getPrenom());
        professeur.setEmail(request.getEmail());
        professeur.setTelephone(request.getTelephone());
        professeur.setSpecialite(request.getSpecialite());
        professeur.setUtilisateur(utilisateur);
    }
}
