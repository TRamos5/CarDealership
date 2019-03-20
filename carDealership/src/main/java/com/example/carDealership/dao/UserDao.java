/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.dao;

import com.example.carDealership.entities.User;
import java.util.List;

/**
 *
 * @author travisramos
 */
public interface UserDao {
    
    User getUserById(int userId);
    List<User> getAllUsers();
    User addUser(User user);
    void updateUser(User user);
    
}
