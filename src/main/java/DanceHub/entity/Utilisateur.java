package DanceHub.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "utilisateurs")
@Getter
@Setter
public class Utilisateur {

    @Id
    @Column(name = "id_utilisateur")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUtilisateur;

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

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caracteres")
    private String password;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean actif = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public enum Role {
        ADMIN, PROFESSEUR, ELEVE
    }
}
