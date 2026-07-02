package de.htw.berlin.Matchlog;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/football")
public class FootballProxyController {

    private static final String BASE_URL = "https://soccer.highlightly.net";

    @Value("${highlightly.api.key:}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    /** Liefert Spiele gefiltert nach Datum und/oder Heimteam-Name.
     *  Mindestens einer der Parameter muss angegeben werden. */
    @GetMapping("/matches")
    public ResponseEntity<String> getMatches(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String homeTeamName) {

        if (apiKey == null || apiKey.isBlank()) {
            return ResponseEntity.status(503).body("{\"error\":\"API-Key nicht konfiguriert\"}");
        }

        if ((date == null || date.isBlank()) && (homeTeamName == null || homeTeamName.isBlank())) {
            return ResponseEntity.badRequest().body("{\"error\":\"Bitte Datum oder Heimteam angeben\"}");
        }

        StringBuilder url = new StringBuilder(BASE_URL + "/matches?limit=50");
        if (date != null && !date.isBlank()) url.append("&date=").append(date);
        if (homeTeamName != null && !homeTeamName.isBlank()) url.append("&homeTeamName=").append(homeTeamName);

        return restTemplate.exchange(url.toString(), HttpMethod.GET, buildHeaders(), String.class);
    }

    /** Liefert Detaildaten zu einem Spiel (Stadion, Wetter, Endergebnis). */
    @GetMapping("/matches/{id}")
    public ResponseEntity<String> getMatchById(@PathVariable String id) {
        if (apiKey == null || apiKey.isBlank()) {
            return ResponseEntity.status(503).body("{\"error\":\"API-Key nicht konfiguriert\"}");
        }

        String url = BASE_URL + "/matches/" + id;
        return restTemplate.exchange(url, HttpMethod.GET, buildHeaders(), String.class);
    }

    private HttpEntity<Void> buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-key", apiKey);
        return new HttpEntity<>(headers);
    }
}
