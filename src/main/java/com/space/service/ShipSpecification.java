package com.space.service;

import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.sql.Date;

public class ShipSpecification {
    public static Specification<Ship> getShipByName(String name) {
        return (Specification<Ship>) (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Ship> getShipByPlanet(String planet) {
        return (Specification<Ship>) (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("planet"), "%" + planet + "%");
    }

    public static Specification<Ship> getShipByShipType(ShipType shipType) {
        return (Specification<Ship>) (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("shipType"), shipType);
    }

    public static Specification<Ship> getShipByMinProdDate(long minProdDate) {
        Date after = new Date(minProdDate);
        return (Specification<Ship>) (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("prodDate"), after);
    }

    public static Specification<Ship> getShipByMaxProdDate(long maxProdDate) {
        Date before = new Date(maxProdDate);
        return (Specification<Ship>) (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("prodDate"), before);
    }

    public static Specification<Ship> getShipByMinCrewSize(int minCrew) {
        return (Specification<Ship>) (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("crewSize"), minCrew);
    }

    public static Specification<Ship> getShipByMaxCrewSize(int maxCrew) {
        return (Specification<Ship>) (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("crewSize"), maxCrew);
    }

    public static Specification<Ship> getShipByUsed(boolean isUsed) {
        return (Specification<Ship>) (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isUsed"), isUsed);
    }

    public static Specification<Ship> getShipByMinSpeed(double minSpeed) {
        return (Specification<Ship>) (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("speed"), minSpeed);
    }

    public static Specification<Ship> getShipByMaxSpeed(double maxSpeed) {
        return (Specification<Ship>) (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("speed"), maxSpeed);
    }

    public static Specification<Ship> getShipByMinRating(double minRating) {
        return (Specification<Ship>) (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), minRating);
    }

    public static Specification<Ship> getShipByMaxRating(double maxRating) {
        return (Specification<Ship>) (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("rating"), maxRating);
    }
}
