/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.dao;

import com.example.carDealership.entities.Vehicle;
import java.util.List;

/**
 *
 * @author travisramos
 */
public interface VehicleDao {
    
    Vehicle getVehicleById(int vehicleId);
    List<Vehicle> getAllVehicles();
    Vehicle createVehicle(Vehicle vehicle);
    void updateVehicle(Vehicle vehicle);
    List<Vehicle> search(String search, Boolean isNew);
    List<Vehicle> findFeatured();
}
