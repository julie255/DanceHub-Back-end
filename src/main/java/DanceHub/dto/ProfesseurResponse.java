package DanceHub.dto;

public record ProfesseurResponse(
        Long idProfesseur,
        String nom,
        String prenom,
        String email,
        String telephone,
        String specialite,
        Long utilisateurId
) {
}
