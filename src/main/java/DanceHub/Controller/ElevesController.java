package DanceHub.Controller;

import DanceHub.Service.ElevesService;
import DanceHub.dto.ApiMapper;
import DanceHub.dto.EleveRequest;
import DanceHub.dto.EleveResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eleves")
public class ElevesController {

    private final ElevesService elevesService;

    public ElevesController(ElevesService elevesService) {
        this.elevesService = elevesService;
    }

    @PostMapping
    public ResponseEntity<EleveResponse> creer(@Valid @RequestBody EleveRequest eleve, Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiMapper.toEleveResponse(elevesService.creerEleve(eleve, authentication)));
    }

    @GetMapping
    public ResponseEntity<List<EleveResponse>> listerTous(Authentication authentication) {
        return ResponseEntity.ok(elevesService.listerTous(authentication).stream().map(ApiMapper::toEleveResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EleveResponse> trouverParId(@PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(ApiMapper.toEleveResponse(elevesService.trouverParId(id, authentication)));
    }

    @GetMapping("/utilisateur/{utilisateurId}")
    public ResponseEntity<EleveResponse> trouverParUtilisateur(@PathVariable Long utilisateurId, Authentication authentication) {
        return ResponseEntity.ok(ApiMapper.toEleveResponse(elevesService.trouverParUtilisateur(utilisateurId, authentication)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EleveResponse> modifier(@PathVariable Long id, @Valid @RequestBody EleveRequest eleve, Authentication authentication) {
        return ResponseEntity.ok(ApiMapper.toEleveResponse(elevesService.modifierEleve(id, eleve, authentication)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id, Authentication authentication) {
        elevesService.supprimerEleve(id, authentication);
        return ResponseEntity.noContent().build();
    }
}
