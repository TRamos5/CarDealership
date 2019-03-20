/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.service;

import com.example.carDealership.entities.Purchases;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author travisramos
 */
public interface PurchasesService {
    
    Purchases getPurchaseById(Integer purchaseId) throws DoesNotExistException;
    
    List<Purchases> getAllPurchases() throws DoesNotExistException;
    
    Purchases createPurchase(Purchases purchase) throws InvalidEntryException, DoesNotExistException;
    
    void updatePurchase(Purchases purchases) throws InvalidEntryException, DoesNotExistException;
    
    BigDecimal getUserSells(int userId, LocalDate startDate, LocalDate endDate) throws DoesNotExistException;
    
    int getUserNumSold(int userId, LocalDate startDate, LocalDate endDate) throws DoesNotExistException;
    
}
