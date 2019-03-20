/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.service;

import com.example.carDealership.entities.Specials;
import java.util.List;

/**
 *
 * @author travisramos
 */
public interface SpecialsService {
    
    Specials getSpecialById(int specialId) throws DoesNotExistException;
    
    List<Specials> getAllSpecials();
    
    Specials createSpecial(Specials special) throws InvalidEntryException;
    
    void updateSpecial(Specials special) throws InvalidEntryException;
    
    void deleteSpecial(int specialId);
}
