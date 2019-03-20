/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.dao;

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
public class MakeDaoTest {
    
    @Autowired
    MakeDao makeDao;
    
    @Autowired
    UserDao userDao;
    
    User user = new User();
    User userTwo = new User();
    
    public MakeDaoTest() {
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
        
        userTwo.setFirstName("Travis");
        userTwo.setLastName("Ramos");
        userTwo.setEmail("test@test.com");
        userTwo.setPassword("password");
        userTwo.setRole(1);
        userTwo = userDao.addUser(userTwo);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createMake method, of class MakeDao.
     */
    @Test
    public void testCreateMake() {
        
        //Arrange
        LocalDate ld = LocalDate.parse("10-10-2020", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        
        Make make = new Make();
        make.setMakeName("GMC");
        make.setDateAdded(ld);
        make.setUser(user);
        
        //Act
        Make createdMake = makeDao.createMake(make);
        Make fetchedMake = makeDao.getMakeById(createdMake.getMakeId());
        
        //Assert
        assertEquals(fetchedMake.getMakeName(), "GMC");
        assertEquals(fetchedMake.getDateAdded(), LocalDate.parse("10-10-2020", DateTimeFormatter.ofPattern("MM-dd-yyyy")));
    }
    
    /**
     * Test of getMakeById method, of class MakeDao.
     */
    @Test
    public void testGetMakeById() {
        
        //Arrange
        LocalDate ld = LocalDate.parse("10-10-2020", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        
        Make make = new Make();
        make.setMakeName("GMC");
        make.setDateAdded(ld);
        make.setUser(user);
        
        Make createdMake = makeDao.createMake(make);
        
        //Act
        Make fetchedMake = makeDao.getMakeById(createdMake.getMakeId());
        
        //Assert
        assertEquals(createdMake.getMakeName(), fetchedMake.getMakeName());
        assertEquals(createdMake.getDateAdded(), fetchedMake.getDateAdded());
        assertEquals(createdMake.getUser().getUserId(), fetchedMake.getUser().getUserId());
    }

    /**
     * Test of getAllMakes method, of class MakeDao.
     */
    @Test
    public void testGetAllMakes() {
        
        //Arrange
        LocalDate ld = LocalDate.parse("10-10-2020", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        
        Make make = new Make();
        make.setMakeName("GMC");
        make.setDateAdded(ld);
        make.setUser(user);
        
        makeDao.createMake(make);
        
        Make makeTwo = new Make();
        makeTwo.setMakeName("BMW");
        makeTwo.setDateAdded(ld);
        makeTwo.setUser(userTwo);
        
        makeDao.createMake(makeTwo);
        
        //Act
        List<Make> list = makeDao.getAllMakes();
        
        //Assert
        assertEquals(list.size(), 2);
        assertEquals(list.get(0).getMakeName(), "GMC");
        assertEquals(list.get(1).getMakeName(), "BMW");
        
    }

    /**
     * Test of updateMake method, of class MakeDao.
     */
    @Test
    public void testUpdateMake() {
        
        //Arrange
        LocalDate ld = LocalDate.parse("10-10-2020", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        
        Make make = new Make();
        make.setMakeName("GMC");
        make.setDateAdded(ld);
        make.setUser(user);
        
        Make createdMake = makeDao.createMake(make);
        
        createdMake.setMakeName("BMW");
        createdMake.setUser(userTwo);;
        
        //Act
        makeDao.updateMake(createdMake);
        Make fetchedMake = makeDao.getMakeById(createdMake.getMakeId());
        
        //Assert
        assertEquals(fetchedMake.getMakeName(), "BMW");
        assertEquals(fetchedMake.getUser().getUserId(), userTwo.getUserId());
    }
    
}
