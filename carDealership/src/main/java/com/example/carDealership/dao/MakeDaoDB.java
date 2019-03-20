/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.dao;

import com.example.carDealership.entities.Make;
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
public class MakeDaoDB implements MakeDao {
    
    @Autowired
    JdbcTemplate jdbc;
    
    public MakeDaoDB(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Make getMakeById(int makeId) {
        
        String query = "SELECT * FROM Make WHERE makeId = ?";
        try {
            return jdbc.queryForObject(query, new MakeMapper(), makeId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Make> getAllMakes() {
        
        String query = "SELECT * FROM Make";
        return jdbc.query(query, new MakeMapper());
    }

    @Override
    @Transactional
    public Make createMake(Make make) {
        
        String query = "INSERT INTO Make (makeName, dateAdded, userId) VALUES (?, ?, ?)";
        jdbc.update(query,
                make.getMakeName(),
                make.getDateAdded(),
                make.getUser().getUserId()
        );

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        make.setMakeId(newId);

        return make;
    }

    @Override
    public void updateMake(Make make) {
        
        String query = "UPDATE make SET "
                + "makeName = ?, "
                + "dateAdded = ?, "
                + "userId = ? WHERE makeId = ?";
        jdbc.update(query,
                make.getMakeName(),
                make.getDateAdded(),
                make.getUser().getUserId(),
                make.getMakeId()
        );
    }
    
    private static class MakeMapper implements RowMapper<Make> {

        @Override
        public Make mapRow(ResultSet rs, int i) throws SQLException {
            
            Make make = new Make();
            make.setMakeId(rs.getInt("makeId"));
            make.setMakeName(rs.getString("makeName"));
            make.setDateAdded(rs.getDate("dateAdded").toLocalDate());
            
            User user = new User();
            user.setUserId(rs.getInt("userId"));
            
            make.setUser(user);
            
            return make;
            
        }
        
    }
    
}
