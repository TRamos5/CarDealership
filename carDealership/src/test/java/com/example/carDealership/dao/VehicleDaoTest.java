/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.dao;

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
public class VehicleDaoTest {
    
    @Autowired
    VehicleDao vehicleDao;
    
    @Autowired
    MakeDao makeDao;
    
    @Autowired
    UserDao userDao;
    
    @Autowired
    ModelDao modelDao;
    
    User user = new User();
    User userTwo = new User();
    
    Make make = new Make();
    Model model = new Model();
    
    public VehicleDaoTest() {
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
     * Test of createVehicle method, of class VehicleDaoDB.
     */
    @Test
    public void testCreateVehicle() {
        
        //Arrange
        Vehicle vehicle = new Vehicle();
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
        
        //Act
        Vehicle createdVehicle = vehicleDao.createVehicle(vehicle);
        
        //Assert
        assertEquals(createdVehicle.getColor(), "Grey");
        assertEquals(createdVehicle.getYear(), 2008);
        assertEquals(createdVehicle.getSold(), false);
    }
    
    /**
     * Test of getVehicleById method, of class VehicleDaoDB.
     */
    @Test
    public void testGetVehicleById() {
        
        //Arrange
        Vehicle vehicle = new Vehicle();
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
        
        //Act
        Vehicle createdVehicle = vehicleDao.createVehicle(vehicle);
        Vehicle fetchedVehicle = vehicleDao.getVehicleById(vehicle.getVehicleId());
        
        //Assert
        assertEquals(createdVehicle.getColor(), fetchedVehicle.getColor());
        assertEquals(createdVehicle.getFeatured(), fetchedVehicle.getFeatured());
        assertEquals(createdVehicle.getYear(), fetchedVehicle.getYear());
    }

    /**
     * Test of getAllVehicles method, of class VehicleDaoDB.
     */
    @Test
    public void testGetAllVehicles() {
        
        //Arrange
        Vehicle vehicle = new Vehicle();
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
        
        Vehicle vehicleOne = vehicleDao.createVehicle(vehicle);
        
        Vehicle vehicleTwo = new Vehicle();
        vehicleTwo.setYear(2009);
        vehicleTwo.setMileage(25000);
        vehicleTwo.setIsNew(false);
        vehicleTwo.setSalePrice(new BigDecimal("13000"));
        vehicleTwo.setStyle("SUV");
        vehicleTwo.setInterior("Cloth");
        vehicleTwo.setTrans("Automatic");
        vehicleTwo.setMsrp(new BigDecimal("15000"));
        vehicleTwo.setColor("Blue");
        vehicleTwo.setVin("qwertyuiopasdfghj");
        vehicleTwo.setDescription("Description");
        vehicleTwo.setFeatured(true);
        vehicleTwo.setSold(false);
        vehicleTwo.setFileImg("1089-jpg");
        vehicleTwo.setMake(make);
        vehicleTwo.setModel(model);
        vehicleTwo.setUser(user);
        
        Vehicle vehicle2 = vehicleDao.createVehicle(vehicleTwo);
        
        //Act
        List<Vehicle> list = vehicleDao.getAllVehicles();
        
        //Assert
        assertEquals(list.size(), 2);
        assertEquals(list.get(0).getVehicleId(), vehicleOne.getVehicleId());
        assertEquals(list.get(1).getVehicleId(), vehicle2.getVehicleId());
    }

    /**
     * Test of updateVehicle method, of class VehicleDaoDB.
     */
    @Test
    public void testUpdateVehicle() {
        
        //Arrange
        Vehicle vehicle = new Vehicle();
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
        
        Vehicle vehicleOne = vehicleDao.createVehicle(vehicle);
        
        vehicleOne.setYear(2009);
        vehicleOne.setStyle("truck");
        vehicleOne.setSold(true);
        
        //Act
        vehicleDao.updateVehicle(vehicleOne);
        
        //Assert
        Vehicle fetchedVehicle = vehicleDao.getVehicleById(vehicleOne.getVehicleId());
        
        assertEquals(fetchedVehicle.getYear(), 2009);
        assertEquals(fetchedVehicle.getStyle(), "truck");
        assertEquals(fetchedVehicle.getSold(), true);
    }

    @Test
    public void testSearch() {
        
        //Arrange
        Vehicle vehicle = new Vehicle();
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
        
        Vehicle vehicleOne = vehicleDao.createVehicle(vehicle);
        
        Vehicle vehicleTwo = new Vehicle();
        vehicleTwo.setYear(2009);
        vehicleTwo.setMileage(25000);
        vehicleTwo.setIsNew(false);
        vehicleTwo.setSalePrice(new BigDecimal("13000"));
        vehicleTwo.setStyle("SUV");
        vehicleTwo.setInterior("Cloth");
        vehicleTwo.setTrans("Automatic");
        vehicleTwo.setMsrp(new BigDecimal("15000"));
        vehicleTwo.setColor("Blue");
        vehicleTwo.setVin("qwertyuiopasdfghj");
        vehicleTwo.setDescription("Description");
        vehicleTwo.setFeatured(true);
        vehicleTwo.setSold(false);
        vehicleTwo.setFileImg("1089-jpg");
        vehicleTwo.setMake(make);
        vehicleTwo.setModel(model);
        vehicleTwo.setUser(user);
        
        Vehicle vehicle2 = vehicleDao.createVehicle(vehicleTwo);
        
        //Act
        List<Vehicle> vehicles = vehicleDao.getAllVehicles();
        List<Vehicle> searchedVehicles = vehicleDao.search("2008", false);
        
        Vehicle foundVehicle = searchedVehicles.get(0);
        
        //Assert
        assertEquals(vehicles.size() - 1, searchedVehicles.size());
        assertEquals(searchedVehicles.size(), 1);
        
        assertEquals(vehicle.getColor(), foundVehicle.getColor());
    }
    
}
