/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.dao;

import com.example.carDealership.entities.Model;
import java.util.List;

/**
 *
 * @author travisramos
 */
public interface ModelDao {
    
    Model getModelById(int modelId);
    List<Model> getAllModels();
    Model createModel(Model model);
    void updateModel(Model model);
}
