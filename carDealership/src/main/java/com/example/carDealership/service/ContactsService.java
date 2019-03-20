/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.service;

import com.example.carDealership.entities.Contacts;
import java.util.List;

/**
 *
 * @author travisramos
 */
public interface ContactsService {
    
    Contacts getContactById(int contactId) throws DoesNotExistException;
    
    List<Contacts> getAllContacts();
    
    Contacts createContact(Contacts contact) throws InvalidEntryException;
    
    void updateContact(Contacts contact) throws InvalidEntryException;
    
    void deleteContact(int contactId);
    
}
