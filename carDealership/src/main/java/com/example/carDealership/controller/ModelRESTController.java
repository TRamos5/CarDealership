/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.controller;

import com.example.carDealership.entities.Model;
import com.example.carDealership.service.DoesNotExistException;
import com.example.carDealership.service.ModelService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("/model/admin")
public class ModelRESTController {
    
    @Autowired
    ModelService modelService;
    
    @GetMapping("/allmodels")
    public List<Model> getAllModels() throws DoesNotExistException {
        return modelService.getAllModels();
    }
    
    @GetMapping("/getmodel/{modelId}")
    public ResponseEntity<Model> getModelById(@PathVariable int modelId) throws DoesNotExistException {
        
        Model model = modelService.getModelById(modelId);
        
        if (model == null) {
            return new ResponseEntity(new Error(), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(model);
    }
    
    @CrossOrigin(origins = "*")
    @PostMapping("/createmodel")
    public ResponseEntity<Model> createModel(@RequestBody Model model) throws DoesNotExistException {
        model = modelService.createModel(model);
        
        return ResponseEntity.ok(model);
    }
}
