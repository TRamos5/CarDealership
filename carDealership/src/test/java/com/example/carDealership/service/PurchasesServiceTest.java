/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.service;

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
public class PurchasesServiceTest {
    
    @Autowired
    PurchasesService purchasesService;
    
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
    Vehicle vehicleTwo = new Vehicle();
    
    public PurchasesServiceTest() {
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
        
        vehicleTwo.setYear(2009);
        vehicleTwo.setMileage(25000);
        vehicleTwo.setIsNew(false);
        vehicleTwo.setSalePrice(new BigDecimal("11000"));
        vehicleTwo.setStyle("SUV");
        vehicleTwo.setInterior("black");
        vehicleTwo.setTrans("Automatic");
        vehicleTwo.setMsrp(new BigDecimal("15000"));
        vehicleTwo.setColor("blue");
        vehicleTwo.setVin("qwertyuiopasdfghj");
        vehicleTwo.setDescription("Description");
        vehicleTwo.setFeatured(true);
        vehicleTwo.setSold(false);
        vehicleTwo.setFileImg("1089.jpg");
        vehicleTwo.setMake(make);
        vehicleTwo.setModel(model);
        vehicleTwo.setUser(user);
        vehicleTwo = vehicleService.createVehicle(vehicleTwo);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of creatPurchase method, of class PurchasesService.
     */
    @Test
    public void testCreatPurchase() throws Exception {
        
        //Arrange
        LocalDate ld = LocalDate.parse("10-10-2020", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        
        Purchases purchase = new Purchases();
        purchase.setPurchaseCost(new BigDecimal("15000"));
        purchase.setPurchaseType("bank finance");
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
        Purchases createdPurchase = purchasesService.createPurchase(purchase);
        
        //Assert
        assertEquals(createdPurchase.getPurchaseCost(), new BigDecimal("15000"));
        assertEquals(createdPurchase.getPurchaseType(), "bank finance");
    }
    
    /**
     * Test of getPurchaseById method, of class PurchasesService.
     */
    @Test
    public void testGetPurchaseById() throws Exception {
        
        //Arrange
        LocalDate ld = LocalDate.parse("10-10-2020", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        
        Purchases purchase = new Purchases();
        purchase.setPurchaseCost(new BigDecimal("15000"));
        purchase.setPurchaseType("bank finance");
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
        
        Purchases createdPurchase = purchasesService.createPurchase(purchase);
        
        //Act
        Purchases fetchedPurchase = purchasesService.getPurchaseById(createdPurchase.getPurchaseId());
        
        //Assert
        assertEquals(createdPurchase.getPurchaseCost(), fetchedPurchase.getPurchaseCost());
        assertEquals(createdPurchase.getPurchaseType(), fetchedPurchase.getPurchaseType());
        assertEquals(createdPurchase.getEmail(), fetchedPurchase.getEmail());
    }

    /**
     * Test of getAllPurchases method, of class PurchasesService.
     */
    @Test
    public void testGetAllPurchases() throws InvalidEntryException, DoesNotExistException {
        
        //Arrange
        LocalDate ld = LocalDate.parse("10-10-2020", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        
        Purchases purchase = new Purchases();
        purchase.setPurchaseCost(new BigDecimal("15000"));
        purchase.setPurchaseType("bank finance");
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
        
        Purchases createdPurchase = purchasesService.createPurchase(purchase);
        
        Purchases purchaseTwo = new Purchases();
        purchaseTwo.setPurchaseCost(new BigDecimal("15000"));
        purchaseTwo.setPurchaseType("bank finance");
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
        
        Purchases createdPurchaseTwo = purchasesService.createPurchase(purchaseTwo);
        
        //Act
        List<Purchases> list = purchasesService.getAllPurchases();
        
        //Assert
        assertEquals(list.size(), 2);
        
        assertEquals(list.get(0).getFirstName(), createdPurchase.getFirstName());
        assertEquals(list.get(1).getLastName(), createdPurchase.getLastName());
    }

    /**
     * Test of updatePurchase method, of class PurchasesService.
     */
    @Test
    public void testUpdatePurchase() throws Exception {
        
        //Arrange
        LocalDate ld = LocalDate.parse("10-10-2020", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        
        Purchases purchase = new Purchases();
        purchase.setPurchaseCost(new BigDecimal("15000"));
        purchase.setPurchaseType("bank finance");
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
        
        Purchases createdPurchase = purchasesService.createPurchase(purchase);
        
        createdPurchase.setPurchaseCost(new BigDecimal("14500"));
        createdPurchase.setFirstName("Joe");
        
        //Act
        purchasesService.updatePurchase(createdPurchase);
        Purchases fetchedPurchase = purchasesService.getPurchaseById(createdPurchase.getPurchaseId());
        
        //Assert
        assertEquals(fetchedPurchase.getPurchaseCost(), new BigDecimal("14500"));
        assertEquals(fetchedPurchase.getFirstName(), "Joe");
    }

    /**
     * Test of getUserSells method, of class PurchasesService.
     */
    @Test
    public void testGetUserSells() throws Exception {
        
        //Arrange
        LocalDate ld = LocalDate.parse("10-10-2020", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        LocalDate ld2 = LocalDate.parse("10-11-2020", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        
        Purchases purchase = new Purchases();
        purchase.setPurchaseCost(new BigDecimal("15000"));
        purchase.setPurchaseType("bank finance");
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
        
        purchase = purchasesService.createPurchase(purchase);
        
        Purchases purchaseTwo = new Purchases();
        purchaseTwo.setPurchaseCost(new BigDecimal("15000"));
        purchaseTwo.setPurchaseType("bank finance");
        purchaseTwo.setFirstName("Travis");
        purchaseTwo.setLastName("Ramos");
        purchaseTwo.setStreet("3515 Cypress");
        purchaseTwo.setStreet2("4110 Fairmount");
        purchaseTwo.setCity("Dallas");
        purchaseTwo.setState("Texas");
        purchaseTwo.setZip("75219");
        purchaseTwo.setPhone("7135558282");
        purchaseTwo.setEmail("test@test.com");
        purchaseTwo.setPurchaseDate(ld2);
        purchaseTwo.setVehicle(vehicleTwo);
        purchaseTwo.setUser(user);
        
        purchaseTwo = purchasesService.createPurchase(purchaseTwo);
        int userId = user.getUserId();
        //Act
        BigDecimal sales = purchasesService.getUserSells(userId, null, null);
        
        //Assert
        assertEquals(new BigDecimal("30000.00"), sales);
    }
    
    @Test
    public void testZip() throws DoesNotExistException {
        
        //Arrange
        LocalDate ld = LocalDate.parse("10-10-2020", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        
        Purchases purchase = new Purchases();
        purchase.setPurchaseCost(new BigDecimal("15000"));
        purchase.setPurchaseType("bank finance");
        purchase.setFirstName("Travis");
        purchase.setLastName("Ramos");
        purchase.setStreet("3515 Cypress");
        purchase.setStreet2("4110 Fairmount");
        purchase.setCity("Dallas");
        purchase.setState("Texas");
        purchase.setZip("7521");
        purchase.setPhone("7135558282");
        purchase.setEmail("test@test.com");
        purchase.setPurchaseDate(ld);
        purchase.setVehicle(vehicle);
        purchase.setUser(user);
        
        try {
            purchasesService.createPurchase(purchase);
            fail("Should have thrown exception");
        } catch(InvalidEntryException e) {
            
        }

    }
    
    @Test
    public void testPurchaseType() throws DoesNotExistException {
        
        //Arrange
        LocalDate ld = LocalDate.parse("10-10-2020", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        
        Purchases purchase = new Purchases();
        purchase.setPurchaseCost(new BigDecimal("15000"));
        purchase.setPurchaseType("test");
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
        
        try {
            purchasesService.createPurchase(purchase);
            fail("Should have thrown exception");
        } catch(InvalidEntryException e) {
            
        }

    }
    
}
