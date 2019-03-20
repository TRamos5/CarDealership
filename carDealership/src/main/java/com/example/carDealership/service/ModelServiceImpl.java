/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.service;

import com.example.carDealership.dao.ModelDao;
import com.example.carDealership.entities.Model;
import com.example.carDealership.entities.User;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author travisramos
 */
@Service
public class ModelServiceImpl implements ModelService{
    
    ModelDao modelDao;
    
    @Autowired
    UserService userService;
    
    @Autowired
    MakeService makeService;
    
    @Autowired
    public ModelServiceImpl(ModelDao modelDao) {
        this.modelDao = modelDao;
    }

    @Override
    public Model getModelById(int modelId) throws DoesNotExistException {
        
        Model model = modelDao.getModelById(modelId);
        
        if(model == null) {
            throw new DoesNotExistException();
        }
        
        model.setUser(userService.getUserById(model.getUser().getUserId()));
        model.setMake(makeService.getMakeById(model.getMake().getMakeId()));
        
        return model;
    }

    @Override
    public List<Model> getAllModels() throws DoesNotExistException {
        
        List<Model> list = modelDao.getAllModels();
        
        for(Model models : list) {
            models.setUser(userService.getUserById(models.getUser().getUserId()));
            models.setMake(makeService.getMakeById(models.getMake().getMakeId()));
        }
        return list;
    }

    @Override
    public Model createModel(Model model) throws DoesNotExistException {
        model.setDateAdded(LocalDate.now());
        model.setUser(userService.getUserById(model.getUser().getUserId()));
        model.setMake(makeService.getMakeById(model.getMake().getMakeId()));
        return modelDao.createModel(model);
    }

    @Override
    public void updateModel(Model model) throws DoesNotExistException {
        modelDao.updateModel(model);
    }
    
}
