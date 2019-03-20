/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.service;

import com.example.carDealership.dao.VehicleDao;
import com.example.carDealership.entities.Vehicle;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author travisramos
 */
@Service
public class VehicleServiceImpl implements VehicleService {

    VehicleDao vehicleDao;

    @Autowired
    UserService userService;

    @Autowired
    MakeService makeService;

    @Autowired
    ModelService modelService;

    @Autowired
    public VehicleServiceImpl(VehicleDao vehicleDao) {
        this.vehicleDao = vehicleDao;
    }

    @Override
    public Vehicle getVehicleById(int vehicleId) throws DoesNotExistException {
        Vehicle veh = vehicleDao.getVehicleById(vehicleId);
        if (veh == null) {
            throw new DoesNotExistException();
        }

        veh.setUser(userService.getUserById(veh.getUser().getUserId()));
        veh.setModel(modelService.getModelById(veh.getModel().getModelId()));
        veh.setMake(makeService.getMakeById(veh.getMake().getMakeId()));
        return veh;

    }

    @Override
    public List<Vehicle> getAllVehicles() throws DoesNotExistException {
        List<Vehicle> list = vehicleDao.getAllVehicles();

        for (Vehicle veh : list) {
            veh.setUser(userService.getUserById(veh.getUser().getUserId()));
            veh.setModel(modelService.getModelById(veh.getModel().getModelId()));
            veh.setMake(makeService.getMakeById(veh.getMake().getMakeId()));
        }
        return list;
    }

    @Override
    public Vehicle createVehicle(Vehicle vehicle) throws InvalidEntryException, DoesNotExistException {
        validateRequiredFields(vehicle);
        vehicle.setUser(userService.getUserById(vehicle.getUser().getUserId()));
        vehicle.setModel(modelService.getModelById(vehicle.getModel().getModelId()));
        vehicle.setMake(makeService.getMakeById(vehicle.getMake().getMakeId()));
        return vehicleDao.createVehicle(vehicle);
    }

    @Override
    public void updateVehicle(Vehicle vehicle) throws InvalidEntryException, DoesNotExistException {
        validateRequiredFields(vehicle);
        vehicleDao.updateVehicle(vehicle);
    }

    @Override
    public List<Vehicle> search(String search, Boolean isNew, Integer minYear, Integer maxYear, BigDecimal minPrice, BigDecimal maxPrice) {

        List<Vehicle> searchedVehicles;

        if ((search == null || search.trim().isEmpty())
                && minPrice == null
                && maxPrice == null
                && minYear == null
                && maxYear == null) {
            if (isNew != null) {
                searchedVehicles = vehicleDao.getAllVehicles()
                        .stream()
                        .filter(v -> v.getIsNew() == isNew)
                        .sorted(Comparator.comparing(v -> v.getMsrp()))
                        .collect(Collectors.toList());
                Collections.reverse(searchedVehicles);
            } else {
                searchedVehicles = vehicleDao.getAllVehicles()
                        .stream()
                        .sorted(Comparator.comparing(v -> v.getMsrp()))
                        .collect(Collectors.toList());
                Collections.reverse(searchedVehicles);
            }
        } else {
            searchedVehicles = vehicleDao.search(search, isNew);

            if (minPrice != null) {
                searchedVehicles.stream()
                        .filter(v -> v.getSalePrice().compareTo(minPrice) > 0)
                        .collect(Collectors.toList());
            }
            if (maxPrice != null) {
                searchedVehicles.stream()
                        .filter(v -> v.getSalePrice().compareTo(maxPrice) < 0)
                        .collect(Collectors.toList());
            }
            if (minYear != null) {
                searchedVehicles.stream()
                        .filter(v -> v.getYear() >= minYear)
                        .collect(Collectors.toList());
            }
            if (maxYear != null) {
                searchedVehicles.stream()
                        .filter(v -> v.getYear() <= maxYear)
                        .collect(Collectors.toList());
            }
        }

        if (searchedVehicles.size() > 20) {
            searchedVehicles = searchedVehicles.subList(0, 19);
        }

        try {
            for (Vehicle vehicle : searchedVehicles) {
                vehicle.setUser(userService.getUserById(vehicle.getUser().getUserId()));
                vehicle.setMake(makeService.getMakeById(vehicle.getMake().getMakeId()));
                vehicle.setModel(modelService.getModelById(vehicle.getModel().getModelId()));
            }
        } catch (DoesNotExistException e) {
            //should not throw this as from the database
        }
        return searchedVehicles;
    }

    @Override
    public List<Vehicle> findFeatured() {
//        return vehicleDao.findFeatured();

        List<Vehicle> list = vehicleDao.findFeatured();

        try {
            for (Vehicle veh : list) {
                veh.setUser(userService.getUserById(veh.getUser().getUserId()));
                veh.setModel(modelService.getModelById(veh.getModel().getModelId()));
                veh.setMake(makeService.getMakeById(veh.getMake().getMakeId()));
            }
        } catch (DoesNotExistException e) {

        }
        return list;
    }

    private void validateRequiredFields(Vehicle vehicle) throws InvalidEntryException {

        // check year
        if ((vehicle.getYear() < 2000) || (vehicle.getYear() > (LocalDate.now().getYear() + 1))) {
            throw new InvalidEntryException();
        }
        // check transmission
        if (!vehicle.getTrans().equalsIgnoreCase("Automatic")
                && !vehicle.getTrans().equalsIgnoreCase("Manual")) {
            throw new InvalidEntryException();
        }
        // check color (red, blue, green, silver, black)
        if (vehicle.getColor() == null
                || (!vehicle.getColor().equals("red")
                && !vehicle.getColor().equals("blue")
                && !vehicle.getColor().equals("green")
                && !vehicle.getColor().equals("silver")
                && !vehicle.getColor().equals("black"))) {
            throw new InvalidEntryException();
        }
        // check interior (red, blue, green, silver, black)
        if (!vehicle.getInterior().equals("red")
                && !vehicle.getInterior().equals("blue")
                && !vehicle.getInterior().equals("green")
                && !vehicle.getInterior().equals("silver")
                && !vehicle.getInterior().equals("black")) {
            throw new InvalidEntryException();
        }
        // check milage (non negative, less than 1000 for new vehicles, used must be 1000+)
        if ((vehicle.getMileage() < 0)
                || (vehicle.getIsNew() && (vehicle.getMileage() > 1000)) // new vehicle cant have more than 1000 miles
                || (!vehicle.getIsNew() && (vehicle.getMileage() < 1000))) { // used vehicles must have more than 1000 miles
            throw new InvalidEntryException();
        }
        // check vin (not null)
        if (vehicle.getVin() == null || vehicle.getVin().equals("")) {
            throw new InvalidEntryException();
        }
        // check MSRP (non negative)
        if (vehicle.getMsrp() == null || vehicle.getMsrp().compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidEntryException();
        }
        // check sale price
        if (vehicle.getMsrp() == null
                || vehicle.getSalePrice() == null
                || vehicle.getSalePrice().compareTo(vehicle.getMsrp()) > 0) {
            throw new InvalidEntryException();
        }
        // check description
        if (vehicle.getDescription() == null
                || vehicle.getDescription().equals("")) {
            throw new InvalidEntryException();
        }
        // check images
        if (vehicle.getFileImg() == null
                || (!vehicle.getFileImg().endsWith(".png")
                && !vehicle.getFileImg().endsWith(".jpg")
                && !vehicle.getFileImg().endsWith(".jpeg"))) {
            throw new InvalidEntryException();
        }

    }

}
