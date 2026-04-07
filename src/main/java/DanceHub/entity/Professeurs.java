package DanceHub.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "professeurs")
@Getter
@Setter
public class Professeurs {

    @Id
    @Column(name = "id_professeur")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProfesseur;

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

    private String specialite;

    @OneToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

    @OneToMany(mappedBy = "professeurs", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Cours> cours;
}
