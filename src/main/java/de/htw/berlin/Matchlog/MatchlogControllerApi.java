package de.htw.berlin.Matchlog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MatchlogControllerApi {
    
    @GetMapping("/api/matches")
    public List<EntityClass> getHelloWorld() {
        List<EntityClass> matches = new ArrayList<>();
        matches.add(new EntityClass("Union Berlin gegen Bayern", "Alte Försterei", 25.00));
        matches.add(new EntityClass("Kaiserslautern gegen Karlsruhe", "Fritz Walter Stadion", 30.00));
        return matches;
    }
}
