/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.dao;

import com.example.carDealership.entities.Specials;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class SpecialsDaoDB implements SpecialsDao {

    @Autowired
    JdbcTemplate jdbc;

    public SpecialsDaoDB(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Specials getSpecialById(int specialId) {

        try {
            return jdbc.queryForObject("SELECT * FROM Specials WHERE specialId = ?", new SpecialMapper(), specialId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Specials> getAllSpecials() {
        return jdbc.query("SELECT * FROM Specials", new SpecialMapper());
    }

    @Override
    public Specials createSpecial(Specials special) {
        
        jdbc.update("INSERT INTO Specials(updateMultiplier, title, description) VALUES(?,?,?)",
                special.getUpdateMultiplier(),
                special.getTitle(),
                special.getDescription()
        );
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        special.setSpecialId(newId);

        return special;
    }

    @Override
    public void updateSpecial(Specials special) {
        
        jdbc.update("UPDATE Specials SET updateMultiplier = ?, title = ?, description = ? WHERE specialId = ?",
                special.getUpdateMultiplier(), 
                special.getTitle(), 
                special.getDescription(),  
                special.getSpecialId());
    }

    @Override
    public void deleteSpecial(int specialId) {
        jdbc.update("DELETE FROM Specials WHERE specialId = ?", specialId);
    }

    private static final class SpecialMapper implements RowMapper<Specials> {

        @Override
        public Specials mapRow(ResultSet rs, int i) throws SQLException {

            Specials special = new Specials();
            special.setSpecialId(rs.getInt("specialId"));
            special.setUpdateMultiplier(rs.getBigDecimal("updateMultiplier"));
            special.setTitle(rs.getString("title"));
            special.setDescription(rs.getString("description"));

            return special;
        }

    }

}
