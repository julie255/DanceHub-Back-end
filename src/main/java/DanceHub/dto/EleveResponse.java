package DanceHub.dto;

import java.time.LocalDate;

public record EleveResponse(
        Long idEleves,
        String nom,
        String prenom,
        String email,
        String telephone,
        LocalDate dateNaissance,
        Long utilisateurId
) {
}
