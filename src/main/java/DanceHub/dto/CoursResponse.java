package DanceHub.dto;

import DanceHub.entity.Niveau;

import java.time.LocalDateTime;

public record CoursResponse(
        Long idCours,
        String nomCours,
        String description,
        LocalDateTime horaire,
        int dureeMinutes,
        int capaciteMax,
        String salle,
        Niveau niveau,
        Long professeurId,
        String professeurNom,
        int placesDisponibles
) {
}
