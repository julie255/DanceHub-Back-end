package DanceHub.dto;

import DanceHub.entity.Cours;
import DanceHub.entity.Eleves;
import DanceHub.entity.Inscription;
import DanceHub.entity.Professeurs;
import DanceHub.entity.Statut;
import DanceHub.entity.Utilisateur;

public final class ApiMapper {

    private ApiMapper() {
    }

    public static UtilisateurResponse toUtilisateurResponse(Utilisateur utilisateur) {
        return new UtilisateurResponse(
                utilisateur.getIdUtilisateur(),
                utilisateur.getNom(),
                utilisateur.getPrenom(),
                utilisateur.getEmail(),
                utilisateur.isActif(),
                utilisateur.getRole().name()
        );
    }

    public static EleveResponse toEleveResponse(Eleves eleve) {
        Long utilisateurId = eleve.getUtilisateur() == null ? null : eleve.getUtilisateur().getIdUtilisateur();
        return new EleveResponse(
                eleve.getIdEleves(),
                eleve.getNom(),
                eleve.getPrenom(),
                eleve.getEmail(),
                eleve.getTelephone(),
                eleve.getDateNaissance(),
                utilisateurId
        );
    }

    public static ProfesseurResponse toProfesseurResponse(Professeurs professeur) {
        Long utilisateurId = professeur.getUtilisateur() == null ? null : professeur.getUtilisateur().getIdUtilisateur();
        return new ProfesseurResponse(
                professeur.getIdProfesseur(),
                professeur.getNom(),
                professeur.getPrenom(),
                professeur.getEmail(),
                professeur.getTelephone(),
                professeur.getSpecialite(),
                utilisateurId
        );
    }

    public static CoursResponse toCoursResponse(Cours cours) {
        Professeurs professeur = cours.getProfesseurs();
        String professeurNom = professeur == null ? null : professeur.getPrenom() + " " + professeur.getNom();
        long inscritsConfirmes = cours.getInscriptions() == null ? 0 : cours.getInscriptions().stream()
                .filter(inscription -> inscription.getStatut() == Statut.CONFIRMEE)
                .count();
        int placesDisponibles = Math.max(0, cours.getCapaciteMax() - (int) inscritsConfirmes);

        return new CoursResponse(
                cours.getIdCours(),
                cours.getNomCours(),
                cours.getDescription(),
                cours.getHoraire(),
                cours.getDureeMinutes(),
                cours.getCapaciteMax(),
                cours.getSalle(),
                cours.getNiveau(),
                professeur == null ? null : professeur.getIdProfesseur(),
                professeurNom,
                placesDisponibles
        );
    }

    public static InscriptionResponse toInscriptionResponse(Inscription inscription) {
        Eleves eleve = inscription.getEleves();
        Cours cours = inscription.getCours();
        return new InscriptionResponse(
                inscription.getIdInscription(),
                eleve == null ? null : eleve.getIdEleves(),
                eleve == null ? null : eleve.getPrenom() + " " + eleve.getNom(),
                cours == null ? null : cours.getIdCours(),
                cours == null ? null : cours.getNomCours(),
                inscription.getDateInscription(),
                inscription.getStatut()
        );
    }
}
