package com.petmascota.robot;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.petmascota.robot.model.Product;

/**
 * RobotMarketcan
 * @author agustinadagnino
 *
 */
public class RobotMarketcan implements Robot {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RobotMarketcan.class.getName());
    
    /**
     * scrape
     */
    public List<Product> scrape() {
        return null;
    }
    
    
}
