package DanceHub.Controller;

import DanceHub.Service.InscriptionService;
import DanceHub.dto.ApiMapper;
import DanceHub.dto.InscriptionRequest;
import DanceHub.dto.InscriptionResponse;
import DanceHub.entity.Statut;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscriptions")
public class InscriptionController {

    private final InscriptionService inscriptionService;

    public InscriptionController(InscriptionService inscriptionService) {
        this.inscriptionService = inscriptionService;
    }

    @PostMapping
    public ResponseEntity<InscriptionResponse> creer(@Valid @RequestBody InscriptionRequest inscription, Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiMapper.toInscriptionResponse(inscriptionService.creerInscription(inscription, authentication)));
    }

    @GetMapping
    public ResponseEntity<List<InscriptionResponse>> listerToutes(Authentication authentication) {
        return ResponseEntity.ok(inscriptionService.listerToutes(authentication).stream().map(ApiMapper::toInscriptionResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InscriptionResponse> trouverParId(@PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(ApiMapper.toInscriptionResponse(inscriptionService.trouverParId(id, authentication)));
    }

    @GetMapping("/eleve/{eleveId}")
    public ResponseEntity<List<InscriptionResponse>> trouverParEleve(@PathVariable Long eleveId, Authentication authentication) {
        return ResponseEntity.ok(inscriptionService.trouverParEleve(eleveId, authentication).stream().map(ApiMapper::toInscriptionResponse).toList());
    }

    @GetMapping("/cours/{coursId}")
    public ResponseEntity<List<InscriptionResponse>> trouverParCours(@PathVariable Long coursId, Authentication authentication) {
        return ResponseEntity.ok(inscriptionService.trouverParCours(coursId, authentication).stream().map(ApiMapper::toInscriptionResponse).toList());
    }

    @PutMapping("/{id}/statut")
    public ResponseEntity<InscriptionResponse> changerStatut(@PathVariable Long id, @RequestParam Statut statut, Authentication authentication) {
        return ResponseEntity.ok(ApiMapper.toInscriptionResponse(inscriptionService.changerStatut(id, statut, authentication)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id, Authentication authentication) {
        inscriptionService.supprimerInscription(id, authentication);
        return ResponseEntity.noContent().build();
    }
}
