/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.service;

import com.example.carDealership.entities.Make;
import com.example.carDealership.entities.Model;
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
public class ModelServiceTest {
    
    @Autowired
    ModelService modelService;
    
    @Autowired
    UserService userService;
    
    @Autowired
    MakeService makeService;
    
    User user = new User();
    User userTwo = new User();
    
    Make make = new Make();
    
    public ModelServiceTest() {
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
        
        userTwo.setFirstName("Travis");
        userTwo.setLastName("Ramos");
        userTwo.setEmail("test@test.com");
        userTwo.setPassword("password");
        userTwo.setRole(1);
        userTwo = userService.addUser(userTwo);
        
        LocalDate ld = LocalDate.parse("10-10-2020", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        
        make.setMakeName("GMC");
        make.setDateAdded(ld);
        make.setUser(user);
        make = makeService.createMake(make);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createModel method, of class ModelService.
     */
    @Test
    public void testCreateModel() throws DoesNotExistException {
        
        //Arrange
        LocalDate ld = LocalDate.parse("01-10-2019", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        
        Model model = new Model();
        model.setModelName("Tesla");
        model.setDateAdded(ld);
        model.setUser(user);
        model.setMake(make);
        
        //Act
        Model createdModel = modelService.createModel(model);
        
        //Assert
        assertEquals(createdModel.getModelName(), "Tesla");
        assertEquals(createdModel.getUser().getUserId(), model.getUser().getUserId());
    }
    
    /**
     * Test of getModelById method, of class ModelService.
     */
    @Test
    public void testGetModelById() throws DoesNotExistException {
        
        //Arrange
        LocalDate ld = LocalDate.parse("10-10-2020", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        
        Model model = new Model();
        model.setModelName("Tesla");
        model.setDateAdded(ld);
        model.setUser(user);
        model.setMake(make);
        
        Model createdModel = modelService.createModel(model);
        
        //Act
        Model fetchedModel = modelService.getModelById(createdModel.getModelId());
        
        //Assert
        assertEquals(createdModel.getModelName(), fetchedModel.getModelName());
    }

    /**
     * Test of getAllModels method, of class ModelService.
     */
    @Test
    public void testGetAllModels() throws DoesNotExistException {
        
        //Arrange
        LocalDate ld = LocalDate.parse("10-10-2020", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        
        Model model = new Model();
        model.setModelName("Tesla");
        model.setDateAdded(ld);
        model.setUser(user);
        model.setMake(make);
        
        modelService.createModel(model);
        
        LocalDate ldTwo = LocalDate.parse("10-10-2020", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        
        Model modelTwo = new Model();
        modelTwo.setModelName("Hummer");
        modelTwo.setDateAdded(ldTwo);
        modelTwo.setUser(user);
        modelTwo.setMake(make);
        
        modelService.createModel(modelTwo);
        
        //Act
        List<Model> list = modelService.getAllModels();
        
        //Assert
        assertEquals(list.size(), 2);
        assertEquals(list.get(0).getModelName(), "Tesla");
        assertEquals(list.get(1).getModelName(), "Hummer");
    }

    /**
     * Test of updateModel method, of class ModelService.
     */
    @Test
    public void testUpdateModel() throws DoesNotExistException {
        
        //Arrange
        LocalDate ld = LocalDate.parse("10-10-2020", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        
        Model model = new Model();
        model.setModelName("Tesla");
        model.setDateAdded(ld);
        model.setUser(user);
        model.setMake(make);
        
        Model createdModel = modelService.createModel(model);
        
        createdModel.setModelName("Hummer");
        
        //Act
        modelService.updateModel(createdModel);
        Model fetchedModel = modelService.getModelById(createdModel.getModelId());
        
        //Assert
        assertEquals(fetchedModel.getModelName(), "Hummer"); 
    }
    
}
