/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.service;

import com.example.carDealership.entities.Model;
import java.util.List;

/**
 *
 * @author travisramos
 */
public interface ModelService {
    
    Model getModelById(int modelId) throws DoesNotExistException;
    
    List<Model> getAllModels() throws DoesNotExistException;
    
    Model createModel(Model model) throws DoesNotExistException;
    
    void updateModel(Model model) throws DoesNotExistException;

}
