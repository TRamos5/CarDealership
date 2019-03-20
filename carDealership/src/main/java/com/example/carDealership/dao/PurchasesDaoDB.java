/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.dao;

import com.example.carDealership.entities.Purchases;
import com.example.carDealership.entities.User;
import com.example.carDealership.entities.Vehicle;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author travisramos
 */
@Repository
public class PurchasesDaoDB implements PurchasesDao {
    
    @Autowired
    JdbcTemplate jdbc;
    
    public PurchasesDaoDB(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Purchases getPurchaseById(int purchaseId) {
        
        try {
            return jdbc.queryForObject("SELECT * FROM Purchases WHERE purchaseId = ?", new PurchasesMapper(), purchaseId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Purchases> getAllPurchases() {
        
        return jdbc.query("SELECT * FROM Purchases", new PurchasesMapper());
    }

    @Override
    public Purchases createPurchase(Purchases purchases) {
        
        String query = "INSERT INTO Purchases (purchaseCost, purchaseType, firstName, lastName, street, street2, "
                + "city, state, zip, phone, email, purchaseDate, vehicleId, userId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbc.update(query,
                purchases.getPurchaseCost(),
                purchases.getPurchaseType(),
                purchases.getFirstName(),
                purchases.getLastName(),
                purchases.getStreet(),
                purchases.getStreet2(),
                purchases.getCity(),
                purchases.getState(),
                purchases.getZip(),
                purchases.getPhone(),
                purchases.getEmail(),
                purchases.getPurchaseDate(),
                purchases.getVehicle().getVehicleId(),
                purchases.getUser().getUserId()
        );

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        purchases.setPurchaseId(newId);
        return purchases;
    }

    @Override
    public void updatePurchase(Purchases purchases) {
        
        String query = "UPDATE Purchases SET "
                + "purchaseCost = ?, "
                + "purchaseType = ?, "
                + "firstName = ?, "
                + "lastName = ?, "
                + "street = ?, "
                + "street2 = ?, "
                + "city = ?, "
                + "state = ?, "
                + "zip = ?, "
                + "phone = ?, "
                + "email = ?, "
                + "purchaseDate = ?, "
                + "vehicleId = ?, "
                + "userId = ? WHERE purchaseId = ?";
        jdbc.update(query,
                purchases.getPurchaseCost(),
                purchases.getPurchaseType(), 
                purchases.getFirstName(),
                purchases.getLastName(),
                purchases.getStreet(),
                purchases.getStreet2(),
                purchases.getCity(),
                purchases.getState(),
                purchases.getZip(),
                purchases.getPhone(),
                purchases.getEmail(),
                purchases.getPurchaseDate(),
                purchases.getVehicle().getVehicleId(),
                purchases.getUser().getUserId(),
                purchases.getPurchaseId()
        );
    }
    
    private static class PurchasesMapper implements RowMapper<Purchases> {

        @Override
        public Purchases mapRow(ResultSet rs, int i) throws SQLException {
            
            Vehicle vehicle = new Vehicle();
            User user = new User();
            vehicle.setVehicleId(rs.getInt("vehicleId"));
            user.setUserId(rs.getInt("userId"));
            
            Purchases purchase = new Purchases();
            
            purchase.setPurchaseId(rs.getInt("purchaseId"));
            purchase.setPurchaseCost(rs.getBigDecimal("purchaseCost"));
            purchase.setPurchaseType(rs.getString("purchaseType"));
            purchase.setFirstName(rs.getString("firstName"));
            purchase.setLastName(rs.getString("lastName"));
            purchase.setStreet(rs.getString("street"));
            purchase.setStreet2(rs.getString("street2"));
            purchase.setCity(rs.getString("city"));
            purchase.setState(rs.getString("state"));
            purchase.setZip(rs.getString("zip"));
            purchase.setPhone(rs.getString("phone"));
            purchase.setEmail(rs.getString("email"));
            purchase.setPurchaseDate(LocalDate.now());
            purchase.setVehicle(vehicle);
            purchase.setUser(user);
            
            return purchase;
        }
        
    }
    
}
