/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.service;

import com.example.carDealership.dao.UserDao;
import com.example.carDealership.entities.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author travisramos
 */
@Service
public class UserServiceImpl implements UserService {
    
    UserDao userDao;
    
    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getUserById(Integer userId) throws DoesNotExistException {
        
        User u = userDao.getUserById(userId);
        
        if(u == null) {
            throw new DoesNotExistException();
        }
        return u;
    }

    @Override
    public List<User> getAllUsers() throws DoesNotExistException {
        List<User> list = userDao.getAllUsers();
        
        if(list == null) {
            throw new DoesNotExistException();
        }
        return list;
    }

    @Override
    public User addUser(User user) throws InvalidEntryException {
        validateEntry(user);
        return userDao.addUser(user);
    }

    @Override
    public void updateUser(User user) throws InvalidEntryException {
        validateEntry(user);
        userDao.updateUser(user);
    }
    
    private void validateEntry(User user) throws InvalidEntryException {
        
        if(user.getFirstName() == null ||
                user.getFirstName().trim().length() == 0 ||
                user.getLastName() == null ||
                user.getLastName().trim().length() == 0 ||
                user.getEmail() == null ||
                user.getEmail().trim().length() == 0 ||
                user.getPassword() == null ||
                user.getPassword().trim().length() == 0) {
            throw new InvalidEntryException();
        }
        
//        if(user.getRole() != 0 ||
//                user.getRole() != 1 ||
//                user.getRole() != 2) {
//            throw new InvalidEntryException();
//        }
        
    }
    
}
