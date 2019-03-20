/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.dao;

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
public class ModelDaoTest {
    
    @Autowired
    ModelDao modelDao;
    
    @Autowired
    MakeDao makeDao;
    
    @Autowired
    UserDao userDao;
    
    User user = new User();
    User userTwo = new User();
    
    Make make = new Make();
    
    public ModelDaoTest() {
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
        
        LocalDate ld = LocalDate.parse("10-10-2020", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        
        make.setMakeName("GMC");
        make.setDateAdded(ld);
        make.setUser(user);
        make = makeDao.createMake(make);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createModel method, of class ModelDaoDB.
     */
    @Test
    public void testCreateModel() {
        
        //Arrange
        LocalDate ld = LocalDate.parse("10-10-2020", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        
        Model model = new Model();
        model.setModelName("Tesla");
        model.setDateAdded(ld);
        model.setUser(user);
        model.setMake(make);
        
        //Act
        Model createdModel = modelDao.createModel(model);
        
        //Assert
        assertEquals(createdModel.getModelName(), "Tesla");
        assertEquals(createdModel.getDateAdded(), LocalDate.parse("10-10-2020", DateTimeFormatter.ofPattern("MM-dd-yyyy")));
        assertEquals(createdModel.getUser().getUserId(), model.getUser().getUserId());
        
    }
    
    /**
     * Test of getModelById method, of class ModelDaoDB.
     */
    @Test
    public void testGetModelById() {
        
        //Arrange
        LocalDate ld = LocalDate.parse("10-10-2020", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        
        Model model = new Model();
        model.setModelName("Tesla");
        model.setDateAdded(ld);
        model.setUser(user);
        model.setMake(make);
        
        Model createdModel = modelDao.createModel(model);
        
        //Act
        Model fetchedModel = modelDao.getModelById(createdModel.getModelId());
        
        //Assert
        assertEquals(createdModel.getModelName(), fetchedModel.getModelName());
        assertEquals(createdModel.getDateAdded(), fetchedModel.getDateAdded());
    }

    /**
     * Test of getAllModels method, of class ModelDaoDB.
     */
    @Test
    public void testGetAllModels() {
        
        //Arrange
        LocalDate ld = LocalDate.parse("10-10-2020", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        
        Model model = new Model();
        model.setModelName("Tesla");
        model.setDateAdded(ld);
        model.setUser(user);
        model.setMake(make);
        
        modelDao.createModel(model);
        
        LocalDate ldTwo = LocalDate.parse("10-10-2020", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        
        Model modelTwo = new Model();
        modelTwo.setModelName("Hummer");
        modelTwo.setDateAdded(ldTwo);
        modelTwo.setUser(user);
        modelTwo.setMake(make);
        
        modelDao.createModel(modelTwo);
        
        //Act
        List<Model> list = modelDao.getAllModels();
        
        //Assert
        assertEquals(list.size(), 2);
        assertEquals(list.get(0).getModelName(), "Tesla");
        assertEquals(list.get(1).getModelName(), "Hummer");
    }

    /**
     * Test of updateModel method, of class ModelDaoDB.
     */
    @Test
    public void testUpdateModel() {
        
        //Arrange
        LocalDate ld = LocalDate.parse("10-10-2020", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        
        Model model = new Model();
        model.setModelName("Tesla");
        model.setDateAdded(ld);
        model.setUser(user);
        model.setMake(make);
        
        Model createdModel = modelDao.createModel(model);
        
        createdModel.setModelName("Hummer");
        
        //Act
        modelDao.updateModel(createdModel);
        Model fetchedModel = modelDao.getModelById(createdModel.getModelId());
        
        //Assert
        assertEquals(fetchedModel.getModelName(), "Hummer"); 
    }
    
}
