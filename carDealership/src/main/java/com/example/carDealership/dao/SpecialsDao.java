/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.dao;

import com.example.carDealership.entities.Specials;
import java.util.List;

/**
 *
 * @author travisramos
 */
public interface SpecialsDao {
    
    Specials getSpecialById(int specialId);
    List<Specials> getAllSpecials();
    Specials createSpecial(Specials special);
    void updateSpecial(Specials special);
    void deleteSpecial(int specialId);
}
