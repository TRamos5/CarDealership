/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.service;

import com.example.carDealership.entities.Vehicle;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author travisramos
 */
public interface VehicleService {
    
    Vehicle getVehicleById(int vehicleId) throws DoesNotExistException;
    
    List<Vehicle> getAllVehicles() throws DoesNotExistException;
    
    Vehicle createVehicle(Vehicle vehicle) throws InvalidEntryException, DoesNotExistException;
    
    void updateVehicle(Vehicle vehicle) throws InvalidEntryException, DoesNotExistException;
    
    public List<Vehicle> search(String search, Boolean isNew, Integer minYear, Integer maxYear, BigDecimal minPrice, BigDecimal maxPrice);
    
    public List<Vehicle> findFeatured();
}
