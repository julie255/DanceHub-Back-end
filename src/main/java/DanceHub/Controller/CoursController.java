package DanceHub.Controller;

import DanceHub.Service.CoursService;
import DanceHub.entity.Cours;
import DanceHub.entity.Niveau;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cours")
public class CoursController {

    private final CoursService coursService;

    public CoursController(CoursService coursService) {
        this.coursService = coursService;
    }

    // POST /api/cours
    @PostMapping
    public ResponseEntity<Cours> creer(@RequestBody Cours cours) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(coursService.creerCours(cours));
    }

    // GET /api/cours
    @GetMapping
    public ResponseEntity<List<Cours>> listerTous() {
        return ResponseEntity.ok(coursService.listerTous());
    }

    // GET /api/cours/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Cours> trouverParId(@PathVariable Long id) {
        return ResponseEntity.ok(coursService.trouverParId(id));
    }

    // GET /api/cours/disponibles
    @GetMapping("/disponibles")
    public ResponseEntity<List<Cours>> disponibles() {
        return ResponseEntity.ok(coursService.coursDisponibles());
    }

    // GET /api/cours/niveau/{niveau}
    @GetMapping("/niveau/{niveau}")
    public ResponseEntity<List<Cours>> parNiveau(@PathVariable Niveau niveau) {
        return ResponseEntity.ok(coursService.trouverParNiveau(niveau));
    }

    // GET /api/cours/recherche?nom=salsa
    @GetMapping("/recherche")
    public ResponseEntity<List<Cours>> rechercher(@RequestParam String nom) {
        return ResponseEntity.ok(coursService.rechercherParNom(nom));
    }

    // GET /api/cours/{id}/places
    @GetMapping("/{id}/places")
    public ResponseEntity<Integer> placesDisponibles(@PathVariable Long id) {
        return ResponseEntity.ok(coursService.placesDisponibles(id));
    }

    // PUT /api/cours/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Cours> modifier(
            @PathVariable Long id,
            @RequestBody Cours cours) {
        return ResponseEntity.ok(coursService.modifierCours(id, cours));
    }

    // DELETE /api/cours/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        coursService.supprimerCours(id);
        return ResponseEntity.noContent().build();
    }
}