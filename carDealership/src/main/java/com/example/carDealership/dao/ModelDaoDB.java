/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.dao;

import com.example.carDealership.entities.Make;
import com.example.carDealership.entities.Model;
import com.example.carDealership.entities.User;
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
public class ModelDaoDB implements ModelDao {
    
    @Autowired
    JdbcTemplate jdbc;
    
    public ModelDaoDB(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Model getModelById(int modelId) {
        
        try {
            return jdbc.queryForObject("SELECT * FROM Model WHERE modelId = ?", new ModelMapper(), modelId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Model> getAllModels() {
        
        String query = "SELECT * FROM Model";
        return jdbc.query(query, new ModelMapper());
    }

    @Override
    @Transactional
    public Model createModel(Model model) {
        
        jdbc.update("INSERT INTO Model(modelName, dateAdded, userId, makeId) VALUES (?, ?, ?, ?)",
                model.getModelName(),
                model.getDateAdded(),
                model.getUser().getUserId(),
                model.getMake().getMakeId()
        );
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        model.setModelId(newId);
        
        return model;
    }

    @Override
    public void updateModel(Model model) {
        
        String query = "UPDATE Model SET "
                + "modelName = ?, "
                + "dateAdded = ?, "
                + "userId = ?, "
                + "makeId = ?  WHERE modelId = ?";
        jdbc.update(query,
                model.getModelName(),
                model.getDateAdded(),
                model.getUser().getUserId(),
                model.getMake().getMakeId(),
                model.getModelId()
        );
    }
    
    private static class ModelMapper implements RowMapper<Model> {

        @Override
        public Model mapRow(ResultSet rs, int i) throws SQLException {
            User user = new User();
            Make make = new Make();
            Model model = new Model();
            
            model.setModelId(rs.getInt("modelId"));
            model.setModelName(rs.getString("modelName"));
            model.setDateAdded(rs.getDate("dateAdded").toLocalDate());
            
            user.setUserId(rs.getInt("userId"));
            make.setMakeId(rs.getInt("makeId"));
            
            model.setUser(user);
            model.setMake(make);
            
            return model;
        }
        
    }
    
}
