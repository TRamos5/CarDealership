/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.dao;

import com.example.carDealership.entities.Make;
import com.example.carDealership.entities.Model;
import com.example.carDealership.entities.Purchases;
import com.example.carDealership.entities.User;
import com.example.carDealership.entities.Vehicle;
import java.math.BigDecimal;
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
public class PurchasesDaoTest {
    
    @Autowired
    PurchasesDao purchasesDao;
    
    @Autowired
    VehicleDao vehicleDao;
    
    @Autowired
    MakeDao makeDao;
    
    @Autowired
    UserDao userDao;
    
    @Autowired
    ModelDao modelDao;
    
    User user = new User();
    Make make = new Make();
    Model model = new Model();
    Vehicle vehicle = new Vehicle();
    
    
    public PurchasesDaoTest() {
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
        
        vehicle.setYear(2008);
        vehicle.setMileage(25000);
        vehicle.setIsNew(false);
        vehicle.setSalePrice(new BigDecimal("12000"));
        vehicle.setStyle("SUV");
        vehicle.setInterior("Cloth");
        vehicle.setTrans("Automatic");
        vehicle.setMsrp(new BigDecimal("15000"));
        vehicle.setColor("Grey");
        vehicle.setVin("qwertyuiopasdfghj");
        vehicle.setDescription("Description");
        vehicle.setFeatured(true);
        vehicle.setSold(false);
        vehicle.setFileImg("1089-jpg");
        vehicle.setMake(make);
        vehicle.setModel(model);
        vehicle.setUser(user);
        vehicle = vehicleDao.createVehicle(vehicle);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createPurchase method, of class PurchasesDaoDB.
     */
    @Test
    public void testCreatePurchase() {
        
        //Arrange
        
        LocalDate ld = LocalDate.parse("10-10-2020", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        
        Purchases purchase = new Purchases();
        purchase.setPurchaseCost(new BigDecimal("25.00"));
        purchase.setPurchaseType("New");
        purchase.setFirstName("Travis");
        purchase.setLastName("Ramos");
        purchase.setStreet("3515 Cypress");
        purchase.setStreet2("4110 Fairmount");
        purchase.setCity("Dallas");
        purchase.setState("Texas");
        purchase.setZip("75219");
        purchase.setPhone("7135558282");
        purchase.setEmail("test@test.com");
        purchase.setPurchaseDate(ld);
        purchase.setVehicle(vehicle);
        purchase.setUser(user);
        
        //Act
        Purchases createdPurchase = purchasesDao.createPurchase(purchase);
        
        //Assert
        assertEquals(createdPurchase.getPurchaseCost(), new BigDecimal("25.00"));
        assertEquals(createdPurchase.getPurchaseType(), "New");
    }
    
    /**
     * Test of getPurchaseById method, of class PurchasesDaoDB.
     */
    @Test
    public void testGetPurchaseById() {
        
        //Arrange
        LocalDate ld = LocalDate.parse("10-10-2020", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        
        Purchases purchase = new Purchases();
        purchase.setPurchaseCost(new BigDecimal("25"));
        purchase.setPurchaseType("New");
        purchase.setFirstName("Travis");
        purchase.setLastName("Ramos");
        purchase.setStreet("3515 Cypress");
        purchase.setStreet2("4110 Fairmount");
        purchase.setCity("Dallas");
        purchase.setState("Texas");
        purchase.setZip("75219");
        purchase.setPhone("7135558282");
        purchase.setEmail("test@test.com");
        purchase.setPurchaseDate(ld);
        purchase.setVehicle(vehicle);
        purchase.setUser(user);
        
        Purchases createdPurchase = purchasesDao.createPurchase(purchase);
        
        //Act
        Purchases fetchedPurchase = purchasesDao.getPurchaseById(createdPurchase.getPurchaseId());
        
        //Assert
        assertEquals(createdPurchase.getPurchaseCost(), fetchedPurchase.getPurchaseCost());
        assertEquals(createdPurchase.getPurchaseType(), fetchedPurchase.getPurchaseType());
        assertEquals(createdPurchase.getEmail(), fetchedPurchase.getEmail());
        
    }

    /**
     * Test of getAllPurchases method, of class PurchasesDaoDB.
     */
    @Test
    public void testGetAllPurchases() {
        
        //Arrange
        LocalDate ld = LocalDate.parse("10-10-2020", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        
        Purchases purchase = new Purchases();
        purchase.setPurchaseCost(new BigDecimal("25.00"));
        purchase.setPurchaseType("New");
        purchase.setFirstName("Travis");
        purchase.setLastName("Ramos");
        purchase.setStreet("3515 Cypress");
        purchase.setStreet2("4110 Fairmount");
        purchase.setCity("Dallas");
        purchase.setState("Texas");
        purchase.setZip("75219");
        purchase.setPhone("7135558282");
        purchase.setEmail("test@test.com");
        purchase.setPurchaseDate(ld);
        purchase.setVehicle(vehicle);
        purchase.setUser(user);
        
        Purchases createdPurchase = purchasesDao.createPurchase(purchase);
        
        Purchases purchaseTwo = new Purchases();
        purchaseTwo.setPurchaseCost(new BigDecimal("25.00"));
        purchaseTwo.setPurchaseType("New");
        purchaseTwo.setFirstName("Travis");
        purchaseTwo.setLastName("Ramos");
        purchaseTwo.setStreet("3515 Cypress");
        purchaseTwo.setStreet2("4110 Fairmount");
        purchaseTwo.setCity("Dallas");
        purchaseTwo.setState("Texas");
        purchaseTwo.setZip("75219");
        purchaseTwo.setPhone("7135558282");
        purchaseTwo.setEmail("test@test.com");
        purchaseTwo.setPurchaseDate(ld);
        purchaseTwo.setVehicle(vehicle);
        purchaseTwo.setUser(user);
        
        Purchases createdPurchaseTwo = purchasesDao.createPurchase(purchaseTwo);
        
        //Act
        List<Purchases> list = purchasesDao.getAllPurchases();
        
        //Assert
        assertEquals(list.size(), 2);
        
        assertEquals(list.get(0).getFirstName(), createdPurchase.getFirstName());
        assertEquals(list.get(1).getLastName(), createdPurchase.getLastName());
    }

    /**
     * Test of updatePurchase method, of class PurchasesDaoDB.
     */
    @Test
    public void testUpdatePurchase() {
        
        //Arrange
        LocalDate ld = LocalDate.parse("10-10-2020", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        
        Purchases purchase = new Purchases();
        purchase.setPurchaseCost(new BigDecimal("25.00"));
        purchase.setPurchaseType("New");
        purchase.setFirstName("Travis");
        purchase.setLastName("Ramos");
        purchase.setStreet("3515 Cypress");
        purchase.setStreet2("4110 Fairmount");
        purchase.setCity("Dallas");
        purchase.setState("Texas");
        purchase.setZip("75219");
        purchase.setPhone("7135558282");
        purchase.setEmail("test@test.com");
        purchase.setPurchaseDate(ld);
        purchase.setVehicle(vehicle);
        purchase.setUser(user);
        
        Purchases createdPurchase = purchasesDao.createPurchase(purchase);
        
        createdPurchase.setPurchaseCost(new BigDecimal("30"));
        createdPurchase.setFirstName("Joe");
        
        //Act
        purchasesDao.updatePurchase(createdPurchase);
        Purchases fetchedPurchase = purchasesDao.getPurchaseById(createdPurchase.getPurchaseId());
        
        //Assert
        assertEquals(fetchedPurchase.getPurchaseCost(), new BigDecimal("30"));
        assertEquals(fetchedPurchase.getFirstName(), "Joe");
    }
    
}
