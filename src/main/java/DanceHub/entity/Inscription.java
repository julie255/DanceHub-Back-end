package DanceHub.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "inscriptions")
@Getter
@Setter
public class Inscription {

    @Id
    @Column(name = "id_inscription")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInscription;

    @ManyToOne
    @JoinColumn(name = "eleve_id", nullable = false)
    @JsonIgnoreProperties({"inscriptions", "cours"})
    private Eleves eleves;

    @ManyToOne
    @JoinColumn(name = "cours_id", nullable = false)
    @JsonIgnoreProperties({"inscriptions"})
    private Cours cours;

    @Column(nullable = false)
    private LocalDate dateInscription;

    @Enumerated(EnumType.STRING)
    private Statut statut;


}
