package DanceHub.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "eleves")
@Data
public class Eleves {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_eleves;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false, unique = true)
    private String email;

    private String telephone;

    private LocalDate dateNaissance;

    @OneToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

    @OneToMany(mappedBy = "eleves", cascade = CascadeType.ALL)
    private List<Inscription> inscriptions;

    @ManyToMany
    private List<Cours> cours;
}