/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.dao;

import com.example.carDealership.entities.Make;
import com.example.carDealership.entities.Model;
import com.example.carDealership.entities.Specials;
import com.example.carDealership.entities.User;
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
public class SpecialsDaoTest {
    
    @Autowired
    SpecialsDao specialsDao;
    
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
    
    public SpecialsDaoTest() {
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
     * Test of createSpecial method, of class SpecialsDaoDB.
     */
    @Test
    public void testCreateSpecial() {
        
        //Arrange
        Specials special = new Specials();
        special.setUpdateMultiplier(new BigDecimal("23"));
        special.setTitle("New Years");
        special.setDescription("Only limited time");
        
        //Act
        Specials createdSpecial = specialsDao.createSpecial(special);
        
        //Assert
        assertEquals(createdSpecial.getUpdateMultiplier(), new BigDecimal("23"));
        assertEquals(createdSpecial.getTitle(), "New Years");
    }
    
    /**
     * Test of getSpecialById method, of class SpecialsDaoDB.
     */
    @Test
    public void testGetSpecialById() {
        
        //Arrange
        Specials special = new Specials();
        special.setUpdateMultiplier(new BigDecimal("23"));
        special.setTitle("New Years");
        special.setDescription("Only limited time");
        
        Specials createdSpecial = specialsDao.createSpecial(special);
        
        //Act
        Specials fetchedSpecial = specialsDao.getSpecialById(createdSpecial.getSpecialId());
        
        //Assert
        assertEquals(createdSpecial.getTitle(), fetchedSpecial.getTitle());
        assertEquals(createdSpecial.getUpdateMultiplier(), fetchedSpecial.getUpdateMultiplier());
    }

    /**
     * Test of getAllSpecials method, of class SpecialsDaoDB.
     */
    @Test
    public void testGetAllSpecials() {
        
        //Arrange
        Specials special = new Specials();
        special.setUpdateMultiplier(new BigDecimal("23"));
        special.setTitle("New Years");
        special.setDescription("Only limited time");
        
        Specials createdSpecial = specialsDao.createSpecial(special);
        
        Specials special2 = new Specials();
        special2.setUpdateMultiplier(new BigDecimal("23"));
        special2.setTitle("New Years");
        special2.setDescription("Only limited time");
        
        Specials createdSpecialTwo = specialsDao.createSpecial(special2);
        
        //Act
        List<Specials> list = specialsDao.getAllSpecials();
        
        //Assert
        assertEquals(list.size(), 2);
        
        assertEquals(list.get(0).getTitle(), createdSpecial.getTitle());
        assertEquals(list.get(1).getTitle(), createdSpecialTwo.getTitle());
    }

    /**
     * Test of updateSpecial method, of class SpecialsDaoDB.
     */
    @Test
    public void testUpdateSpecial() {
        
        //Arrange
        Specials special = new Specials();
        special.setUpdateMultiplier(new BigDecimal("23"));
        special.setTitle("New Years");
        special.setDescription("Only limited time");
        
        Specials createdSpecial = specialsDao.createSpecial(special);
        
        createdSpecial.setUpdateMultiplier(new BigDecimal("25"));
        createdSpecial.setTitle("Halloween");
        
        //Act
        specialsDao.updateSpecial(createdSpecial);
        Specials fetchedSpecial = specialsDao.getSpecialById(createdSpecial.getSpecialId());
        
        //Assert
        assertEquals(fetchedSpecial.getUpdateMultiplier(), new BigDecimal("25"));
        assertEquals(fetchedSpecial.getTitle(), "Halloween");
    }

    /**
     * Test of deleteSpecial method, of class SpecialsDaoDB.
     */
    @Test
    public void testDeleteSpecial() {
        
        //Arrange
        Specials special = new Specials();
        special.setUpdateMultiplier(new BigDecimal("23"));
        special.setTitle("New Years");
        special.setDescription("Only limited time");
        
        Specials createdSpecial = specialsDao.createSpecial(special);
        
        //Act
        specialsDao.deleteSpecial(createdSpecial.getSpecialId());
        
        //Assert
        Specials fetchedSpecial = specialsDao.getSpecialById(createdSpecial.getSpecialId());
        
        assertNull(fetchedSpecial);
    }
    
}
