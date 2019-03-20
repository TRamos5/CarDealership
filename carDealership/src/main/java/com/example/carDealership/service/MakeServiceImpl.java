/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.service;

import com.example.carDealership.dao.MakeDao;
import com.example.carDealership.entities.Make;
import com.example.carDealership.entities.User;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author travisramos
 */
@Service
public class MakeServiceImpl implements MakeService {

    @Autowired
    UserService userService;

    MakeDao makeDao;

    @Autowired
    public MakeServiceImpl(MakeDao makeDao) {
        this.makeDao = makeDao;
    }

    @Override
    public Make getMakeById(int makeId) throws DoesNotExistException {

        Make make = makeDao.getMakeById(makeId);

        if (make == null) {
            throw new DoesNotExistException();
        }

        make.setUser(userService.getUserById(make.getUser().getUserId()));

        return make;
    }

    @Override
    public List<Make> getAllMakes() throws DoesNotExistException {
        List<Make> list = makeDao.getAllMakes();

        for (Make makes : list) {
            User user = makes.getUser();
            makes.setUser(userService.getUserById(user.getUserId()));
        }
        return list;
    }

    @Override
    public Make createMake(Make make) throws DoesNotExistException {
        make.setDateAdded(LocalDate.now());
        User user = make.getUser();
        make.setUser(userService.getUserById(user.getUserId()));
        return makeDao.createMake(make);
    }

    @Override
    public void updateMake(Make make) {
        make.setDateAdded(LocalDate.now());
        makeDao.updateMake(make);
    }

}
