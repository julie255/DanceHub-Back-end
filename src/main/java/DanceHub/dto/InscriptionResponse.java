package DanceHub.dto;

import DanceHub.entity.Statut;

import java.time.LocalDate;

public record InscriptionResponse(
        Long idInscription,
        Long eleveId,
        String eleveNom,
        Long coursId,
        String coursNom,
        LocalDate dateInscription,
        Statut statut
) {
}
