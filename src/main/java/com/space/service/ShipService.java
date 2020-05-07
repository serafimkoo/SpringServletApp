package com.space.service;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ShipService {
    @Autowired
    ShipRepository repo;

    public ResponseEntity<List<Ship>> listAll(String name, String planet, ShipType shipType,
                                              Long after, Long before,
                                              Boolean isUsed,
                                              Double minSpeed, Double maxSpeed,
                                              Integer minCrewSize, Integer maxCrewSize,
                                              Double minRating, Double maxRating,
                                              int pageNumber, int pageSize, String order) {
        Specification<Ship> specification = ShipSpecification.getShipByMinCrewSize(1);
        if (name != null) {
            specification = specification.and(ShipSpecification.getShipByName(name));
        }
        if (planet != null) {
            specification = specification.and(ShipSpecification.getShipByPlanet(planet));
        }
        if (shipType != null) {
            specification = specification.and(ShipSpecification.getShipByShipType(shipType));
        }
        if (after != null) {
            specification = specification.and(ShipSpecification.getShipByMinProdDate(after));
        }
        if (before != null) {
            specification = specification.and(ShipSpecification.getShipByMaxProdDate(before));
        }
        if (isUsed != null) {
            specification = specification.and(ShipSpecification.getShipByUsed(isUsed));
        }
        if (minSpeed != null) {
            specification = specification.and(ShipSpecification.getShipByMinSpeed(minSpeed));
        }
        if (maxSpeed != null) {
            specification = specification.and(ShipSpecification.getShipByMaxSpeed(maxSpeed));
        }
        if (minCrewSize != null) {
            specification = specification.and(ShipSpecification.getShipByMinCrewSize(minCrewSize));
        }
        if (maxCrewSize != null) {
            specification = specification.and(ShipSpecification.getShipByMaxCrewSize(maxCrewSize));
        }
        if (minRating != null) {
            specification = specification.and(ShipSpecification.getShipByMinRating(minRating));
        }
        if (maxRating != null) {
            specification = specification.and(ShipSpecification.getShipByMaxRating(maxRating));
        }

        return ResponseEntity.ok(repo.findAll(specification, PageRequest.of(pageNumber, pageSize, Sort.by(order))).getContent());
    }

    public ResponseEntity<Ship> getShip(Long id) {
        if (repo.existsById(id)) {
            return ResponseEntity.ok(repo.findById(id).get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    public ResponseEntity<Integer> shipCount(String name, String planet, ShipType shipType,
                                             Long after, Long before,
                                             Boolean isUsed,
                                             Double minSpeed, Double maxSpeed,
                                             Integer minCrewSize, Integer maxCrewSize,
                                             Double minRating, Double maxRating) {
        Specification<Ship> specification = ShipSpecification.getShipByMinCrewSize(1);
        if (name != null) {
            specification = specification.and(ShipSpecification.getShipByName(name));
        }
        if (planet != null) {
            specification = specification.and(ShipSpecification.getShipByPlanet(planet));
        }
        if (shipType != null) {
            specification = specification.and(ShipSpecification.getShipByShipType(shipType));
        }
        if (after != null) {
            specification = specification.and(ShipSpecification.getShipByMinProdDate(after));
        }
        if (before != null) {
            specification = specification.and(ShipSpecification.getShipByMaxProdDate(before));
        }
        if (isUsed != null) {
            specification = specification.and(ShipSpecification.getShipByUsed(isUsed));
        }
        if (minSpeed != null) {
            specification = specification.and(ShipSpecification.getShipByMinSpeed(minSpeed));
        }
        if (maxSpeed != null) {
            specification = specification.and(ShipSpecification.getShipByMaxSpeed(maxSpeed));
        }
        if (minCrewSize != null) {
            specification = specification.and(ShipSpecification.getShipByMinCrewSize(minCrewSize));
        }
        if (maxCrewSize != null) {
            specification = specification.and(ShipSpecification.getShipByMaxCrewSize(maxCrewSize));
        }
        if (minRating != null) {
            specification = specification.and(ShipSpecification.getShipByMinRating(minRating));
        }
        if (maxRating != null) {
            specification = specification.and(ShipSpecification.getShipByMaxRating(maxRating));
        }
        return ResponseEntity.ok(repo.findAll(specification).size());
    }

    public ResponseEntity<Ship> createShip(Ship ship) {
        LocalDate localDate = new Date(ship.getProdDate()).toLocalDate();
        if (ship.getName() == null ||
                ship.getName().isEmpty() ||
                ship.getName().length() > 50 ||
                ship.getPlanet() == null ||
                ship.getPlanet().isEmpty() ||
                ship.getPlanet().length() > 50 ||
                ship.getProdDate() == 0 ||
                localDate.getYear() < 2800 ||
                localDate.getYear() > 3019 ||
                ship.getSpeed() < 0.01d ||
                ship.getSpeed() > 0.99d ||
                ship.getCrewSize() < 1 ||
                ship.getCrewSize() > 9999) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        ship.updateRating();

        repo.save(ship);
        return ResponseEntity.ok(ship);
    }

    public ResponseEntity<Ship> updateShip(Long id, Ship ship) {
        if (repo.existsById(id)) {
            Ship target = repo.findById(id).get();

            if (ship.getName() != null)
                if (!ship.getName().isEmpty() && ship.getName().length() <= 50) {
                    target.setName(ship.getName());
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
                }
            if (ship.getPlanet() != null)
                if (!ship.getPlanet().isEmpty() && ship.getPlanet().length() <= 50) {
                    target.setPlanet(ship.getPlanet());
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
                }
            if (ship.getShipType() != null)
                target.setShipType(ship.getShipType());
            LocalDate localDate = new Date(ship.getProdDate()).toLocalDate();
            if (ship.getProdDate() != 0)
                if (localDate.getYear() >= 2800 && localDate.getYear() <= 3019) {
                    target.setProdDate(ship.getProdDate());
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
                }
            if (ship.getCrewSize() != 0)
                if (ship.getCrewSize() >= 1 && ship.getCrewSize() <= 9999) {
                    target.setCrewSize(ship.getCrewSize());
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
                }
            if (ship.getSpeed() != 0)
                if (ship.getSpeed() >= 0.01d && ship.getSpeed() <= 0.99d) {
                    target.setSpeed(ship.getSpeed());
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
                }
            target.updateRating();
            repo.save(target);
            return ResponseEntity.ok(target);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    public Ship get(Long id) {
        return repo.findById(id).get();
    }

    public ResponseEntity delete(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.ok("something");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
