package DanceHub.Service;

import DanceHub.Repository.CoursRepository;
import DanceHub.entity.Cours;
import DanceHub.entity.Inscription;
import DanceHub.entity.Niveau;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CoursService {

    private final CoursRepository coursRepository;

    public CoursService(CoursRepository coursRepository) {
        this.coursRepository = coursRepository;
    }

    // Créer un cours
    public Cours creerCours(Cours cours) {
        if (cours.getCapaciteMax() <= 0) {
            throw new RuntimeException("La capacité doit être supérieure à 0");
        }
        return coursRepository.save(cours);
    }

    // Lister tous les cours
    public List<Cours> listerTous() {
        return coursRepository.findAll();
    }

    // Trouver par ID
    public Cours trouverParId(Long id) {
        return coursRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cours non trouvé"));
    }

    // Trouver par niveau
    public List<Cours> trouverParNiveau(Niveau niveau) {
        return coursRepository.findByNiveau(niveau);
    }

    // Rechercher par nom
    public List<Cours> rechercherParNom(String nom) {
        return coursRepository.findByNomCoursContaining(nom);
    }

    // Lister les cours disponibles (places restantes)
    public List<Cours> coursDisponibles() {
        return coursRepository.findCoursDisponibles();
    }

    // Modifier un cours
    public Cours modifierCours(Long id, Cours coursMisAJour) {
        Cours cours = trouverParId(id);
        cours.setNomCours(coursMisAJour.getNomCours());
        cours.setDescription(coursMisAJour.getDescription());
        cours.setHoraire(coursMisAJour.getHoraire());
        cours.setDureeMinutes(coursMisAJour.getDureeMinutes());
        cours.setCapaciteMax(coursMisAJour.getCapaciteMax());
        cours.setSalle(coursMisAJour.getSalle());
        cours.setNiveau(coursMisAJour.getNiveau());
        return coursRepository.save(cours);
    }

    // Supprimer un cours
    public void supprimerCours(Long id) {
        if (!coursRepository.existsById(id)) {
            throw new RuntimeException("Cours non trouvé");
        }
        coursRepository.deleteById(id);
    }

    // Vérifier les places disponibles
    public int placesDisponibles(Long id) {
        Cours cours = trouverParId(id);
        List<Inscription> inscriptions = cours.getInscriptions();
        if (inscriptions == null) {
            return cours.getCapaciteMax();
        }

        int inscrits = (int) inscriptions.stream()
                .filter(i -> i.getStatut().name().equals("CONFIRMEE"))
                .count();
        return cours.getCapaciteMax() - inscrits;
    }
}
