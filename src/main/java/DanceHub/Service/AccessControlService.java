package DanceHub.Service;

import DanceHub.Repository.UtilisateurRepository;
import DanceHub.entity.Cours;
import DanceHub.entity.Eleves;
import DanceHub.entity.Inscription;
import DanceHub.entity.Professeurs;
import DanceHub.entity.Utilisateur;
import DanceHub.exception.ForbiddenException;
import DanceHub.exception.NotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AccessControlService {

    private final UtilisateurRepository utilisateurRepository;

    public AccessControlService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    public Utilisateur currentUser(Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            throw new ForbiddenException("Authentification requise");
        }

        return utilisateurRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new NotFoundException("Utilisateur connecte introuvable"));
    }

    public void assertAdmin(Authentication authentication) {
        if (!isAdmin(authentication)) {
            throw new ForbiddenException("Acces reserve a l'administrateur");
        }
    }

    public boolean isAdmin(Authentication authentication) {
        return currentUser(authentication).getRole() == Utilisateur.Role.ADMIN;
    }

    public void assertSelfOrAdmin(Authentication authentication, Long utilisateurId) {
        Utilisateur currentUser = currentUser(authentication);
        if (currentUser.getRole() != Utilisateur.Role.ADMIN && !currentUser.getIdUtilisateur().equals(utilisateurId)) {
            throw new ForbiddenException("Acces refuse a cette ressource");
        }
    }

    public void assertCanAccessEleve(Authentication authentication, Eleves eleve) {
        Utilisateur currentUser = currentUser(authentication);
        if (currentUser.getRole() == Utilisateur.Role.ADMIN) {
            return;
        }

        if (eleve.getUtilisateur() == null || !currentUser.getIdUtilisateur().equals(eleve.getUtilisateur().getIdUtilisateur())) {
            throw new ForbiddenException("Acces refuse a cet eleve");
        }
    }

    public void assertCanAccessProfesseur(Authentication authentication, Professeurs professeur) {
        Utilisateur currentUser = currentUser(authentication);
        if (currentUser.getRole() == Utilisateur.Role.ADMIN) {
            return;
        }

        if (professeur.getUtilisateur() == null || !currentUser.getIdUtilisateur().equals(professeur.getUtilisateur().getIdUtilisateur())) {
            throw new ForbiddenException("Acces refuse a ce professeur");
        }
    }

    public void assertCanAccessInscription(Authentication authentication, Inscription inscription) {
        Utilisateur currentUser = currentUser(authentication);
        if (currentUser.getRole() == Utilisateur.Role.ADMIN) {
            return;
        }

        // Un eleve peut gerer ses propres inscriptions.
        Eleves eleve = inscription.getEleves();
        if (eleve != null && eleve.getUtilisateur() != null
                && currentUser.getIdUtilisateur().equals(eleve.getUtilisateur().getIdUtilisateur())) {
            return;
        }

        // Un professeur peut consulter les inscriptions de ses cours.
        Cours cours = inscription.getCours();
        if (cours != null && cours.getProfesseurs() != null && cours.getProfesseurs().getUtilisateur() != null
                && currentUser.getIdUtilisateur().equals(cours.getProfesseurs().getUtilisateur().getIdUtilisateur())) {
            return;
        }

        throw new ForbiddenException("Acces refuse a cette inscription");
    }

    public void assertCanAccessCoursInscriptions(Authentication authentication, Cours cours) {
        Utilisateur currentUser = currentUser(authentication);
        if (currentUser.getRole() == Utilisateur.Role.ADMIN) {
            return;
        }

        if (cours.getProfesseurs() != null && cours.getProfesseurs().getUtilisateur() != null
                && currentUser.getIdUtilisateur().equals(cours.getProfesseurs().getUtilisateur().getIdUtilisateur())) {
            return;
        }

        throw new ForbiddenException("Acces refuse aux inscriptions de ce cours");
    }
}
