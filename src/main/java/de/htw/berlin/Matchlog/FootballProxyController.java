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

    /** Liefert alle Spiele für ein gegebenes Datum (YYYY-MM-DD). */
    @GetMapping("/matches")
    public ResponseEntity<String> getMatchesByDate(@RequestParam String date) {
        if (apiKey == null || apiKey.isBlank()) {
            return ResponseEntity.status(503).body("{\"error\":\"API-Key nicht konfiguriert\"}");
        }

        String url = BASE_URL + "/matches?date=" + date + "&limit=100";
        return restTemplate.exchange(url, HttpMethod.GET, buildHeaders(), String.class);
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
