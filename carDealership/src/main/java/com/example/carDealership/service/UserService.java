/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.service;

import com.example.carDealership.entities.User;
import java.util.List;

/**
 *
 * @author travisramos
 */
public interface UserService {
    
    User getUserById(Integer userId) throws DoesNotExistException;
    
    List<User> getAllUsers() throws DoesNotExistException;
    
    User addUser(User user) throws InvalidEntryException;
    
    void updateUser(User user) throws InvalidEntryException;
    
}
