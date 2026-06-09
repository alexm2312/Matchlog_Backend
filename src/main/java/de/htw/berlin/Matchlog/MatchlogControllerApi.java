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
    public List<EntityClass> getMatches() {
        return repository.findAll();
    }

    @PostMapping("/api/matches")
    public EntityClass createMatch(@RequestBody EntityClass match) {
        return repository.save(match);
    }
}