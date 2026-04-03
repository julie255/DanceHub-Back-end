package DanceHub.Controller;

import DanceHub.Service.UtilisateurService;
import DanceHub.entity.Utilisateur;
import DanceHub.entity.Utilisateur.Role;
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

    // POST /api/utilisateurs/inscription
    @PostMapping("/inscription")
    public ResponseEntity<Utilisateur> inscription(@RequestBody Utilisateur utilisateur) {
        Utilisateur nouveau = utilisateurService.creerCompte(utilisateur);
        return ResponseEntity.status(HttpStatus.CREATED).body(nouveau);
    }

    // GET /api/utilisateurs
    @GetMapping
    public ResponseEntity<List<Utilisateur>> listerTous() {
        return ResponseEntity.ok(utilisateurService.listerTous());
    }

    // GET /api/utilisateurs/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> trouverParId(@PathVariable Long id) {
        return ResponseEntity.ok(utilisateurService.trouverParId(id));
    }

    // PUT /api/utilisateurs/{id}/activer
    @PutMapping("/{id}/activer")
    public ResponseEntity<Utilisateur> activer(@PathVariable Long id) {
        return ResponseEntity.ok(utilisateurService.activerCompte(id));
    }

    // PUT /api/utilisateurs/{id}/desactiver
    @PutMapping("/{id}/desactiver")
    public ResponseEntity<Utilisateur> desactiver(@PathVariable Long id) {
        return ResponseEntity.ok(utilisateurService.desactiverCompte(id));
    }

    // DELETE /api/utilisateurs/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        utilisateurService.supprimerUtilisateur(id);
        return ResponseEntity.noContent().build();
    }

    // PUT /api/utilisateurs/{id}/role
    @PutMapping("/{id}/role")
    public ResponseEntity<Utilisateur> changerRole(
            @PathVariable Long id,
            @RequestParam Role role) {
        return ResponseEntity.ok(utilisateurService.changerRole(id, role));
    }
}
