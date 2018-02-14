package com.petmascota.robot;

import java.util.List;

import com.petmascota.robot.model.Product;

/**
 * Robot
 * @author agustinadagnino
 *
 */
public interface Robot {

    /**
     * scrape
     * @param resultFile
     */
    public List<Product> scrape();
    
}
