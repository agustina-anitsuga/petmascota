package com.petmascota.robot;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.petmascota.robot.model.Product;
import com.petmascota.robot.page.PuppisListingPage;
import com.petmascota.robot.utils.Browser;
import com.petmascota.robot.utils.SeleniumUtils;

/**
 * RobotPuppis
 * @author agustinadagnino
 *
 */
public class RobotPuppis implements Robot {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RobotPuppis.class.getName());
    
    /**
     * scrape
     */
    public List<Product> scrape() {
        
        List<Product> ret = new ArrayList<Product>();

        // Get required properties
        Properties config = new Properties();
        String dogsURL = config.getProperty("puppis.url.dogs");
        String catsURL = config.getProperty("puppis.url.cats");
        
        // Scrape data
        List<Product> dogsItems = scrape(dogsURL);
        List<Product> catsItems = scrape(catsURL);
        
        // build output
        ret.addAll(dogsItems);
        ret.addAll(catsItems);
        
        return ret;
    }

    /**
     * scrape
     * @param url
     * @return
     */
    private List<Product> scrape(String url) {
        
        WebDriver driver = null ;
        List<Product> ret = new ArrayList<Product>();
        
        try {
            // Open browser to requested URL
            driver = SeleniumUtils.buildDriver(Browser.CHROME);
            PuppisListingPage listingPage = new PuppisListingPage(driver);
            listingPage = listingPage.go(url);
            LOGGER.info("Navigated to page: " + url );
    
            do {
        
                // get products in page
                List<Product> products = listingPage.getProducts();
                ret.addAll(products);
    
                // get nextPage
                listingPage = listingPage.getNextPage();
                
            } while ( listingPage!=null );
            
            
        } catch (Exception e) {
            
            // take screenshot of error
            SeleniumUtils.captureScreenshot(driver);
            
            // log exception
            LOGGER.error("Error reading URL "+url, e);
            
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
        
        return ret;
    }

    
}
