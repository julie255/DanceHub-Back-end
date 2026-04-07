package DanceHub.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "eleves")
@Getter
@Setter
public class Eleves {

    @Id
    @Column(name = "id_eleves")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEleves;

    @Column(nullable = false)
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @Column(nullable = false)
    @NotBlank(message = "Le prenom est obligatoire")
    private String prenom;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit etre valide")
    private String email;

    private String telephone;

    @Past(message = "La date de naissance doit etre dans le passe")
    private LocalDate dateNaissance;

    @OneToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

    @OneToMany(mappedBy = "eleves", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Inscription> inscriptions;

    @ManyToMany
    @JsonIgnore
    private List<Cours> cours;
}
