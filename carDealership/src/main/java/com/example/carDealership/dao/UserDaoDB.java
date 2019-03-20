/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.dao;

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
public class UserDaoDB implements UserDao {

    @Autowired
    JdbcTemplate jdbc;

    public UserDaoDB(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public User getUserById(int userId) {

        String query = "SELECT * FROM User WHERE userId = ?";
        try {
            return jdbc.queryForObject(query, new UserMapper(), userId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<User> getAllUsers() {
        
        String query = "SELECT * FROM User";
        return jdbc.query(query, new UserMapper());
    }

    @Override
    @Transactional
    public User addUser(User user) {
        
        String query = "INSERT INTO User (firstName, lastName, email, password, role) VALUES (?, ?, ?, ?, ?)";
        jdbc.update(query,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        user.setUserId(newId);

        return user;

    }

    @Override
    public void updateUser(User user) {
        
        String query = "UPDATE User SET firstName = ?, lastName = ?, email = ?, password = ?, role = ? WHERE userId = ?";
        
        jdbc.update(query,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole(),
                user.getUserId()
        );
    }

    private static class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int i) throws SQLException {

            User user = new User();
            user.setUserId(rs.getInt("userId"));
            user.setFirstName(rs.getString("firstName"));
            user.setLastName(rs.getString("lastName"));
            user.setEmail(rs.getString("email"));
            user.setRole(rs.getInt("role"));
            user.setPassword(rs.getString("password"));
            return user;
        }

    }

}
