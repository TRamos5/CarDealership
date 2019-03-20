/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.controller;

import com.example.carDealership.entities.Make;
import com.example.carDealership.service.DoesNotExistException;
import com.example.carDealership.service.MakeService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author travisramos
 */
@RestController
@RequestMapping("/make/admin")
public class MakeRESTController {

    MakeService makeService;

    public MakeRESTController(MakeService makeService) {
        this.makeService = makeService;
    }

    @GetMapping("/getmake/{makeId}")
    public ResponseEntity<Make> getMakeById(@PathVariable int makeId) throws DoesNotExistException {
        Make make = makeService.getMakeById(makeId);

        if (make == null) {
            return new ResponseEntity(new Error(), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(make);
    }

    @GetMapping("/allmakes")
    public ResponseEntity<List<Make>> getallMakes() throws DoesNotExistException {

        List<Make> makes;

        makes = makeService.getAllMakes();

        return ResponseEntity.ok(makes);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/createmake")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Make> createMake(@RequestBody Make make) throws DoesNotExistException {

        make = makeService.createMake(make);

        return ResponseEntity.ok(make);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/updatemake")
    public ResponseEntity<String> updateMake(@RequestBody Make make) {

        makeService.updateMake(make);

        return ResponseEntity.ok("Update Success!");
    }

}
