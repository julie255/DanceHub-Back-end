package DanceHub.entity;

import jakarta.persistence.*;
import lombok.Setter;
import org.springframework.context.annotation.Role;

@Entity
@Table(name = "utilisateurs")
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_utilisateur;

    @Setter
    @Column(nullable = false)
    private String nom;

    @Setter
    @Column(nullable = false)
    private String prenom;

    @Setter
    @Column(nullable = false, unique = true)
    private String email;

    @Setter
    @Column(nullable = false)
    private String password;
    @Setter
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean actif = false;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    public enum Role {
        ADMIN, PROFESSEUR, ELEVE
    }



    public Long getId_utilisateur() {
        return id_utilisateur;
    }

    public void setIdUtilisateur(Long id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isActif() {
        return actif;
    }

    public Role getRole() {
        return role;
    }

}
