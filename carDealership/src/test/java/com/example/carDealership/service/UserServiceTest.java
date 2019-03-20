/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.service;

import com.example.carDealership.entities.User;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
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
public class UserServiceTest {
    
    @Autowired
    UserService userService;
    
    public UserServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addUser method, of class UserService.
     */
    @Test
    public void testAddUser() throws Exception {
        
        //Arrange
        User user = new User();
        user.setFirstName("Travis");
        user.setLastName("Ramos");
        user.setEmail("test@test.com");
        user.setPassword("password");
        user.setRole(1);
        
        //Act
        User createUser = userService.addUser(user);
        
        //Assert
        assertEquals(createUser.getFirstName(), "Travis");
    }
    
    /**
     * Test of getUserById method, of class UserService.
     */
    @Test
    public void testGetUserById() throws Exception {
        
        //Arrange
        User user = new User();
        user.setFirstName("Travis");
        user.setLastName("Ramos");
        user.setEmail("test@test.com");
        user.setPassword("password");
        user.setRole(1);
        
        //Act
        User createUser = userService.addUser(user);
        
        //Assert
        User fetchedUser = userService.getUserById(createUser.getUserId());
        
        assertEquals(fetchedUser.getFirstName(), "Travis");
        assertEquals(fetchedUser.getRole(), 1);
    }

    /**
     * Test of getAllUsers method, of class UserService.
     */
    @Test
    public void testGetAllUsers() throws Exception {
        
        //Arrange
        User user = new User();
        user.setFirstName("Travis");
        user.setLastName("Ramos");
        user.setEmail("test@test.com");
        user.setPassword("password");
        user.setRole(1);
        
        User userOne = userService.addUser(user);
        
        User userTwo = new User();
        userTwo.setFirstName("Terry");
        userTwo.setLastName("Ramos");
        userTwo.setEmail("test@test.com");
        userTwo.setPassword("password");
        userTwo.setRole(2);
        
        User user2 = userService.addUser(userTwo);
        
        //Act
        List<User> list = userService.getAllUsers();
        
        //Assert
        assertEquals(list.size(), 2);
        
        assertEquals(list.get(0).getFirstName(), userOne.getFirstName());
        assertEquals(list.get(1).getFirstName(), user2.getFirstName());
    }

    /**
     * Test of updateUser method, of class UserService.
     */
    @Test
    public void testUpdateUser() throws Exception {
        
        //Arrange
        User user = new User();
        user.setFirstName("Travis");
        user.setLastName("Ramos");
        user.setEmail("test@test.com");
        user.setPassword("password");
        user.setRole(1);
        
        User firstUser = userService.addUser(user);
        
        firstUser.setFirstName("Joe");
        firstUser.setLastName("Ram");
        firstUser.setPassword("pass");
        
        //Act
        userService.updateUser(firstUser);
        
        //Assert
        User fetchedUser = userService.getUserById(firstUser.getUserId());
        
        assertEquals(fetchedUser.getFirstName(), "Joe");
        assertEquals(fetchedUser.getPassword(), "pass");
    }
    
    @Test
    public void testCreateUserNoFirstName() {
        
        User user = new User();
        user.setFirstName("");
        user.setLastName("Ramos");
        user.setEmail("test@test.com");
        user.setPassword("password");
        user.setRole(1);
        
        try {
            userService.addUser(user);
            fail("Should have thrown error");
        } catch(InvalidEntryException e){
            
        }
    }
    
    @Test
    public void testCreateUserNoLastName() {
        
        User user = new User();
        user.setFirstName("Travis");
        user.setLastName("");
        user.setEmail("test@test.com");
        user.setPassword("password");
        user.setRole(1);
        
        try {
            userService.addUser(user);
            fail("Should have thrown error");
        } catch(InvalidEntryException e){
            
        }
    }
    
}
