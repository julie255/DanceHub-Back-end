package DanceHub.Service;

import DanceHub.Repository.CoursRepository;
import DanceHub.Repository.ElevesRepository;
import DanceHub.Repository.InscriptionRepository;
import DanceHub.dto.InscriptionRequest;
import DanceHub.entity.Cours;
import DanceHub.entity.Eleves;
import DanceHub.entity.Inscription;
import DanceHub.entity.Statut;
import DanceHub.exception.BadRequestException;
import DanceHub.exception.ConflictException;
import DanceHub.exception.ForbiddenException;
import DanceHub.exception.NotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InscriptionService {

    private final InscriptionRepository inscriptionRepository;
    private final ElevesRepository elevesRepository;
    private final CoursRepository coursRepository;
    private final CoursService coursService;
    private final AccessControlService accessControlService;

    public InscriptionService(
            InscriptionRepository inscriptionRepository,
            ElevesRepository elevesRepository,
            CoursRepository coursRepository,
            CoursService coursService,
            AccessControlService accessControlService
    ) {
        this.inscriptionRepository = inscriptionRepository;
        this.elevesRepository = elevesRepository;
        this.coursRepository = coursRepository;
        this.coursService = coursService;
        this.accessControlService = accessControlService;
    }

    public Inscription creerInscription(InscriptionRequest request, Authentication authentication) {
        Long eleveId = request.getEleveId();
        Long coursId = request.getCoursId();

        Eleves eleve = elevesRepository.findById(eleveId)
                .orElseThrow(() -> new NotFoundException("Eleve non trouve"));
        Cours cours = coursRepository.findById(coursId)
                .orElseThrow(() -> new NotFoundException("Cours non trouve"));

        accessControlService.assertCanAccessEleve(authentication, eleve);

        if (inscriptionRepository.existsByEleves_IdElevesAndCours_IdCours(eleveId, coursId)) {
            throw new ConflictException("Cet eleve est deja inscrit a ce cours");
        }

        if (coursService.placesDisponibles(coursId) <= 0) {
            throw new ConflictException("Le cours est complet");
        }

        if (request.getStatut() != null && !accessControlService.isAdmin(authentication)) {
            throw new ForbiddenException("Seul un administrateur peut definir le statut a la creation");
        }

        Inscription inscription = new Inscription();
        inscription.setEleves(eleve);
        inscription.setCours(cours);

        if (request.getDateInscription() == null) {
            inscription.setDateInscription(LocalDate.now());
        } else {
            inscription.setDateInscription(request.getDateInscription());
        }
        if (request.getStatut() == null) {
            inscription.setStatut(Statut.EN_ATTENTE);
        } else {
            inscription.setStatut(request.getStatut());
        }

        return inscriptionRepository.save(inscription);
    }

    public List<Inscription> listerToutes(Authentication authentication) {
        accessControlService.assertAdmin(authentication);
        return inscriptionRepository.findAll();
    }

    public Inscription trouverParId(Long id, Authentication authentication) {
        Inscription inscription = inscriptionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Inscription non trouvee"));
        accessControlService.assertCanAccessInscription(authentication, inscription);
        return inscription;
    }

    public List<Inscription> trouverParEleve(Long eleveId, Authentication authentication) {
        Eleves eleve = elevesRepository.findById(eleveId)
                .orElseThrow(() -> new NotFoundException("Eleve non trouve"));
        accessControlService.assertCanAccessEleve(authentication, eleve);
        return inscriptionRepository.findByEleves_IdEleves(eleveId);
    }

    public List<Inscription> trouverParCours(Long coursId, Authentication authentication) {
        Cours cours = coursRepository.findById(coursId)
                .orElseThrow(() -> new NotFoundException("Cours non trouve"));
        accessControlService.assertCanAccessCoursInscriptions(authentication, cours);
        return inscriptionRepository.findByCours_IdCours(coursId);
    }

    public Inscription changerStatut(Long id, Statut statut, Authentication authentication) {
        if (statut == null) {
            throw new BadRequestException("Le statut est obligatoire");
        }
        accessControlService.assertAdmin(authentication);
        Inscription inscription = inscriptionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Inscription non trouvee"));
        inscription.setStatut(statut);
        return inscriptionRepository.save(inscription);
    }

    public void supprimerInscription(Long id, Authentication authentication) {
        Inscription inscription = inscriptionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Inscription non trouvee"));
        accessControlService.assertCanAccessInscription(authentication, inscription);
        if (!inscriptionRepository.existsById(id)) {
            throw new NotFoundException("Inscription non trouvee");
        }
        inscriptionRepository.deleteById(id);
    }
}
