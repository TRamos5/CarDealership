/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.controller;

import com.example.carDealership.entities.Purchases;
import com.example.carDealership.service.DoesNotExistException;
import com.example.carDealership.service.InvalidEntryException;
import com.example.carDealership.service.PurchasesService;
import java.math.BigDecimal;
import java.time.LocalDate;
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
@RequestMapping("")
public class PurchaseRESTController {
    
    @Autowired
    PurchasesService purchaseService;
    
    @GetMapping("/allpurchase")
    public List<Purchases> getAllPurchases() throws DoesNotExistException {
        return purchaseService.getAllPurchases();
    }
    
    @GetMapping("/getpurchase/{purchaseId}")
    public ResponseEntity<Purchases> getPurchaseById(@PathVariable int purchaseId) throws DoesNotExistException {
        
        Purchases purchase = purchaseService.getPurchaseById(purchaseId);
        
        if (purchase == null) {
            return new ResponseEntity(new Error(), HttpStatus.NOT_FOUND);
        }
        
        return ResponseEntity.ok(purchase);
    }
    
    @CrossOrigin(origins = "*")
    @PostMapping("/createpurchase")
    public ResponseEntity<Purchases> createPurchase(@RequestBody Purchases purchase) throws InvalidEntryException, DoesNotExistException {
        
        purchase = purchaseService.createPurchase(purchase);
        
        return ResponseEntity.ok(purchase);
    }
    
    @CrossOrigin(origins = "*")
    @PutMapping("/updatepurchase")
    public ResponseEntity<String> updatePurchase(@RequestBody Purchases purchase) throws InvalidEntryException, DoesNotExistException {
        
        purchaseService.updatePurchase(purchase);
        
        return ResponseEntity.ok("Update Success!");
    }
    
    @GetMapping("/purchaseinventory/usersales/{userId}")
    public ResponseEntity<BigDecimal> userSales(@PathVariable int userId, @RequestBody DateRange dates) {
        BigDecimal total;
        try {
            total = purchaseService.getUserSells(userId, dates.getStartDate(), dates.getEndDate());
        } catch (DoesNotExistException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.OK);
        }
        return new ResponseEntity(total, HttpStatus.OK);

    }
    
    @GetMapping("/purchaseinventory/usernumsold/{id}")
    public ResponseEntity<Integer> userNumSold(@PathVariable int userId, @RequestBody DateRange dates) {
        int total;
        try {
            total = purchaseService.getUserNumSold(userId, dates.getStartDate(), dates.getEndDate());
        } catch (DoesNotExistException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.OK);
        }
        return new ResponseEntity(total, HttpStatus.OK);
    }
    
    public static class DateRange {

        LocalDate startDate;
        LocalDate endDate;

        public DateRange() {
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public void setStartDate(LocalDate startDate) {
            this.startDate = startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public void setEndDate(LocalDate endDate) {
            this.endDate = endDate;
        }

    }
}
