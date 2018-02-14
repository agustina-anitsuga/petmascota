package com.petmascota.robot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RobotProperties
 * @author agustinadagnino
 *
 */
public class Properties {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Properties.class.getName());

    /**
     * config
     */
    private static java.util.Properties config = new java.util.Properties();
    
    
    /**
     * Constructor
     * @param country
     */
    public Properties() {
        try {
            config.load(this.getClass().getClassLoader().getResourceAsStream("robot.properties"));
        } catch (Exception e) {
            LOGGER.error("Could not read properties file.", e);
        }
    }
    
    /**
     * getProperty
     * @param property
     * @return
     */
    public String getProperty(String property){
        return config.getProperty(property);
    }

}
