/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.dao;

import com.example.carDealership.entities.Contacts;
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
public class ContactsDaoDB implements ContactsDao {

    @Autowired
    JdbcTemplate jdbc;

    public ContactsDaoDB(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Contacts getContactById(int contactId) {

        String query = "SELECT * FROM Contacts WHERE contactId = ?";
        try {
            return jdbc.queryForObject(query, new ContactMapper(), contactId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Contacts> getAllContacts() {

        String query = "SELECT * FROM Contacts";
        return jdbc.query(query, new ContactMapper());
    }

    @Override
    @Transactional
    public Contacts createContact(Contacts contact) {

        String query = "Insert into Contacts(name, phone, message, email) Values(?,?,?,?)";

        jdbc.update(query, contact.getName(), contact.getPhone(), contact.getMessage(), contact.getEmail());

        int newid = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        contact.setContactId(newid);
        return contact;
    }

    @Override
    public void updateContact(Contacts contact) {

        String query = "UPDATE Contacts Set name = ?, phone = ?, email = ?";
        jdbc.update(query, contact.getName(), contact.getPhone(), contact.getEmail());
    }

    @Override
    public void deleteContact(int contactId) {
      
        String query = "DELETE From Contacts WHERE contactId = ?";
        jdbc.update(query, contactId);
        
    }

    public class ContactMapper implements RowMapper<Contacts> {

        @Override
        public Contacts mapRow(ResultSet rs, int index) throws SQLException {
            Contacts contact = new Contacts();
            contact.setContactId(rs.getInt("contactId"));
            contact.setName(rs.getString("name"));
            contact.setPhone(rs.getString("phone"));
            contact.setEmail(rs.getString("email"));
            contact.setMessage(rs.getString("message"));
            
            return contact;

        }
    }

}
