/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.service;

import com.example.carDealership.entities.Contacts;
import com.example.carDealership.entities.Make;
import com.example.carDealership.entities.Model;
import com.example.carDealership.entities.User;
import com.example.carDealership.entities.Vehicle;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author travisramos
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback
@Transactional
public class ContactsServiceTest {
    
    @Autowired
    ContactsService contactsService;
    
    @Autowired
    VehicleService vehicleService;
    
    @Autowired
    MakeService makeService;
    
    @Autowired
    ModelService modelService;
    
    @Autowired
    UserService userService;
    
    User user = new User();
    Make make = new Make();
    Model model = new Model();
    
    public ContactsServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws InvalidEntryException, DoesNotExistException {
        
        user.setFirstName("Travis");
        user.setLastName("Ramos");
        user.setEmail("test@test.com");
        user.setPassword("password");
        user.setRole(1);
        user = userService.addUser(user);
        
        LocalDate ld = LocalDate.parse("10-10-2020", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        
        make.setMakeName("GMC");
        make.setDateAdded(ld);
        make.setUser(user);
        make = makeService.createMake(make);
        
        model.setModelName("Tesla");
        model.setDateAdded(ld);
        model.setUser(user);
        model.setMake(make);
        model = modelService.createModel(model);
        
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createContact method, of class ContactsService.
     */
    @Test
    public void testCreateContact() throws Exception {
        
        //Arrange
        Contacts contact = new Contacts();
        contact.setName("Travis");
        contact.setPhone("2343332324");
        contact.setMessage("Hello");
        contact.setEmail("test@test.com");
        
        //Act
        Contacts createdContact = contactsService.createContact(contact);
        
        //Assert
        assertEquals(createdContact.getName(), "Travis");
    }
    
    /**
     * Test of getContactById method, of class ContactsService.
     */
    @Test
    public void testGetContactById() throws Exception {
        
        //Arrange
        Contacts contact = new Contacts();
        contact.setName("Travis");
        contact.setPhone("2343332323");
        contact.setMessage("Hello");
        contact.setEmail("test@test.com");
        
        Contacts createdContact = contactsService.createContact(contact);
        
        //Act
        Contacts fetchedContact = contactsService.getContactById(createdContact.getContactId());
        
        //Assert
        assertEquals(fetchedContact.getName(), createdContact.getName());
    }

    /**
     * Test of getAllContacts method, of class ContactsService.
     */
    @Test
    public void testGetAllContacts() throws InvalidEntryException {
        
        //Arrange
        Contacts contact = new Contacts();
        contact.setName("Travis");
        contact.setPhone("2343332323");
        contact.setMessage("Hello");
        contact.setEmail("test@test.com");
        
        Contacts createdContact = contactsService.createContact(contact);
        
        Contacts contactTwo = new Contacts();
        contactTwo.setName("Joe");
        contactTwo.setPhone("2343332323");
        contactTwo.setMessage("Hello");
        contactTwo.setEmail("test@test.com");
        
        Contacts createdContactTwo = contactsService.createContact(contactTwo);
        
        //Act
        List<Contacts> list = contactsService.getAllContacts();
        
        //Assert
        assertEquals(list.size(), 2);
        assertEquals(list.get(0).getName(), "Travis");
        assertEquals(list.get(1).getName(), "Joe");
    }

    /**
     * Test of updateContact method, of class ContactsService.
     */
    @Test
    public void testUpdateContact() throws Exception {
        
        //Arrange
        Contacts contact = new Contacts();
        contact.setName("Travis");
        contact.setPhone("2343332323");
        contact.setMessage("Hello");
        contact.setEmail("test@test.com");
        
        Contacts createdContact = contactsService.createContact(contact);
        
        createdContact.setName("Joe");
        
        //Act
        contactsService.updateContact(createdContact);
        Contacts fetchedContact = contactsService.getContactById(createdContact.getContactId());
        
        //Assert
        assertEquals(fetchedContact.getName(), "Joe");
    }

    /**
     * Test of deleteContact method, of class ContactsService.
     */
    @Test
    public void testDeleteContact() throws InvalidEntryException, DoesNotExistException {
        
        //Arrange
        Contacts contact = new Contacts();
        contact.setName("Travis");
        contact.setPhone("2343332323");
        contact.setMessage("Hello");
        contact.setEmail("test@test.com");
        
        Contacts createdContact = contactsService.createContact(contact);
        
        //Act
        contactsService.deleteContact(createdContact.getContactId());
        
        //Assert
        try {
            contactsService.getContactById(createdContact.getContactId());
            fail("Expected DoesNotExistException was not thrown.");
        } catch (DoesNotExistException ex) {
            
        }
    }
    
    @Test
    public void testName() {
        
        //Arrange
        Contacts contact = new Contacts();
        contact.setName("");
        contact.setPhone("2343332323");
        contact.setMessage("Hello");
        contact.setEmail("test@test.com");
        
        try {
            contactsService.createContact(contact);
            fail("Error should be thrown");
        } catch (InvalidEntryException e) {
            
        }
    }
    
    @Test
    public void testMessage() {
        
        //Arrange
        Contacts contact = new Contacts();
        contact.setName("");
        contact.setPhone("2343332323");
        contact.setMessage("");
        contact.setEmail("test@test.com");
        
        try {
            contactsService.createContact(contact);
            fail("Error should be thrown");
        } catch (InvalidEntryException e) {
            
        }
    }
    
}
