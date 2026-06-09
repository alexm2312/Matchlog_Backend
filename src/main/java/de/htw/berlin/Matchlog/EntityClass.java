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

    public EntityClass() {}

    public EntityClass(String title, String location, double ticketprice) {
        this.title = title;
        this.location = location;
        this.ticketprice = ticketprice;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getLocation() { return location; }
    public double getTicketprice() { return ticketprice; }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setTicketprice(double ticketprice) {
        this.ticketprice = ticketprice;
    }
    
}
