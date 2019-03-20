/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.controller;

import com.example.carDealership.entities.Vehicle;
import com.example.carDealership.service.DoesNotExistException;
import com.example.carDealership.service.InvalidEntryException;
import com.example.carDealership.service.VehicleService;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author travisramos
 */
@RestController
@RequestMapping("/vehicle")
public class VehicleRESTController {
    
    @Autowired
    VehicleService vehicleService;
    
    @GetMapping("/allvehicles")
    public List<Vehicle> getAllMakes() throws DoesNotExistException {
        return vehicleService.getAllVehicles();
    }
    
    @GetMapping("/getvehicle/{vehicleId}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable int vehicleId) throws DoesNotExistException {
        
        Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
        
        if(vehicle == null) {
            return new ResponseEntity(new Error(), HttpStatus.NOT_FOUND);
        }
        
        return ResponseEntity.ok(vehicle);
    }
    
    @CrossOrigin(origins = "*")
    @PostMapping("/createvehicle")
    public ResponseEntity<Vehicle> createVehicle(@RequestBody Vehicle vehicle) throws InvalidEntryException, DoesNotExistException {
        
        vehicle = vehicleService.createVehicle(vehicle);
        
        return ResponseEntity.ok(vehicle);
    }
    
    @CrossOrigin(origins = "*")
    @PutMapping("/updatevehicle")
    public ResponseEntity<String> updateVehicle(@RequestBody Vehicle vehicle) throws InvalidEntryException, DoesNotExistException {
        
        vehicleService.updateVehicle(vehicle);
        
        return ResponseEntity.ok("Update Success!");
    }
    
    @GetMapping("/getfeatured")
    public List<Vehicle> getFeatured() {
        return vehicleService.findFeatured();
    }
    
    @PostMapping("/search")
    public List<Vehicle> getSearchResults(@RequestBody SearchQuery search) {
        
        return vehicleService.search(search.getStringSearch(),
                search.isNewVehicle(),
                search.getMinYear(),
                search.getMaxYear(),
                search.getMinPrice(),
                search.getMaxPrice());
    }
    
    public static class SearchQuery {

        String stringSearch;
        BigDecimal minPrice;
        BigDecimal maxPrice;
        Integer minYear;
        Integer maxYear;
        Boolean newVehicle;

        public SearchQuery() {

        }

        public String getStringSearch() {
            return stringSearch;
        }

        public void setStringSearch(String stringSearch) {
            this.stringSearch = stringSearch;
        }

        public BigDecimal getMinPrice() {
            return minPrice;
        }

        public void setMinPrice(BigDecimal minPrice) {
            this.minPrice = minPrice;
        }

        public BigDecimal getMaxPrice() {
            return maxPrice;
        }

        public void setMaxPrice(BigDecimal maxPrice) {
            this.maxPrice = maxPrice;
        }

        public Integer getMinYear() {
            return minYear;
        }

        public void setMinYear(Integer minYear) {
            this.minYear = minYear;
        }

        public Integer getMaxYear() {
            return maxYear;
        }

        public void setMaxYear(Integer maxYear) {
            this.maxYear = maxYear;
        }

        public Boolean isNewVehicle() {
            return newVehicle;
        }

        public void setNewVehicle(Boolean newVehicle) {
            this.newVehicle = newVehicle;
        }
    }
    
}
