package com.petmascota.robot;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.petmascota.robot.model.BrandLink;
import com.petmascota.robot.model.Product;
import com.petmascota.robot.page.AnimalandiaCategoryPage;
import com.petmascota.robot.page.AnimalandiaListingPage;
import com.petmascota.robot.utils.Browser;
import com.petmascota.robot.utils.SeleniumUtils;

/**
 * RobotAnimalandia
 * @author agustinadagnino
 *
 */
public class RobotAnimalandia implements Robot {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RobotAnimalandia.class.getName());
    
    /**
     * scrape
     */
    public List<Product> scrape() {

        List<Product> ret = new ArrayList<Product>();

        // Get required properties
        Properties config = new Properties();
        String dogsURL = config.getProperty("animalandia.url.dogs");
        String catsURL = config.getProperty("animalandia.url.cats");
        
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
     * @param dogsURL
     * @return
     */
    private List<Product> scrape(String url) {
        
        WebDriver driver = null ;
        List<Product> ret = new ArrayList<Product>();
        String fullUrl = url;
        
        try {
            driver = SeleniumUtils.buildDriver(Browser.CHROME);
            AnimalandiaCategoryPage categoryPage = new AnimalandiaCategoryPage(driver);
            int page = 0;
            
            // from parent url, extract brand links
            fullUrl = url+page+"/";
            categoryPage = categoryPage.go(fullUrl);
            List<BrandLink> brandLinks = categoryPage.getBrandLinks();
            
            // for each brand, extract products
            for (BrandLink brandLink : brandLinks) {
                
                // build base path for category
                String path = brandLink.getLink();
                int indexLastSlash = path.indexOf("categoria/");
                int indexLastCharacter = path.indexOf("/",indexLastSlash+11);
                path = path.substring(0, indexLastCharacter+1);
                
                // get products for selected category (brand)
                List<Product> products = getProductsByBrand(path, brandLink.getBrand(), driver);
                ret.addAll(products);
            }
            
            
        } catch (Exception e) {
            
            // take screenshot of error
            SeleniumUtils.captureScreenshot(driver);
            
            // log exception
            LOGGER.error("Error reading URL "+fullUrl, e);
            
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
        
        return ret;
    }
    
    /**
     * getProductsByBrand
     * @param path
     * @param brand
     * @param source
     * @return product list
     */
    private List<Product> getProductsByBrand( String path, String brand, WebDriver driver){
        
        AnimalandiaListingPage listingPage = null;
        List<Product> ret = new ArrayList<Product>();
        int productCount = 1;
        int page = 0;
        boolean flag = true;
        
        while(flag) 
        {   
            // open browser to requested url
            String fullUrl = path+"pagina/"+page+"/";
            listingPage = new AnimalandiaListingPage(driver).go(fullUrl);
            productCount = listingPage.getProductCount();
            LOGGER.info("Navigated to page: " +fullUrl + " ("+productCount+" products)");
            
            // if products are found
            if( productCount>0 ){
                // get products in page
                List<Product> products = listingPage.getProducts(brand);
                ret.addAll(products);
                page++;
            }
            
            if( productCount<9 ){
                flag = false;
            }
            LOGGER.info("Read products in page: " +fullUrl + " ("+flag+")");
            
        } 
        
        return ret;
    } 

}
