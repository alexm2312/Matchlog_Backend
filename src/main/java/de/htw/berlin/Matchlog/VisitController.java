package de.htw.berlin.Matchlog;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/visits")
public class VisitController {

    private final VisitRepository repository;

    public VisitController(VisitRepository repository) {
        this.repository = repository;
    }

    /** Diary: alle Einträge eines Nutzers, unabhängig von der Sichtbarkeit. */
    @GetMapping("/me")
    public List<Visit> getMyVisits(@RequestParam String userId) {
        return repository.findByUserIdOrderByDateDesc(userId);
    }

    /** Public: alle Einträge, die als PUBLIC markiert sind, egal von wem. */
    @GetMapping("/public")
    public List<Visit> getPublicVisits() {
        return repository.findByVisibilityOrderByDateDesc(Visibility.PUBLIC);
    }

    @GetMapping("/{id}")
    public Visit getVisit(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Eintrag nicht gefunden"));
    }

    @PostMapping
    public Visit createVisit(@Valid @RequestBody Visit visit) {
        return repository.save(visit);
    }

    @PutMapping("/{id}")
    public Visit updateVisit(@PathVariable Long id, @RequestParam String userId, @Valid @RequestBody Visit updated) {
        Visit existing = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Eintrag nicht gefunden"));

        if (!existing.getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Nur der Besitzer darf diesen Eintrag bearbeiten");
        }

        existing.setHomeTeam(updated.getHomeTeam());
        existing.setAwayTeam(updated.getAwayTeam());
        existing.setStadium(updated.getStadium());
        existing.setCity(updated.getCity());
        existing.setCountry(updated.getCountry());
        existing.setDate(updated.getDate());
        existing.setScore(updated.getScore());
        existing.setAttendance(updated.getAttendance());
        existing.setWeather(updated.getWeather());
        existing.setRatings(updated.getRatings());
        existing.setNote(updated.getNote());
        existing.setVisibility(updated.getVisibility());
        existing.setReporterName(updated.getReporterName());

        return repository.save(existing);
    }

    @DeleteMapping("/{id}")
    public void deleteVisit(@PathVariable Long id, @RequestParam String userId) {
        Visit existing = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Eintrag nicht gefunden"));

        if (!existing.getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Nur der Besitzer darf diesen Eintrag löschen");
        }

        repository.deleteById(id);
    }
}
