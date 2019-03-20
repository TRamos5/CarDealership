/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.service;

import com.example.carDealership.dao.PurchasesDao;
import com.example.carDealership.entities.Purchases;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author travisramos
 */
@Service
public class PurchasesServiceImpl implements PurchasesService {

    
    PurchasesDao purchasesDao;
    
    @Autowired
    VehicleService vehicleService;
    
    @Autowired
    UserService userService;

    @Autowired
    public PurchasesServiceImpl(PurchasesDao purchasesDao) {
        this.purchasesDao = purchasesDao;
    }

    @Override
    public Purchases getPurchaseById(Integer purchaseId) throws DoesNotExistException {
        Purchases purchase = purchasesDao.getPurchaseById(purchaseId);

        if (purchase == null) {
            throw new DoesNotExistException();
        }
        
        purchase.setUser(userService.getUserById(purchase.getUser().getUserId()));
        purchase.setVehicle(vehicleService.getVehicleById(purchase.getVehicle().getVehicleId()));
        return purchase;
    }

    @Override
    public List<Purchases> getAllPurchases() throws DoesNotExistException {
        List<Purchases> list = purchasesDao.getAllPurchases();

        for(Purchases purchases : list) {
            purchases.setUser(userService.getUserById(purchases.getUser().getUserId()));
            purchases.setVehicle(vehicleService.getVehicleById(purchases.getVehicle().getVehicleId()));
        }
        return list;
    }

    @Override
    public Purchases createPurchase(Purchases purchase) throws InvalidEntryException, DoesNotExistException {
        purchase.setUser(userService.getUserById(purchase.getUser().getUserId()));
        purchase.setVehicle(vehicleService.getVehicleById(purchase.getVehicle().getVehicleId()));
        validateEntries(purchase);
        return purchasesDao.createPurchase(purchase);
    }

    @Override
    public void updatePurchase(Purchases purchases) throws InvalidEntryException, DoesNotExistException {
        validateEntries(purchases);
        purchasesDao.updatePurchase(purchases);
    }

    @Override
    public BigDecimal getUserSells(int userId, LocalDate startDate, LocalDate endDate) throws DoesNotExistException {
        userService.getUserById(userId);
        List<Purchases> list = getAllPurchases();

        if(startDate != null) {
            list = list.stream()
                    .filter(p -> p.getPurchaseDate().compareTo(startDate) > 0)
                    .collect(Collectors.toList());
        }
        if(endDate != null) {
            list = list.stream()
                    .filter(p -> p.getPurchaseDate().compareTo(endDate) > 0)
                    .collect(Collectors.toList());
        }
        
        BigDecimal earned = new BigDecimal("0.00");

        for (Purchases s : list) {
            if (s.getUser().getUserId() == userId) {
                earned = earned.add((s.getPurchaseCost()));
            }
        }
        return earned;

    }
    
    @Override
    public int getUserNumSold(int userId, LocalDate startDate, LocalDate endDate) throws DoesNotExistException {
        userService.getUserById(userId);
        List<Purchases> list = getAllPurchases();
        if(startDate != null) {
            list = list.stream()
                    .filter(p -> p.getPurchaseDate().compareTo(startDate) > 0)
                    .collect(Collectors.toList());
        }
        if (endDate != null) {
            list = list.stream()
                    .filter(p -> p.getPurchaseDate().compareTo(endDate) > 0)
                    .collect(Collectors.toList());
        }
        
        int totalVehicles = 0;
        
        for(Purchases s : list) {
            if (s.getUser().getUserId() == userId){
                totalVehicles++;
            }
        }
        return totalVehicles;
    }

    private void validateEntries(Purchases purchase) throws InvalidEntryException, DoesNotExistException {
        
        BigDecimal Msrp94 = purchase.getVehicle().getMsrp().multiply(new BigDecimal(".94")).setScale(2, RoundingMode.HALF_UP);
        
        if(purchase.getPurchaseCost().compareTo(purchase.getVehicle().getMsrp()) > 0) {
            throw new InvalidEntryException();
        }
        if(purchase.getPurchaseCost().compareTo(Msrp94) <= 0) {
            throw new InvalidEntryException();
        }
        if(purchase.getZip().trim().length() != 5) {
            throw new InvalidEntryException();
        }
        if(purchase.getEmail() == null  || purchase.getEmail().trim().length() == 0 
                && purchase.getPhone() == null || purchase.getPhone().trim().length() == 0) {
            throw new InvalidEntryException();
        }
        if(purchase.getFirstName() == null ||
                purchase.getFirstName().trim().length() == 0 ||
                purchase.getLastName() == null ||
                purchase.getLastName().trim().length() == 0 ||
                purchase.getStreet() == null ||
                purchase.getStreet().trim().length() == 0 ||
                purchase.getCity() == null ||
                purchase.getCity().trim().length() == 0 ||
                purchase.getState() == null ||
                purchase.getState().trim().length() == 0) {
            throw new InvalidEntryException();
        }

        if (purchase.getVehicle().getSold()) {
            throw new InvalidEntryException();
        }
        
        String type = purchase.getPurchaseType();
        if (!(type.equalsIgnoreCase("bank finance") || type.equalsIgnoreCase("dealer finance") || type.equalsIgnoreCase("cash"))) {
            throw new InvalidEntryException();
        }
    }

}
