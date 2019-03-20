/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.service;

import com.example.carDealership.entities.Make;
import com.example.carDealership.entities.User;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
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
public class MakeServiceTest {
    
    @Autowired
    MakeService makeService;
    
    @Autowired
    UserService userService;
    
    User user = new User();
    User userTwo = new User();
    
    public MakeServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws InvalidEntryException {
        
        user.setFirstName("Travis");
        user.setLastName("Ramos");
        user.setEmail("test@test.com");
        user.setPassword("password");
        user.setRole(1);
        user = userService.addUser(user);
        
        userTwo.setFirstName("Travis");
        userTwo.setLastName("Ramos");
        userTwo.setEmail("test@test.com");
        userTwo.setPassword("password");
        userTwo.setRole(1);
        userTwo = userService.addUser(userTwo);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createMake method, of class MakeService.
     */
    @Test
    public void testCreateMake() throws DoesNotExistException {
        
        //Arrange
        LocalDate ld = LocalDate.parse("01-10-2019", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        
        Make make = new Make();
        make.setMakeName("GMC");
        make.setDateAdded(ld);
        make.setUser(user);
        
        //Act
        Make createdMake = makeService.createMake(make);
        Make fetchedMake = makeService.getMakeById(createdMake.getMakeId());
        
        //Assert
        assertEquals(fetchedMake.getMakeName(), "GMC");
    }
    
    /**
     * Test of getMakeById method, of class MakeService.
     */
    @Test
    public void testGetMakeById() throws DoesNotExistException {
        
        //Arrange
        LocalDate ld = LocalDate.parse("10-10-2020", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        
        Make make = new Make();
        make.setMakeName("GMC");
        make.setDateAdded(ld);
        make.setUser(user);
        
        Make createdMake = makeService.createMake(make);
        
        //Act
        Make fetchedMake = makeService.getMakeById(createdMake.getMakeId());
        
        //Assert
        assertEquals(createdMake.getMakeName(), fetchedMake.getMakeName());
        assertEquals(createdMake.getUser().getUserId(), fetchedMake.getUser().getUserId());
    }

    /**
     * Test of getAllMakes method, of class MakeService.
     */
    @Test
    public void testGetAllMakes() throws DoesNotExistException {
        
        //Arrange
        LocalDate ld = LocalDate.parse("10-10-2020", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        
        Make make = new Make();
        make.setMakeName("GMC");
        make.setDateAdded(ld);
        make.setUser(user);
        
        makeService.createMake(make);
        
        Make makeTwo = new Make();
        makeTwo.setMakeName("BMW");
        makeTwo.setDateAdded(ld);
        makeTwo.setUser(userTwo);
        
        makeService.createMake(makeTwo);
        
        //Act
        List<Make> list = makeService.getAllMakes();
        
        //Assert
        assertEquals(list.size(), 2);
        assertEquals(list.get(0).getMakeName(), "GMC");
        assertEquals(list.get(1).getMakeName(), "BMW");
    }

    /**
     * Test of updateMake method, of class MakeService.
     */
    @Test
    public void testUpdateMake() throws DoesNotExistException {
        
        //Arrange
        LocalDate ld = LocalDate.parse("10-10-2020", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        
        Make make = new Make();
        make.setMakeName("GMC");
        make.setDateAdded(ld);
        make.setUser(user);
        
        Make createdMake = makeService.createMake(make);
        
        createdMake.setMakeName("BMW");
        createdMake.setUser(userTwo);;
        
        //Act
        makeService.updateMake(createdMake);
        Make fetchedMake = makeService.getMakeById(createdMake.getMakeId());
        
        //Assert
        assertEquals(fetchedMake.getMakeName(), "BMW");
        assertEquals(fetchedMake.getUser().getUserId(), userTwo.getUserId());
    }
    
}
