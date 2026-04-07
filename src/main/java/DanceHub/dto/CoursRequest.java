package DanceHub.dto;

import DanceHub.entity.Niveau;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class CoursRequest {

    @NotBlank(message = "est obligatoire")
    private String nomCours;

    private String description;

    @NotNull(message = "est obligatoire")
    private LocalDateTime horaire;

    @Min(value = 1, message = "doit etre superieur a 0")
    private int dureeMinutes;

    @Min(value = 1, message = "doit etre superieur a 0")
    private int capaciteMax;

    private String salle;

    @NotNull(message = "est obligatoire")
    private Niveau niveau;

    private Long professeurId;

    public String getNomCours() {
        return nomCours;
    }

    public void setNomCours(String nomCours) {
        this.nomCours = nomCours;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getHoraire() {
        return horaire;
    }

    public void setHoraire(LocalDateTime horaire) {
        this.horaire = horaire;
    }

    public int getDureeMinutes() {
        return dureeMinutes;
    }

    public void setDureeMinutes(int dureeMinutes) {
        this.dureeMinutes = dureeMinutes;
    }

    public int getCapaciteMax() {
        return capaciteMax;
    }

    public void setCapaciteMax(int capaciteMax) {
        this.capaciteMax = capaciteMax;
    }

    public String getSalle() {
        return salle;
    }

    public void setSalle(String salle) {
        this.salle = salle;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public Long getProfesseurId() {
        return professeurId;
    }

    public void setProfesseurId(Long professeurId) {
        this.professeurId = professeurId;
    }
}
