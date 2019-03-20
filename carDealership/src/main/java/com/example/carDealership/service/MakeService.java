/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.service;

import com.example.carDealership.entities.Make;
import java.util.List;

/**
 *
 * @author travisramos
 */
public interface MakeService {
    
    Make getMakeById(int makeId) throws DoesNotExistException;
    
    List<Make> getAllMakes() throws DoesNotExistException;
    
    Make createMake(Make make) throws DoesNotExistException;
    
    void updateMake(Make make);
    
}
