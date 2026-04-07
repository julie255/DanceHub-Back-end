package DanceHub.dto;

import DanceHub.entity.Statut;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class InscriptionRequest {

    @NotNull(message = "est obligatoire")
    private Long eleveId;

    @NotNull(message = "est obligatoire")
    private Long coursId;

    private LocalDate dateInscription;

    private Statut statut;

    public Long getEleveId() {
        return eleveId;
    }

    public void setEleveId(Long eleveId) {
        this.eleveId = eleveId;
    }

    public Long getCoursId() {
        return coursId;
    }

    public void setCoursId(Long coursId) {
        this.coursId = coursId;
    }

    public LocalDate getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(LocalDate dateInscription) {
        this.dateInscription = dateInscription;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }
}
