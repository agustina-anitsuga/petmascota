package com.petmascota.robot;

/**
 * RobotType
 * @author agustinadagnino
 *
 */
public enum RobotType {

    PUPPIS {
        @Override
        public Robot getInstance() {
            return new RobotPuppis();
        }

        @Override
        public String getFilename() {
            return "puppis.csv";
        }
    },
    
    ANIMALANDIA {
        @Override
        public Robot getInstance() {
            return new RobotAnimalandia();
        }
        
        @Override
        public String getFilename() {
            return "animalandia.csv";
        }
    }, 
    
    TIMOTEO {
        @Override
        public Robot getInstance() {
            return new RobotTimoteo();
        }
        
        @Override
        public String getFilename() {
            return "timoteo.csv";
        }
    };
    
    
    public abstract Robot getInstance();
    public abstract String getFilename();
}
