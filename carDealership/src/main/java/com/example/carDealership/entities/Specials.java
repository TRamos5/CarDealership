/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.carDealership.entities;

import java.math.BigDecimal;

/**
 *
 * @author travisramos
 */
public class Specials {
    
    private int specialId;
    private BigDecimal updateMultiplier;
    private String title;
    private String description;

    public int getSpecialId() {
        return specialId;
    }

    public void setSpecialId(int specialId) {
        this.specialId = specialId;
    }

    public BigDecimal getUpdateMultiplier() {
        return updateMultiplier;
    }

    public void setUpdateMultiplier(BigDecimal updateMultiplier) {
        this.updateMultiplier = updateMultiplier;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
