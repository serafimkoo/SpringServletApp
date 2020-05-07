package com.space.controller;

import com.mysql.cj.PreparedQuery;
import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    ShipService service;

    @GetMapping(value = "/rest/ships")
    public ResponseEntity<List<Ship>> getShip(@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                              @RequestParam(value = "pageSize", defaultValue = "3") int pageSize,
                                              @RequestParam(value = "order", defaultValue = "ID") String order,
                                              @RequestParam Optional<String> name,
                                              @RequestParam Optional<String> planet,
                                              @RequestParam Optional<ShipType> shipType,
                                              @RequestParam Optional<Long> after,
                                              @RequestParam Optional<Long> before,
                                              @RequestParam Optional<Boolean> isUsed,
                                              @RequestParam Optional<Double> minSpeed,
                                              @RequestParam Optional<Double> maxSpeed,
                                              @RequestParam Optional<Integer> minCrewSize,
                                              @RequestParam Optional<Integer> maxCrewSize,
                                              @RequestParam Optional<Double> minRating,
                                              @RequestParam Optional<Double> maxRating
    ) {
//        System.out.println(maxCrewSize.orElseGet(() -> null));
        return service.listAll(name.orElseGet(() -> null),
                planet.orElseGet(() -> null),
                shipType.orElseGet(() -> null),
                after.orElseGet(() -> null),
                before.orElseGet(() -> null),
                isUsed.orElseGet(() -> null),
                minSpeed.orElseGet(() -> null),
                maxSpeed.orElseGet(() -> null),
                minCrewSize.orElseGet(() -> null),
                maxCrewSize.orElseGet(() -> null),
                minRating.orElseGet(() -> null),
                maxRating.orElseGet(() -> null),
                pageNumber, pageSize, ShipOrder.valueOf(order).getFieldName());
    }

    @GetMapping(value = "/rest/ships/{id}")
    public ResponseEntity<Ship> getShipById(@PathVariable String id) {
        try {
            long Id = Long.parseLong(id);
            if (Id < 1)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            return service.getShip(Id);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping(value = "/rest/ships/count")
    public ResponseEntity<Integer> getShipsCount(@RequestParam Optional<String> name,
                                                 @RequestParam Optional<String> planet,
                                                 @RequestParam Optional<ShipType> shipType,
                                                 @RequestParam Optional<Long> after,
                                                 @RequestParam Optional<Long> before,
                                                 @RequestParam Optional<Boolean> isUsed,
                                                 @RequestParam Optional<Double> minSpeed,
                                                 @RequestParam Optional<Double> maxSpeed,
                                                 @RequestParam Optional<Integer> minCrewSize,
                                                 @RequestParam Optional<Integer> maxCrewSize,
                                                 @RequestParam Optional<Double> minRating,
                                                 @RequestParam Optional<Double> maxRating) {
        return service.shipCount(name.orElseGet(() -> null),
                planet.orElseGet(() -> null),
                shipType.orElseGet(() -> null),
                after.orElseGet(() -> null),
                before.orElseGet(() -> null),
                isUsed.orElseGet(() -> null),
                minSpeed.orElseGet(() -> null),
                maxSpeed.orElseGet(() -> null),
                minCrewSize.orElseGet(() -> null),
                maxCrewSize.orElseGet(() -> null),
                minRating.orElseGet(() -> null),
                maxRating.orElseGet(() -> null));
    }

    @PostMapping(value = "/rest/ships")
    public ResponseEntity<Ship> createShip(@RequestBody Ship ship) {

        return service.createShip(ship);
    }

    @PostMapping(value = "/rest/ships/{id}")
    public ResponseEntity<Ship> updateShip(@PathVariable String id, @RequestBody Ship ship) {
        if (ship == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        try {
            long Id = Long.parseLong(id);
            if (Id < 1)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            return service.updateShip(Id, ship);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/rest/ships/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteShip(@PathVariable String id) {
        try {
            long Id = Long.parseLong(id);
            if (Id < 1)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            return service.delete(Id);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
