package de.htw.berlin.Matchlog;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Embeddable
public class Ratings {

    @Min(1)
    @Max(5)
    private int atmosphere;

    @Min(1)
    @Max(5)
    private int ambience;

    @Min(1)
    @Max(5)
    private int food;

    @Min(1)
    @Max(5)
    private int travel;

    @Min(1)
    @Max(5)
    private int fanculture;

    @Min(1)
    @Max(5)
    private int security;

    public Ratings() {}

    public Ratings(int atmosphere, int ambience, int food, int travel, int fanculture, int security) {
        this.atmosphere = atmosphere;
        this.ambience = ambience;
        this.food = food;
        this.travel = travel;
        this.fanculture = fanculture;
        this.security = security;
    }

    public int getAtmosphere() { return atmosphere; }
    public int getAmbience() { return ambience; }
    public int getFood() { return food; }
    public int getTravel() { return travel; }
    public int getFanculture() { return fanculture; }
    public int getSecurity() { return security; }

    public void setAtmosphere(int atmosphere) { this.atmosphere = atmosphere; }
    public void setAmbience(int ambience) { this.ambience = ambience; }
    public void setFood(int food) { this.food = food; }
    public void setTravel(int travel) { this.travel = travel; }
    public void setFanculture(int fanculture) { this.fanculture = fanculture; }
    public void setSecurity(int security) { this.security = security; }
}
