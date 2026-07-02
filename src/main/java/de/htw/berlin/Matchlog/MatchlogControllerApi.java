package de.htw.berlin.Matchlog;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class MatchlogControllerApi {

    private final MatchlogRepository repository;

    public MatchlogControllerApi(MatchlogRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/matches")
    public List<EntityClass> getMatches(@RequestParam(required = false) String owner) {
        return repository.findAll()
                .stream()
                .filter(match -> {

                    String visibility = match.getVisibility();

                    boolean isPublic =
                            visibility == null ||
                            "PUBLIC".equals(visibility);

                    boolean isOwnPrivate =
                            "PRIVATE".equals(visibility)
                            && owner != null
                            && owner.equals(match.getOwner());

                    return isPublic || isOwnPrivate;
                })
                .toList();
    }

    @PostMapping("/api/matches")
    public EntityClass createMatch(@RequestBody EntityClass match) {

        if (match.getVisibility() == null || match.getVisibility().isBlank()) {
            match.setVisibility("PUBLIC");
        }

        return repository.save(match);
    }
}