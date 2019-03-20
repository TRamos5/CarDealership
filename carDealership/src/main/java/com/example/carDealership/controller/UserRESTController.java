/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.controller;

import com.example.carDealership.entities.User;
import com.example.carDealership.service.DoesNotExistException;
import com.example.carDealership.service.InvalidEntryException;
import com.example.carDealership.service.UserService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author travisramos
 */
@RestController
@RequestMapping("/user/admin")
public class UserRESTController {

    UserService userService;

    public UserRESTController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/getuser/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable int userId) throws DoesNotExistException {
        User user = userService.getUserById(userId);
        
        if(user == null) {
            return new ResponseEntity(new Error(), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/allusers")
    public ResponseEntity<List<User>> getAllUsers() {
        
        List<User> users;
        
        try {
            users = userService.getAllUsers();
        } catch (DoesNotExistException ex) {
            return new ResponseEntity(new Error(), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return ResponseEntity.ok(users);
    }
    
    @CrossOrigin(origins = "*")
    @PostMapping("/adduser")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> addUser(@RequestBody User user) {
        
        try {
            user = userService.addUser(user);
        } catch (InvalidEntryException e) {
            return new ResponseEntity(new Error(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        
        return ResponseEntity.ok(user);
    }
    
    @CrossOrigin(origins = "*")
    @PutMapping("/updateuser")
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        
        try {
            userService.updateUser(user);
        } catch (InvalidEntryException ex) {
            return new ResponseEntity(new Error(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return ResponseEntity.ok("Update Success!");
        
    }

}
