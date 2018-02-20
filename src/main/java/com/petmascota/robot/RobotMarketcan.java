package com.petmascota.robot;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.petmascota.robot.model.BrandLink;
import com.petmascota.robot.model.Product;
import com.petmascota.robot.page.MarketcanBrandPage;
import com.petmascota.robot.page.MarketcanListingPage;
import com.petmascota.robot.utils.Browser;
import com.petmascota.robot.utils.SeleniumUtils;

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
        List<Product> ret = new ArrayList<Product>();

        // Get required properties
        Properties config = new Properties();
        String dogsURL = config.getProperty("marketcan.url.dogs");
        String catsURL = config.getProperty("marketcan.url.cats");
        
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
     * @return products
     */
    private List<Product> scrape(String url) {

        WebDriver driver = null ;
        List<Product> ret = new ArrayList<Product>();
        
        try {
            driver = SeleniumUtils.buildDriver(Browser.CHROME);
            MarketcanBrandPage categoryPage = new MarketcanBrandPage(driver);
            
            LOGGER.info("Reading start category URL "+url);
            categoryPage = categoryPage.go(url);
            
            List<BrandLink> brandLinks = categoryPage.getBrandLinks();
            LOGGER.info("Read brands "+brandLinks.size());
            
            // for each brand, extract products
            for (BrandLink brandLink : brandLinks) {
                
                // build base path for category
                String path = brandLink.getLink();
                
                // get products for selected category (brand)
                LOGGER.info("Reading products from brand "+brandLink.getBrand());
                List<Product> products = getProductsByBrand(path, brandLink.getBrand(), driver);
                ret.addAll(products);
            }
            
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
        }
        
        return ret;
    }

    /**
     * getProductsByBrand
     * @param path
     * @param brand
     * @param driver
     * @return products
     */
    private List<Product> getProductsByBrand(String url, String brand, WebDriver driver) {
        List<Product> ret = new ArrayList<Product>();
        MarketcanListingPage listingPage = new MarketcanListingPage(driver).go(url);
        boolean morePagesPresent = true;
        int productCount = 0;
        
        while(morePagesPresent) 
        {   
            
            productCount = listingPage.getProductCount();
            LOGGER.info("Navigated to page: " + driver.getCurrentUrl() + " ("+productCount+" products)");
                
            // if products are found
            if( productCount>0 ){
                // get products in page
                List<Product> products = listingPage.getProducts(brand);
                ret.addAll(products);
                LOGGER.info("Read products in page: " +driver.getCurrentUrl()  + " ("+morePagesPresent+")");
                
            } else {
                
                morePagesPresent = false;
            }
                
            listingPage = listingPage.nextPage();
            if( listingPage==null ){
                morePagesPresent = false;
            }
        }
        
        return ret;
    }
    
    
}
