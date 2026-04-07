package DanceHub.dto;

public record UtilisateurResponse(
        Long idUtilisateur,
        String nom,
        String prenom,
        String email,
        boolean actif,
        String role
) {
}
