/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.dao;

import com.example.carDealership.entities.Purchases;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author travisramos
 */
public interface PurchasesDao {
    
    Purchases getPurchaseById(int purchaseId);
    List<Purchases> getAllPurchases();
    Purchases createPurchase(Purchases purchases);
    void updatePurchase(Purchases purchases);
}
