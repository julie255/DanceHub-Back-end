package DanceHub.Service;

import DanceHub.Repository.CoursRepository;
import DanceHub.Repository.ProfesseursRepository;
import DanceHub.dto.CoursRequest;
import DanceHub.entity.Cours;
import DanceHub.entity.Inscription;
import DanceHub.entity.Niveau;
import DanceHub.entity.Professeurs;
import DanceHub.exception.BadRequestException;
import DanceHub.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoursService {

    private final CoursRepository coursRepository;
    private final ProfesseursRepository professeursRepository;

    public CoursService(CoursRepository coursRepository, ProfesseursRepository professeursRepository) {
        this.coursRepository = coursRepository;
        this.professeursRepository = professeursRepository;
    }

    public Cours creerCours(CoursRequest request) {
        if (request.getCapaciteMax() <= 0) {
            throw new BadRequestException("La capacite doit etre superieure a 0");
        }
        Cours cours = new Cours();
        applyRequest(cours, request);
        return coursRepository.save(cours);
    }

    public List<Cours> listerTous() {
        return coursRepository.findAll();
    }

    public Cours trouverParId(Long id) {
        return coursRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cours non trouve"));
    }

    public List<Cours> trouverParNiveau(Niveau niveau) {
        return coursRepository.findByNiveau(niveau);
    }

    public List<Cours> rechercherParNom(String nom) {
        return coursRepository.findByNomCoursContaining(nom);
    }

    public List<Cours> coursDisponibles() {
        return coursRepository.findCoursDisponibles();
    }

    public Cours modifierCours(Long id, CoursRequest coursMisAJour) {
        Cours cours = trouverParId(id);
        applyRequest(cours, coursMisAJour);
        return coursRepository.save(cours);
    }

    public void supprimerCours(Long id) {
        if (!coursRepository.existsById(id)) {
            throw new NotFoundException("Cours non trouve");
        }
        coursRepository.deleteById(id);
    }

    public int placesDisponibles(Long id) {
        Cours cours = trouverParId(id);
        List<Inscription> inscriptions = cours.getInscriptions();
        if (inscriptions == null) {
            return cours.getCapaciteMax();
        }

        // On ne deduit des places que pour les inscriptions vraiment confirmees.
        int inscrits = (int) inscriptions.stream()
                .filter(i -> i.getStatut() != null && i.getStatut().name().equals("CONFIRMEE"))
                .count();
        return cours.getCapaciteMax() - inscrits;
    }

    private void applyRequest(Cours cours, CoursRequest request) {
        if (request.getCapaciteMax() <= 0) {
            throw new BadRequestException("La capacite doit etre superieure a 0");
        }

        cours.setNomCours(request.getNomCours());
        cours.setDescription(request.getDescription());
        cours.setHoraire(request.getHoraire());
        cours.setDureeMinutes(request.getDureeMinutes());
        cours.setCapaciteMax(request.getCapaciteMax());
        cours.setSalle(request.getSalle());
        cours.setNiveau(request.getNiveau());

        if (request.getProfesseurId() == null) {
            cours.setProfesseurs(null);
            return;
        }

        Professeurs professeur = professeursRepository.findById(request.getProfesseurId())
                .orElseThrow(() -> new NotFoundException("Professeur non trouve"));
        cours.setProfesseurs(professeur);
    }
}
