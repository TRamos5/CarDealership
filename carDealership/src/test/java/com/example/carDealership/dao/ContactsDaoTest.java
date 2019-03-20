/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.dao;

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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
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
public class ContactsDaoTest {
    
    @Autowired
    ContactsDao contactsDao;
    
    @Autowired
    VehicleDao vehicleDao;
    
    @Autowired
    MakeDao makeDao;
    
    @Autowired
    ModelDao modelDao;
    
    @Autowired
    UserDao userDao;
    
    User user = new User();
    Make make = new Make();
    Model model = new Model();
    
    public ContactsDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
        user.setFirstName("Travis");
        user.setLastName("Ramos");
        user.setEmail("test@test.com");
        user.setPassword("password");
        user.setRole(1);
        user = userDao.addUser(user);
        
        LocalDate ld = LocalDate.parse("10-10-2020", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        
        make.setMakeName("GMC");
        make.setDateAdded(ld);
        make.setUser(user);
        make = makeDao.createMake(make);
        
        model.setModelName("Tesla");
        model.setDateAdded(ld);
        model.setUser(user);
        model.setMake(make);
        model = modelDao.createModel(model);
        
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createContact method, of class ContactsDao.
     */
    @Test
    public void testCreateContact() {
        
        //Arrange
        Contacts contact = new Contacts();
        contact.setName("Travis");
        contact.setPhone("2343332323");
        contact.setMessage("Hello");
        contact.setEmail("test@test.com");
        
        //Act
        Contacts createdContact = contactsDao.createContact(contact);
        
        //Assert
        assertEquals(createdContact.getName(), "Travis");
    }
    
    /**
     * Test of getContactById method, of class ContactsDao.
     */
    @Test
    public void testGetContactById() {
        
        //Arrange
        Contacts contact = new Contacts();
        contact.setName("Travis");
        contact.setPhone("2343332323");
        contact.setMessage("Hello");
        contact.setEmail("test@test.com");
        
        Contacts createdContact = contactsDao.createContact(contact);
        
        //Act
        Contacts fetchedContact = contactsDao.getContactById(createdContact.getContactId());
        
        //Assert
        assertEquals(fetchedContact.getName(), createdContact.getName());
    }

    /**
     * Test of getAllContacts method, of class ContactsDao.
     */
    @Test
    public void testGetAllContacts() {
        
        //Arrange
        Contacts contact = new Contacts();
        contact.setName("Travis");
        contact.setPhone("2343332323");
        contact.setMessage("Hello");
        contact.setEmail("test@test.com");
        
        Contacts createdContact = contactsDao.createContact(contact);
        
        Contacts contactTwo = new Contacts();
        contactTwo.setName("Joe");
        contactTwo.setPhone("2343332323");
        contactTwo.setMessage("Hello");
        contactTwo.setEmail("test@test.com");
        
        Contacts createdContactTwo = contactsDao.createContact(contactTwo);
        
        //Act
        List<Contacts> list = contactsDao.getAllContacts();
        
        //Assert
        assertEquals(list.size(), 2);
        assertEquals(list.get(0).getName(), "Travis");
        assertEquals(list.get(1).getName(), "Joe");
    }

    /**
     * Test of updateContact method, of class ContactsDao.
     */
    @Test
    public void testUpdateContact() {
        
        //Arrange
        Contacts contact = new Contacts();
        contact.setName("Travis");
        contact.setPhone("2343332323");
        contact.setMessage("Hello");
        contact.setEmail("test@test.com");
        
        Contacts createdContact = contactsDao.createContact(contact);
        
        createdContact.setName("Joe");
        
        //Act
        contactsDao.updateContact(createdContact);
        Contacts fetchedContact = contactsDao.getContactById(createdContact.getContactId());
        
        //Assert
        assertEquals(fetchedContact.getName(), "Joe");
    }

    /**
     * Test of deleteContact method, of class ContactsDao.
     */
    @Test
    public void testDeleteContact() {
        
        //Arrange
        Contacts contact = new Contacts();
        contact.setName("Travis");
        contact.setPhone("2343332323");
        contact.setMessage("Hello");
        contact.setEmail("test@test.com");
        
        Contacts createdContact = contactsDao.createContact(contact);
        
        //Act
        contactsDao.deleteContact(createdContact.getContactId());
        
        Contacts fetchedContact = contactsDao.getContactById(createdContact.getContactId());
        
        //Assert
        assertNull(fetchedContact);
    }
    
}
