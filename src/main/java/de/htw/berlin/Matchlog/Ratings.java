package de.htw.berlin.Matchlog;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

@Embeddable
public class Ratings {

    @DecimalMin("1.0") @DecimalMax("5.0")
    private Double atmosphere;

    @DecimalMin("1.0") @DecimalMax("5.0")
    private Double ambience;

    @DecimalMin("1.0") @DecimalMax("5.0")
    private Double food;

    @DecimalMin("1.0") @DecimalMax("5.0")
    private Double travel;

    @DecimalMin("1.0") @DecimalMax("5.0")
    private Double fanculture;

    @DecimalMin("1.0") @DecimalMax("5.0")
    private Double security;

    public Ratings() {}

    public Ratings(Double atmosphere, Double ambience, Double food, Double travel, Double fanculture, Double security) {
        this.atmosphere = atmosphere;
        this.ambience   = ambience;
        this.food       = food;
        this.travel     = travel;
        this.fanculture = fanculture;
        this.security   = security;
    }

    public Double getAtmosphere() { return atmosphere; }
    public Double getAmbience()   { return ambience; }
    public Double getFood()       { return food; }
    public Double getTravel()     { return travel; }
    public Double getFanculture() { return fanculture; }
    public Double getSecurity()   { return security; }

    public void setAtmosphere(Double v) { this.atmosphere = v; }
    public void setAmbience(Double v)   { this.ambience   = v; }
    public void setFood(Double v)       { this.food       = v; }
    public void setTravel(Double v)     { this.travel     = v; }
    public void setFanculture(Double v) { this.fanculture = v; }
    public void setSecurity(Double v)   { this.security   = v; }
}
