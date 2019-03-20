/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.service;

import com.example.carDealership.dao.SpecialsDao;
import com.example.carDealership.entities.Specials;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author travisramos
 */
@Service
public class SpecialsServiceImpl implements SpecialsService {
    
    SpecialsDao specialsDao;
    
    @Autowired
    public SpecialsServiceImpl(SpecialsDao specialsDao) {
        this.specialsDao = specialsDao;
    }

    @Override
    public Specials getSpecialById(int specialId) throws DoesNotExistException {
        
        Specials special = specialsDao.getSpecialById(specialId);
        
        if(special == null) {
            throw new DoesNotExistException();
        }
        
        return special;
    }

    @Override
    public List<Specials> getAllSpecials() {
        List<Specials> list = specialsDao.getAllSpecials();
        return list;
    }

    @Override
    public Specials createSpecial(Specials special) throws InvalidEntryException {
        
        validateRequiredFields(special);
        return specialsDao.createSpecial(special);
    }

    @Override
    public void updateSpecial(Specials special) throws InvalidEntryException {
        validateRequiredFields(special);
        specialsDao.updateSpecial(special);
    }

    @Override
    public void deleteSpecial(int specialId) {
        specialsDao.deleteSpecial(specialId);
    }
    
    private void validateRequiredFields(Specials special) throws InvalidEntryException {
        
        if(special.getTitle() == null
                || special.getTitle().trim().length() == 0
                || special.getDescription() == null
                || special.getDescription().trim().length() == 0) {
            throw new InvalidEntryException();
        }
    }
    
}
