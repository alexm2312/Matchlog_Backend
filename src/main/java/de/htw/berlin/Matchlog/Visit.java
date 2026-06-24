package de.htw.berlin.Matchlog;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String homeTeam;

    @NotBlank
    private String awayTeam;

    @NotBlank
    private String stadium;

    @NotBlank
    private String city;

    @NotBlank
    private String country;

    @NotNull
    private LocalDate date;

    private String score;

    private Integer attendance;

    private String weather;

    @Valid
    @NotNull
    @Embedded
    private Ratings ratings;

    @Column(length = 2000)
    private String note;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Visibility visibility = Visibility.PRIVATE;

    /** E-Mail-Adresse des Besitzers – identifiziert, wem der Eintrag gehört. */
    @NotBlank
    private String userId;

    /** Anzeigename, der im Public-Feed statt der userId gezeigt wird. */
    private String reporterName;

    public Visit() {}

    public Long getId() { return id; }

    public String getHomeTeam() { return homeTeam; }
    public void setHomeTeam(String homeTeam) { this.homeTeam = homeTeam; }

    public String getAwayTeam() { return awayTeam; }
    public void setAwayTeam(String awayTeam) { this.awayTeam = awayTeam; }

    public String getStadium() { return stadium; }
    public void setStadium(String stadium) { this.stadium = stadium; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getScore() { return score; }
    public void setScore(String score) { this.score = score; }

    public Integer getAttendance() { return attendance; }
    public void setAttendance(Integer attendance) { this.attendance = attendance; }

    public String getWeather() { return weather; }
    public void setWeather(String weather) { this.weather = weather; }

    public Ratings getRatings() { return ratings; }
    public void setRatings(Ratings ratings) { this.ratings = ratings; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public Visibility getVisibility() { return visibility; }
    public void setVisibility(Visibility visibility) { this.visibility = visibility; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getReporterName() { return reporterName; }
    public void setReporterName(String reporterName) { this.reporterName = reporterName; }
}
