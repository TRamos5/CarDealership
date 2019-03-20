/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.service;

import com.example.carDealership.dao.ContactsDao;
import com.example.carDealership.entities.Contacts;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author travisramos
 */
@Service
public class ContactsServiceImpl implements ContactsService {
    
    ContactsDao contactsDao;
    
    @Autowired
    public ContactsServiceImpl(ContactsDao contactsDao) {
        this.contactsDao = contactsDao;
    }

    @Override
    public Contacts getContactById(int contactId) throws DoesNotExistException{
        Contacts con = contactsDao.getContactById(contactId);
        
        if(con == null) {
            throw new DoesNotExistException();
        }
        
        return con;
    }

    @Override
    public List<Contacts> getAllContacts() {
        List<Contacts> list = contactsDao.getAllContacts();
        return list;
    }

    @Override
    public Contacts createContact(Contacts contact) throws InvalidEntryException {
        validateEntries(contact);
        return contactsDao.createContact(contact);
    }

    @Override
    public void updateContact(Contacts contact) throws InvalidEntryException{
        
        validateEntries(contact);
        contactsDao.updateContact(contact);
        
    }

    @Override
    public void deleteContact(int contactId) {
        contactsDao.deleteContact(contactId);
    }
    
    private void validateEntries(Contacts contact) throws InvalidEntryException {
        if(contact.getName() == null ||
                contact.getName().trim().length() == 0 ||
                contact.getMessage() == null || 
                contact.getMessage().trim().length() == 0) {
            throw new InvalidEntryException();
        }
        
        if(contact.getEmail() == null  || contact.getEmail().trim().length() == 0 
                && contact.getPhone() == null || contact.getPhone().trim().length() == 0) {
            throw new InvalidEntryException();
        }
    }
    
}
