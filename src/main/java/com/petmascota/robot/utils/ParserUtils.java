package com.petmascota.robot.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ParserUtils
 * @author agustinadagnino
 *
 */
public class ParserUtils {

    
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ParserUtils.class.getName());
    
    /**
     * Hide default constructor
     */
    private ParserUtils() {
       // this class should not be instantiated
    }

    /**
     * parse
     * @param numberStr
     * @return number
     * @throws ParseException
     */
    public static Number parse( String numberStr ) {
        try {
            DecimalFormat df = new DecimalFormat();
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator(',');
            symbols.setGroupingSeparator('.');
            df.setDecimalFormatSymbols(symbols);
            return df.parse(numberStr);
        } catch (ParseException pe) {
            LOGGER.error("Could not parse string "+numberStr,pe);
            return null;
        }
    }
}
