/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.controller;

import com.example.carDealership.entities.Contacts;
import com.example.carDealership.service.ContactsService;
import com.example.carDealership.service.InvalidEntryException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author travisramos
 */
@RestController
public class ContactRESTController {
    
    @Autowired
    ContactsService contactService;
    
    @GetMapping("/allcontacts")
    public List<Contacts> getAllContacts() {
        return contactService.getAllContacts();
    }
    
    @CrossOrigin(origins = "*")
    @PostMapping("/createcontact")
    public ResponseEntity<Contacts> createcontact(@RequestBody Contacts contact) throws InvalidEntryException {
        
        contact = contactService.createContact(contact);
        
        return ResponseEntity.ok(contact);
    }
}
