package de.htw.berlin.Matchlog;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class EntityClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String location;
    private double ticketprice;

    // PUBLIC oder PRIVATE
    private String visibility;

    // E-Mail des Erstellers
    private String owner;

    public EntityClass() {
    }

    public EntityClass(String title, String location, double ticketprice, String visibility, String owner) {
        this.title = title;
        this.location = location;
        this.ticketprice = ticketprice;
        this.visibility = visibility;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public double getTicketprice() {
        return ticketprice;
    }

    public String getVisibility() {
        return visibility;
    }

    public String getOwner() {
        return owner;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setTicketprice(double ticketprice) {
        this.ticketprice = ticketprice;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}