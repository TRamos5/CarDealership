/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.service;

import com.example.carDealership.entities.Make;
import com.example.carDealership.entities.Model;
import com.example.carDealership.entities.Specials;
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
public class SpecialsServiceTest {
    
    @Autowired
    SpecialsService specialsService;
    
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
    Vehicle vehicle = new Vehicle();
    
    public SpecialsServiceTest() {
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
        
        vehicle.setYear(2008);
        vehicle.setMileage(25000);
        vehicle.setIsNew(false);
        vehicle.setSalePrice(new BigDecimal("12000"));
        vehicle.setStyle("SUV");
        vehicle.setInterior("black");
        vehicle.setTrans("Automatic");
        vehicle.setMsrp(new BigDecimal("15000"));
        vehicle.setColor("red");
        vehicle.setVin("qwertyuiopasdfghj");
        vehicle.setDescription("Description");
        vehicle.setFeatured(true);
        vehicle.setSold(false);
        vehicle.setFileImg("1089.jpg");
        vehicle.setMake(make);
        vehicle.setModel(model);
        vehicle.setUser(user);
        vehicle = vehicleService.createVehicle(vehicle);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createSpecial method, of class SpecialsService.
     */
    @Test
    public void testCreateSpecial() throws Exception {
        
        //Arrange
        Specials special = new Specials();
        special.setUpdateMultiplier(new BigDecimal("23"));
        special.setTitle("New Years");
        special.setDescription("Only limited time");
        
        //Act
        Specials createdSpecial = specialsService.createSpecial(special);
        
        //Assert
        assertEquals(createdSpecial.getUpdateMultiplier(), new BigDecimal("23"));
        assertEquals(createdSpecial.getTitle(), "New Years");
    }
    
    /**
     * Test of getSpecialById method, of class SpecialsService.
     */
    @Test
    public void testGetSpecialById() throws Exception {
        
        //Arrange
        Specials special = new Specials();
        special.setUpdateMultiplier(new BigDecimal("23"));
        special.setTitle("New Years");
        special.setDescription("Only limited time");
        
        Specials createdSpecial = specialsService.createSpecial(special);
        
        //Act
        Specials fetchedSpecial = specialsService.getSpecialById(createdSpecial.getSpecialId());
        
        //Assert
        assertEquals(createdSpecial.getTitle(), fetchedSpecial.getTitle());
        assertEquals(createdSpecial.getUpdateMultiplier(), fetchedSpecial.getUpdateMultiplier());
    }

    /**
     * Test of getAllSpecials method, of class SpecialsService.
     */
    @Test
    public void testGetAllSpecials() throws InvalidEntryException {
        
        //Arrange
        Specials special = new Specials();
        special.setUpdateMultiplier(new BigDecimal("23"));
        special.setTitle("New Years");
        special.setDescription("Only limited time");
        
        Specials createdSpecial = specialsService.createSpecial(special);
        
        Specials special2 = new Specials();
        special2.setUpdateMultiplier(new BigDecimal("23"));
        special2.setTitle("New Years");
        special2.setDescription("Only limited time");
        
        Specials createdSpecialTwo = specialsService.createSpecial(special2);
        
        //Act
        List<Specials> list = specialsService.getAllSpecials();
        
        //Assert
        assertEquals(list.size(), 2);
        
        assertEquals(list.get(0).getTitle(), createdSpecial.getTitle());
        assertEquals(list.get(1).getTitle(), createdSpecialTwo.getTitle());
    }

    /**
     * Test of updateSpecial method, of class SpecialsService.
     */
    @Test
    public void testUpdateSpecial() throws Exception {
        
        //Arrange
        Specials special = new Specials();
        special.setUpdateMultiplier(new BigDecimal("23"));
        special.setTitle("New Years");
        special.setDescription("Only limited time");
        
        Specials createdSpecial = specialsService.createSpecial(special);
        
        createdSpecial.setUpdateMultiplier(new BigDecimal("25"));
        createdSpecial.setTitle("Halloween");
        
        //Act
        specialsService.updateSpecial(createdSpecial);
        Specials fetchedSpecial = specialsService.getSpecialById(createdSpecial.getSpecialId());
        
        //Assert
        assertEquals(fetchedSpecial.getUpdateMultiplier(), new BigDecimal("25"));
        assertEquals(fetchedSpecial.getTitle(), "Halloween");
    }

    /**
     * Test of deleteSpecial method, of class SpecialsService.
     */
    @Test
    public void testDeleteSpecial() throws InvalidEntryException, DoesNotExistException {
        
        //Arrange
        Specials special = new Specials();
        special.setUpdateMultiplier(new BigDecimal("23"));
        special.setTitle("New Years");
        special.setDescription("Only limited time");
        
        Specials createdSpecial = specialsService.createSpecial(special);
        
        //Act
        specialsService.deleteSpecial(createdSpecial.getSpecialId());
        
        //Assert
        try {
            specialsService.getSpecialById(createdSpecial.getSpecialId());
            fail("Expected DoesNotExistException was not thrown.");
        } catch (DoesNotExistException ex) {
            
        }
    }
    
    @Test
    public void testTitle() {
        
        //Arrange
        Specials special = new Specials();
        special.setUpdateMultiplier(new BigDecimal("23"));
        special.setTitle("");
        special.setDescription("Only limited time");
        
        try {
            specialsService.createSpecial(special);
            fail("Should have thrown error");
        } catch(InvalidEntryException e) {
            
        }
    }
    
    @Test
    public void testDescription() {
        
        //Arrange
        Specials special = new Specials();
        special.setUpdateMultiplier(new BigDecimal("23"));
        special.setTitle("Title");
        special.setDescription("");
        
        try {
            specialsService.createSpecial(special);
            fail("Should have thrown error");
        } catch(InvalidEntryException e) {
            
        }
    }
    
}
