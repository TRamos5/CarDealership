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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author travisramos
 */
@Repository
public class VehicleDaoDB implements VehicleDao {

    @Autowired
    JdbcTemplate jdbc;

    public VehicleDaoDB(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Vehicle getVehicleById(int vehicleId) {

        String query = "SELECT * FROM Vehicle WHERE vehicleId = ?";
        try {
            return jdbc.queryForObject(query, new VehicleMapper(), vehicleId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Vehicle> getAllVehicles() {

        String query = "SELECT * FROM Vehicle";
        return jdbc.query(query, new VehicleMapper());
    }

    @Override
    @Transactional
    public Vehicle createVehicle(Vehicle vehicle) {

        String query = "INSERT INTO Vehicle ("
                + "year, "
                + "mileage, "
                + "isNew, "
                + "salePrice, "
                + "style, "
                + "interior, "
                + "trans, "
                + "msrp, "
                + "color, "
                + "vin, "
                + "description, "
                + "featured, "
                + "sold, "
                + "fileImg, "
                + "makeId, "
                + "modelId, "
                + "userId) "
                + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        jdbc.update(query,
                vehicle.getYear(),
                vehicle.getMileage(),
                vehicle.getIsNew(),
                vehicle.getSalePrice(),
                vehicle.getStyle(),
                vehicle.getInterior(),
                vehicle.getTrans(),
                vehicle.getMsrp(),
                vehicle.getColor(),
                vehicle.getVin(),
                vehicle.getDescription(),
                vehicle.getFeatured(),
                vehicle.getSold(),
                vehicle.getFileImg(),
                vehicle.getMake().getMakeId(),
                vehicle.getModel().getModelId(),
                vehicle.getUser().getUserId());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        vehicle.setVehicleId(newId);

        return vehicle;
    }

    @Override
    public void updateVehicle(Vehicle vehicle) {
        
        String query = "UPDATE Vehicle SET "
                + "year = ?,"
                + "mileage = ?,"
                + "isNew = ?,"
                + "salePrice = ?,"
                + "style = ?,"
                + "interior = ?,"
                + "trans = ?,"
                + "msrp = ?,"
                + "color = ?,"
                + "vin = ?,"
                + "description = ?,"
                + "featured = ?,"
                + "sold = ?,"
                + "fileImg = ?,"
                + "makeId = ?,"
                + "modelId = ?,"
                + "userId = ? "
                + "WHERE vehicleId = ?";
        
        jdbc.update(query,
                vehicle.getYear(),
                vehicle.getMileage(),
                vehicle.getIsNew(),
                vehicle.getSalePrice(),
                vehicle.getStyle(),
                vehicle.getInterior(),
                vehicle.getTrans(),
                vehicle.getMsrp(),
                vehicle.getColor(),
                vehicle.getVin(),
                vehicle.getDescription(),
                vehicle.getFeatured(),
                vehicle.getSold(),
                vehicle.getFileImg(),
                vehicle.getMake().getMakeId(),
                vehicle.getModel().getModelId(),
                vehicle.getUser().getUserId(),
                vehicle.getVehicleId());
    }

    @Override
    @Transactional
    public List<Vehicle> search(String search, Boolean isNew) {
        
         if (isNew != null) {
            try {
                int year = Integer.parseInt(search);
                if (year >= 2000) {
                    return jdbc.query("SELECT * FROM Vehicle "
                            + "WHERE isNew = ? AND year = ?",
                            new VehicleMapper(), isNew, search);
                }
            } catch (NumberFormatException ex) {
            }
            return jdbc.query("SELECT * FROM Vehicle "
                    + "JOIN Model ON Vehicle.modelId = Model.modelId "
                    + "JOIN Make ON Vehicle.makeId = Make.makeId "
                    + "WHERE (isNew = ?) AND (Model.modelName LIKE ? OR Make.makeName LIKE ?)",
                    new VehicleMapper(), isNew, search + "%", search + "%");
        } else {
            try {
                int year = Integer.parseInt(search);
                if (year >= 2000) {
                    return jdbc.query("SELECT * FROM Vehicle "
                            + "WHERE year = ?",
                            new VehicleMapper(), search);
                }
            } catch (NumberFormatException ex) {
            }
            return jdbc.query("SELECT * FROM Vehicle "
                    + "JOIN Model ON Vehicle.modelId = Model.modelId "
                    + "JOIN Make ON Vehicle.makeId = Make.makeId "
                    + "WHERE Model.modelName LIKE ? OR Make.makeName LIKE ?",
                    new VehicleMapper(), search + "%", search + "%");

        }
    }
    
    @Override
    public List<Vehicle> findFeatured() {
        String query = "SELECT * FROM Vehicle WHERE featured = true LIMIT 8";
        
        return jdbc.query(query, new VehicleMapper());
    }

    private static class VehicleMapper implements RowMapper<Vehicle> {

        @Override
        public Vehicle mapRow(ResultSet rs, int i) throws SQLException {

            Vehicle vehicle = new Vehicle();
            vehicle.setVehicleId(rs.getInt("vehicleId"));
            vehicle.setYear(rs.getInt("year"));
            vehicle.setMileage(rs.getInt("mileage"));
            vehicle.setIsNew(rs.getBoolean("isNew"));
            vehicle.setSalePrice(rs.getBigDecimal("salePrice"));
            vehicle.setStyle(rs.getString("style"));
            vehicle.setInterior(rs.getString("interior"));
            vehicle.setTrans(rs.getString("trans"));
            vehicle.setMsrp(rs.getBigDecimal("msrp"));
            vehicle.setColor(rs.getString("color"));
            vehicle.setVin(rs.getString("vin"));
            vehicle.setDescription(rs.getString("description"));
            vehicle.setFeatured(rs.getBoolean("featured"));
            vehicle.setSold(rs.getBoolean("sold"));
            vehicle.setFileImg(rs.getString("fileImg"));

            Make make = new Make();
            make.setMakeId(rs.getInt("makeId"));

            Model model = new Model();
            model.setModelId(rs.getInt("modelId"));

            User user = new User();
            user.setUserId(rs.getInt("userId"));

            vehicle.setMake(make);
            vehicle.setModel(model);
            vehicle.setUser(user);

            return vehicle;
        }

    }

}
