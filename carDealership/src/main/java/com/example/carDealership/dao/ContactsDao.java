/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.dao;

import com.example.carDealership.entities.Contacts;
import java.util.List;

/**
 *
 * @author travisramos
 */
public interface ContactsDao {
    
    Contacts getContactById(int contactId);
    List<Contacts> getAllContacts();
    Contacts createContact(Contacts contact);
    void updateContact(Contacts contact);
    void deleteContact(int contactId);
}
