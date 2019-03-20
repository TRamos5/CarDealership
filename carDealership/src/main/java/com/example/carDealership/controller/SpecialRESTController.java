/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.controller;

import com.example.carDealership.entities.Specials;
import com.example.carDealership.service.DoesNotExistException;
import com.example.carDealership.service.InvalidEntryException;
import com.example.carDealership.service.SpecialsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author travisramos
 */
@RestController
@RequestMapping("/special")
public class SpecialRESTController {
    
    @Autowired
    SpecialsService specialService;
    
    @GetMapping("/allspecial")
    public List<Specials> getAllSpecials() {
        return specialService.getAllSpecials();
    }
    
    @GetMapping("/getspecial/{specialId}")
    public ResponseEntity<Specials> getSpecialById(@PathVariable int specialId) throws DoesNotExistException {
        Specials special = specialService.getSpecialById(specialId);

        if (special == null) {
            return new ResponseEntity(new Error(), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(special);
    }
    
    @CrossOrigin(origins = "*")
    @PostMapping("/createspecial")
    public ResponseEntity<Specials> createSpecial(@RequestBody Specials special) throws InvalidEntryException {
        
         special = specialService.createSpecial(special);
         
         return ResponseEntity.ok(special);
    }
    
    @DeleteMapping("/deletespecial/{specialId}")
    public ResponseEntity deleteSpecial(@PathVariable int specialId) {
        specialService.deleteSpecial(specialId);
        
        return new ResponseEntity(HttpStatus.OK);
    }
}
