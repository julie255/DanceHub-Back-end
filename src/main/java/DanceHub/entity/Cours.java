package DanceHub.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "cours")
public class Cours {

    @Id
    @Column(name = "id_cours")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCours;

    @Column(nullable = false)
    @NotBlank(message = "Le nom du cours est obligatoire")
    private String nomCours;

    private String description;

    @Column(nullable = false)
    @NotNull(message = "L'horaire est obligatoire")
    private LocalDateTime horaire;

    @Min(value = 1, message = "La duree doit etre superieure a 0")
    private int dureeMinutes;

    @Column(nullable = false)
    @Min(value = 1, message = "La capacite doit etre superieure a 0")
    private int capaciteMax;

    private String salle;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Le niveau est obligatoire")
    private Niveau niveau;

    @ManyToOne
    @JoinColumn(name = "professeur_id")
    @JsonIgnoreProperties({"cours"})
    private Professeurs professeurs;

    @OneToMany(mappedBy = "cours", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Inscription> inscriptions;

    public Long getIdCours() {
        return idCours;
    }

    public void setIdCours(Long idCours) {
        this.idCours = idCours;
    }

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

    public Professeurs getProfesseurs() {
        return professeurs;
    }

    public void setProfesseurs(Professeurs professeurs) {
        this.professeurs = professeurs;
    }

    public List<Inscription> getInscriptions() {
        return inscriptions;
    }

    public void setInscriptions(List<Inscription> inscriptions) {
        this.inscriptions = inscriptions;
    }
}
