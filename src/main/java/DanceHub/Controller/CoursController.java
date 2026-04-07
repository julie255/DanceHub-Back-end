package DanceHub.Controller;

import DanceHub.Service.CoursService;
import DanceHub.dto.ApiMapper;
import DanceHub.dto.CoursRequest;
import DanceHub.dto.CoursResponse;
import DanceHub.entity.Niveau;
import jakarta.validation.Valid;
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

    @PostMapping
    public ResponseEntity<CoursResponse> creer(@Valid @RequestBody CoursRequest cours) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiMapper.toCoursResponse(coursService.creerCours(cours)));
    }

    @GetMapping
    public ResponseEntity<List<CoursResponse>> listerTous() {
        return ResponseEntity.ok(coursService.listerTous().stream().map(ApiMapper::toCoursResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoursResponse> trouverParId(@PathVariable Long id) {
        return ResponseEntity.ok(ApiMapper.toCoursResponse(coursService.trouverParId(id)));
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<CoursResponse>> disponibles() {
        return ResponseEntity.ok(coursService.coursDisponibles().stream().map(ApiMapper::toCoursResponse).toList());
    }

    @GetMapping("/niveau/{niveau}")
    public ResponseEntity<List<CoursResponse>> parNiveau(@PathVariable Niveau niveau) {
        return ResponseEntity.ok(coursService.trouverParNiveau(niveau).stream().map(ApiMapper::toCoursResponse).toList());
    }

    @GetMapping("/recherche")
    public ResponseEntity<List<CoursResponse>> rechercher(@RequestParam String nom) {
        return ResponseEntity.ok(coursService.rechercherParNom(nom).stream().map(ApiMapper::toCoursResponse).toList());
    }

    @GetMapping("/{id}/places")
    public ResponseEntity<Integer> placesDisponibles(@PathVariable Long id) {
        return ResponseEntity.ok(coursService.placesDisponibles(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CoursResponse> modifier(
            @PathVariable Long id,
            @Valid @RequestBody CoursRequest cours) {
        return ResponseEntity.ok(ApiMapper.toCoursResponse(coursService.modifierCours(id, cours)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        coursService.supprimerCours(id);
        return ResponseEntity.noContent().build();
    }
}
