package com.space.model;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Ship {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;
    private String planet;
    @Enumerated(EnumType.STRING)
    private ShipType shipType;
    private Date prodDate;
    private boolean isUsed;
    private double speed;
    private int crewSize;
    private double rating;

    public Ship() {
    }

    public Ship(String name, String planet, ShipType shipType, Date prodDate, boolean isUsed, double speed, int crewSize) {
        this.speed = speed;
        this.name = name;
        this.planet = planet;
        this.shipType = shipType;
        this.isUsed = isUsed;
        this.crewSize = crewSize;
        this.prodDate = prodDate;
        updateRating();
    }

    public void updateRating() {
        LocalDate localDate = prodDate.toLocalDate();
        rating = 80 * speed * (!isUsed ? 1 : 0.5) / (3019 - localDate.getYear() + 1);
        rating = (double) Math.round(rating * 100) / 100;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.length() <= 50)
            this.name = name;
    }

    public String getPlanet() {
        return planet;
    }

    public void setPlanet(String planet) {
        if (planet.length() <= 50)
            this.planet = planet;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    public long getProdDate() {
        return prodDate == null ? 0 : prodDate.getTime();
    }

    public void setProdDate(long prodDate) {
        Date prodDateAsDate = new Date(prodDate);
        LocalDate localDate = prodDateAsDate.toLocalDate();
        if (localDate.getYear() >= 2800 && localDate.getYear() <= 3019)
            this.prodDate = prodDateAsDate;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        if (speed >= 0.01d && speed <= 0.99d)
            this.speed = speed;
    }

    public int getCrewSize() {
        return crewSize;
    }

    public void setCrewSize(int crewSize) {
        if (crewSize >= 1 && crewSize <= 9999)
            this.crewSize = crewSize;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
