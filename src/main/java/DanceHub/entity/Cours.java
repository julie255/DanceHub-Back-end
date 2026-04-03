package DanceHub.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "cours")
public class Cours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_cours;

    @Column(nullable = false)
    private String nomCours;

    private String description;

    @Column(nullable = false)
    private LocalDateTime horaire;

    private int dureeMinutes;

    @Column(nullable = false)
    private int capaciteMax;

    private String salle;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Niveau niveau;

    @ManyToOne
    @JoinColumn(name = "professeur_id")
    private Professeurs professeurs;

    @OneToMany(mappedBy = "cours", cascade = CascadeType.ALL)
    private List<Inscription> inscriptions;

    public Long getId_cours() {
        return id_cours;
    }

    public void setId_cours(Long id_cours) {
        this.id_cours = id_cours;
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
