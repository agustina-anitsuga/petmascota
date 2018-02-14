package com.petmascota.robot;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.petmascota.robot.model.Product;

/**
 * ProductCSVWriter
 * @author agustinadagnino
 *
 */
public class ProductCSVWriter {

    /**
     * QUOTES
     */
    private static final String QUOTES = "\"";

    /**
     * DELIMITER
     */
    private static final String DELIMITER = ",";
    
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCSVWriter.class.getName());
    
    
    /**
     * write
     * @param file
     * @param products
     */
    public void write( String filename, List<Product> products ) {
        
        PrintWriter out = null ;
        try {
            LOGGER.info("Writing "+products.size()+" products");
            
            out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));

            out.println("Brand,Title,Size,RegularPriceDesc,RegularPrice,PromotionPriceDesc,PromotionPrice,URL");
            
            for (Product product : products) {
                String data = QUOTES+product.getBrand()+QUOTES+DELIMITER+
                        QUOTES+product.getTitle()+QUOTES+DELIMITER+
                        QUOTES+product.getSize()+QUOTES+DELIMITER+
                        QUOTES+product.getRegularPriceDesc()+QUOTES+DELIMITER+
                        QUOTES+product.getRegularPrice()+QUOTES+DELIMITER+
                        QUOTES+product.getPromotionPriceDesc()+QUOTES+DELIMITER+
                        QUOTES+product.getPromotionPrice()+QUOTES+DELIMITER+
                        QUOTES+product.getUrl()+QUOTES;
                out.println(data);
            }
                            
            
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }  finally {
            if(out!=null){
                out.close();
            }
        }
    }
    
}
