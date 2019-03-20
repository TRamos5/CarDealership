/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.dao;

import com.example.carDealership.entities.Make;
import java.util.List;

/**
 *
 * @author travisramos
 */
public interface MakeDao {
    
    Make getMakeById(int makeId);
    List<Make> getAllMakes();
    Make createMake(Make make);
    void updateMake(Make make);
}
