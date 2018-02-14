package com.petmascota.robot.exception;

/**
 * FrontEndException
 * @author agustinadagnino
 *
 */
public class FrontEndException extends RuntimeException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;


    /**
     * Constructor
     */
    public FrontEndException() {
        super();
    }
    
    /**
     * Constructor
     */
    public FrontEndException(String message) {
        super(message);
    }
    
    /**
     * Constructor
     */
    public FrontEndException(String message,Throwable t) {
        super(message,t);
    }
}
