package de.htw.berlin.Matchlog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class MatchlogApplicationTests {

    @Autowired
    private VisitController controller;

    @Autowired
    private VisitRepository repository;

    // -------------------------------------------------------
    // Hilfsmethoden
    // -------------------------------------------------------

    private Visit buildVisit(String userId, Visibility visibility) {
        Visit v = new Visit();
        v.setHomeTeam("Hertha BSC");
        v.setAwayTeam("Schalke 04");
        v.setStadium("Olympiastadion");
        v.setCity("Berlin");
        v.setCountry("Deutschland");
        v.setDate(LocalDate.of(2026, 3, 14));
        v.setScore("2:1");
        v.setAttendance(48000);
        v.setWeather("Bewoelkt");
        v.setNote("Toller Abend");
        v.setVisibility(visibility);
        v.setUserId(userId);
        v.setReporterName("testuser");

        Ratings r = new Ratings();
        r.setAtmosphere(4.0);
        r.setAmbience(3.5);
        r.setFood(3.0);
        r.setTravel(5.0);
        r.setFanculture(4.0);
        r.setSecurity(4.0);
        v.setRatings(r);

        return v;
    }

    @BeforeEach
    void cleanDatabase() {
        repository.deleteAll();
    }

    // -------------------------------------------------------
    // Test 1: Spring-Context startet fehlerfrei
    // -------------------------------------------------------
    @Test
    @DisplayName("1 - Spring-Context startet fehlerfrei")
    void contextLoads() {
        assertThat(controller).isNotNull();
        assertThat(repository).isNotNull();
    }

    // -------------------------------------------------------
    // Test 2: Public Feed ist initial leer
    // -------------------------------------------------------
    @Test
    @DisplayName("2 - Public-Feed ist leer wenn keine Eintraege vorhanden sind")
    void publicFeedIsInitiallyEmpty() {
        List<Visit> result = controller.getPublicVisits();
        assertThat(result).isEmpty();
    }

    // -------------------------------------------------------
    // Test 3: Neuer Eintrag wird korrekt angelegt
    // -------------------------------------------------------
    @Test
    @DisplayName("3 - createVisit legt Eintrag an und gibt ihn mit ID zurueck")
    void createVisitReturnsVisitWithId() {
        Visit created = controller.createVisit(buildVisit("user@test.de", Visibility.PUBLIC));

        assertThat(created.getId()).isNotNull();
        assertThat(created.getHomeTeam()).isEqualTo("Hertha BSC");
        assertThat(created.getStadium()).isEqualTo("Olympiastadion");
    }

    // -------------------------------------------------------
    // Test 4: Public Feed zeigt nur PUBLIC-Eintraege
    // -------------------------------------------------------
    @Test
    @DisplayName("4 - Public-Feed zeigt nur PUBLIC-Eintraege, keine PRIVATE")
    void publicFeedOnlyShowsPublicVisits() {
        repository.save(buildVisit("user@test.de", Visibility.PUBLIC));
        repository.save(buildVisit("user@test.de", Visibility.PRIVATE));

        List<Visit> result = controller.getPublicVisits();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getVisibility()).isEqualTo(Visibility.PUBLIC);
    }

    // -------------------------------------------------------
    // Test 5: Diary gibt eigene Eintraege zurueck
    // -------------------------------------------------------
    @Test
    @DisplayName("5 - getMyVisits gibt alle eigenen Eintraege zurueck")
    void diaryReturnsOwnVisits() {
        repository.save(buildVisit("mario@inwx.de", Visibility.PRIVATE));
        repository.save(buildVisit("mario@inwx.de", Visibility.PUBLIC));

        List<Visit> result = controller.getMyVisits("mario@inwx.de");

        assertThat(result).hasSize(2);
    }

    // -------------------------------------------------------
    // Test 6: Diary gibt keine fremden Eintraege zurueck
    // -------------------------------------------------------
    @Test
    @DisplayName("6 - getMyVisits gibt keine Eintraege anderer Nutzer zurueck")
    void diaryDoesNotShowOtherUsersVisits() {
        repository.save(buildVisit("other@test.de", Visibility.PUBLIC));

        List<Visit> result = controller.getMyVisits("mario@inwx.de");

        assertThat(result).isEmpty();
    }

    // -------------------------------------------------------
    // Test 7: Eintrag erfolgreich aktualisieren
    // -------------------------------------------------------
    @Test
    @DisplayName("7 - updateVisit aktualisiert den Eintrag korrekt")
    void updateVisitSuccessfully() {
        Visit created = controller.createVisit(buildVisit("mario@inwx.de", Visibility.PRIVATE));

        Visit updated = buildVisit("mario@inwx.de", Visibility.PUBLIC);
        updated.setNote("Aktualisierte Notiz");

        Visit result = controller.updateVisit(created.getId(), "mario@inwx.de", updated);

        assertThat(result.getNote()).isEqualTo("Aktualisierte Notiz");
        assertThat(result.getVisibility()).isEqualTo(Visibility.PUBLIC);
    }

    // -------------------------------------------------------
    // Test 8: Fremder Nutzer darf nicht bearbeiten - 403
    // -------------------------------------------------------
    @Test
    @DisplayName("8 - updateVisit von falschem Nutzer wirft 403 Forbidden")
    void updateVisitByWrongUserReturns403() {
        Visit created = controller.createVisit(buildVisit("mario@inwx.de", Visibility.PRIVATE));

        assertThatThrownBy(() ->
                controller.updateVisit(created.getId(), "hacker@evil.com",
                        buildVisit("hacker@evil.com", Visibility.PUBLIC))
        )
                .isInstanceOf(ResponseStatusException.class)
                .satisfies(ex -> assertThat(
                        ((ResponseStatusException) ex).getStatusCode().value()
                ).isEqualTo(403));
    }

    // -------------------------------------------------------
    // Test 9: Eintrag erfolgreich loeschen
    // -------------------------------------------------------
    @Test
    @DisplayName("9 - deleteVisit entfernt den Eintrag aus der Datenbank")
    void deleteVisitSuccessfully() {
        Visit created = controller.createVisit(buildVisit("mario@inwx.de", Visibility.PRIVATE));

        controller.deleteVisit(created.getId(), "mario@inwx.de");

        assertThat(controller.getMyVisits("mario@inwx.de")).isEmpty();
    }

    // -------------------------------------------------------
    // Test 10: Fremder Nutzer darf nicht loeschen - 403
    // -------------------------------------------------------
    @Test
    @DisplayName("10 - deleteVisit von falschem Nutzer wirft 403 Forbidden")
    void deleteVisitByWrongUserReturns403() {
        Visit created = controller.createVisit(buildVisit("mario@inwx.de", Visibility.PRIVATE));

        assertThatThrownBy(() ->
                controller.deleteVisit(created.getId(), "hacker@evil.com")
        )
                .isInstanceOf(ResponseStatusException.class)
                .satisfies(ex -> assertThat(
                        ((ResponseStatusException) ex).getStatusCode().value()
                ).isEqualTo(403));

        // Eintrag muss noch vorhanden sein
        assertThat(controller.getMyVisits("mario@inwx.de")).hasSize(1);
    }
}
