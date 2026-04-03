package DanceHub.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "professeurs")
@Data
public class Professeurs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_professeur;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false, unique = true)
    private String email;

    private String telephone;

    private String specialite;

    @OneToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

    @OneToMany(mappedBy = "professeurs", cascade = CascadeType.ALL)
    private List<Cours> cours;
}