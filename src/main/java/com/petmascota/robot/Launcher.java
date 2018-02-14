package com.petmascota.robot;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.petmascota.robot.model.Product;


/**
 * Robot
 * @author agustinadagnino
 *
 */
public class Launcher {

    
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class.getName());
    
    
    /**
     * main
     * @param args
     */
    public static void main(String[] args) {
    
        Launcher self = new Launcher();
        self.run(RobotType.ANIMALANDIA);
    }

    /**
     * run
     */
    private void run(RobotType type) {
       LOGGER.info("Starting robot "+type);
       
       // scrape products
       Robot robot = type.getInstance();
       List<Product> products = robot.scrape();
       
       // write them to file
       ProductCSVWriter writer = new ProductCSVWriter();
       writer.write( type.getFilename(), products );
       
       LOGGER.info("Finished robot "+type);
    }
    
}
