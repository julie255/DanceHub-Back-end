package DanceHub.Controller;

import DanceHub.Service.ProfesseursService;
import DanceHub.dto.ApiMapper;
import DanceHub.dto.ProfesseurRequest;
import DanceHub.dto.ProfesseurResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professeurs")
public class ProfesseursController {

    private final ProfesseursService professeursService;

    public ProfesseursController(ProfesseursService professeursService) {
        this.professeursService = professeursService;
    }

    @PostMapping
    public ResponseEntity<ProfesseurResponse> creer(@Valid @RequestBody ProfesseurRequest professeur) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiMapper.toProfesseurResponse(professeursService.creerProfesseur(professeur)));
    }

    @GetMapping
    public ResponseEntity<List<ProfesseurResponse>> listerTous(Authentication authentication) {
        return ResponseEntity.ok(professeursService.listerTous(authentication).stream().map(ApiMapper::toProfesseurResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfesseurResponse> trouverParId(@PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(ApiMapper.toProfesseurResponse(professeursService.trouverParId(id, authentication)));
    }

    @GetMapping("/utilisateur/{utilisateurId}")
    public ResponseEntity<ProfesseurResponse> trouverParUtilisateur(@PathVariable Long utilisateurId, Authentication authentication) {
        return ResponseEntity.ok(ApiMapper.toProfesseurResponse(professeursService.trouverParUtilisateur(utilisateurId, authentication)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfesseurResponse> modifier(@PathVariable Long id, @Valid @RequestBody ProfesseurRequest professeur, Authentication authentication) {
        return ResponseEntity.ok(ApiMapper.toProfesseurResponse(professeursService.modifierProfesseur(id, professeur, authentication)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id, Authentication authentication) {
        professeursService.supprimerProfesseur(id, authentication);
        return ResponseEntity.noContent().build();
    }
}
