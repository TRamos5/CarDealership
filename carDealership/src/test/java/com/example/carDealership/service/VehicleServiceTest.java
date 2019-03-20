/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.service;

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
public class VehicleServiceTest {

    @Autowired
    VehicleService vehicleService;

    @Autowired
    MakeService makeService;

    @Autowired
    ModelService modelService;

    @Autowired
    UserService userService;

    User user = new User();
    User userTwo = new User();

    Make make = new Make();
    Model model = new Model();

    public VehicleServiceTest() {
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

        model.setModelName("Tesla");
        model.setDateAdded(ld);
        model.setUser(user);
        model.setMake(make);
        model = modelService.createModel(model);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of createVehicle method, of class VehicleService.
     */
    @Test
    public void testCreateVehicle() throws Exception {

        //Arrange
        Vehicle vehicle = new Vehicle();
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

        //Act
        Vehicle createdVehicle = vehicleService.createVehicle(vehicle);

        //Assert
        assertEquals(createdVehicle.getColor(), "red");
        assertEquals(createdVehicle.getYear(), 2008);
        assertEquals(createdVehicle.getSold(), false);
    }

    /**
     * Test of getVehicleById method, of class VehicleService.
     */
    @Test
    public void testGetVehicleById() throws Exception {

        //Arrange
        Vehicle vehicle = new Vehicle();
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

        //Act
        Vehicle createdVehicle = vehicleService.createVehicle(vehicle);
        Vehicle fetchedVehicle = vehicleService.getVehicleById(vehicle.getVehicleId());

        //Assert
        assertEquals(createdVehicle.getColor(), fetchedVehicle.getColor());
        assertEquals(createdVehicle.getFeatured(), fetchedVehicle.getFeatured());
        assertEquals(createdVehicle.getYear(), fetchedVehicle.getYear());
    }

    /**
     * Test of getAllVehicles method, of class VehicleService.
     */
    @Test
    public void testGetAllVehicles() throws InvalidEntryException, DoesNotExistException {

        //Arrange
        Vehicle vehicle = new Vehicle();
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

        Vehicle vehicleOne = vehicleService.createVehicle(vehicle);

        Vehicle vehicleTwo = new Vehicle();
        vehicleTwo.setYear(2009);
        vehicleTwo.setMileage(25000);
        vehicleTwo.setIsNew(false);
        vehicleTwo.setSalePrice(new BigDecimal("13000"));
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

        Vehicle vehicle2 = vehicleService.createVehicle(vehicleTwo);

        //Act
        List<Vehicle> list = vehicleService.getAllVehicles();

        //Assert
        assertEquals(list.size(), 2);
        assertEquals(list.get(0).getVehicleId(), vehicleOne.getVehicleId());
        assertEquals(list.get(1).getVehicleId(), vehicle2.getVehicleId());
    }

    /**
     * Test of updateVehicle method, of class VehicleService.
     */
    @Test
    public void testUpdateVehicle() throws Exception {

        //Arrange
        Vehicle vehicle = new Vehicle();
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

        Vehicle vehicleOne = vehicleService.createVehicle(vehicle);

        vehicleOne.setYear(2009);
        vehicleOne.setStyle("truck");
        vehicleOne.setSold(true);

        //Act
        vehicleService.updateVehicle(vehicleOne);

        //Assert
        Vehicle fetchedVehicle = vehicleService.getVehicleById(vehicleOne.getVehicleId());

        assertEquals(fetchedVehicle.getYear(), 2009);
        assertEquals(fetchedVehicle.getStyle(), "truck");
        assertEquals(fetchedVehicle.getSold(), true);
    }

    @Test
    public void testCreateValidYear() throws DoesNotExistException {

        Vehicle vehicle = new Vehicle();
        vehicle.setYear(1999);
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

        try {
            vehicleService.createVehicle(vehicle);
            fail("Definitely should ahve thrown errors");
        } catch (InvalidEntryException e) {

        }
    }
    
    @Test
    public void testCreateInvalidTrans() throws DoesNotExistException {
        
        Vehicle vehicle = new Vehicle();
        vehicle.setYear(1999);
        vehicle.setMileage(25000);
        vehicle.setIsNew(false);
        vehicle.setSalePrice(new BigDecimal("12000"));
        vehicle.setStyle("SUV");
        vehicle.setInterior("black");
        vehicle.setTrans("Blah");
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
        
        try {
            vehicleService.createVehicle(vehicle);
            fail("Shold have thrown error");
        } catch (InvalidEntryException e) {
            
        }
    }

}
