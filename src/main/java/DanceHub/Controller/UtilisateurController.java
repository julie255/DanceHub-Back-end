package DanceHub.Controller;

import DanceHub.Service.UtilisateurService;
import DanceHub.dto.ApiMapper;
import DanceHub.dto.RegisterRequest;
import DanceHub.dto.UtilisateurResponse;
import DanceHub.entity.Utilisateur.Role;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @PostMapping("/inscription")
    public ResponseEntity<UtilisateurResponse> inscription(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiMapper.toUtilisateurResponse(utilisateurService.creerCompte(request)));
    }

    @GetMapping
    public ResponseEntity<List<UtilisateurResponse>> listerTous() {
        return ResponseEntity.ok(utilisateurService.listerTous().stream().map(ApiMapper::toUtilisateurResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UtilisateurResponse> trouverParId(@PathVariable Long id) {
        return ResponseEntity.ok(ApiMapper.toUtilisateurResponse(utilisateurService.trouverParId(id)));
    }

    @PutMapping("/{id}/activer")
    public ResponseEntity<UtilisateurResponse> activer(@PathVariable Long id) {
        return ResponseEntity.ok(ApiMapper.toUtilisateurResponse(utilisateurService.activerCompte(id)));
    }

    @PutMapping("/{id}/desactiver")
    public ResponseEntity<UtilisateurResponse> desactiver(@PathVariable Long id) {
        return ResponseEntity.ok(ApiMapper.toUtilisateurResponse(utilisateurService.desactiverCompte(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        utilisateurService.supprimerUtilisateur(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<UtilisateurResponse> changerRole(
            @PathVariable Long id,
            @RequestParam Role role) {
        return ResponseEntity.ok(ApiMapper.toUtilisateurResponse(utilisateurService.changerRole(id, role)));
    }
}
