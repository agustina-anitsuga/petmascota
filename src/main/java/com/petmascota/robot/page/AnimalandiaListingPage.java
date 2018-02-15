package com.petmascota.robot.page;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.petmascota.robot.model.Product;
import com.petmascota.robot.utils.SeleniumUtils;



/**
 * AnimalandiaListingPage
 * @author agustinadagnino
 *
 */
public class AnimalandiaListingPage extends Page {

    
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AnimalandiaListingPage.class.getName());

    /**
     * products
     */
    @FindBy(xpath = ".//*/ul[contains(@class,\"thumbnails\")]/li")
    private List<WebElement> products;
        
    
    
    /**
     * AnimalandiaListingPage
     * @param driver
     */
    public AnimalandiaListingPage(WebDriver driver) {
        super(driver);
    }

    /**
     * go
     * @param url
     * @return
     */
    public AnimalandiaListingPage go(String url) {
        // go to url
        driver.get(url);

        // return page
        return new AnimalandiaListingPage(this.driver);
    }

    /**
     * getProducts
     * @return products
     */
    public List<Product> getProducts(String brand) {
        List<Product> ret = new ArrayList<Product>();
        for (int i = 0; i < products.size(); i++) {
            WebElement product = products.get(i);
            List<Product> itemizedProducts = parseProducts(product,brand);
            ret.addAll(itemizedProducts);
        }
        return ret;
    }

    /**
     * parseProduct
     * @param webElement
     * @return
     */
    private List<Product> parseProducts(WebElement product,String brand){
        List<Product> ret = new ArrayList<Product>();
        
        try
        {
            Product p = new Product();
            p.setBrand(brand);
            
            try {
            WebElement title = product.findElement(By.className("producto-descripcion-corta"));
            p.setTitle(title.getText());
            } catch (Exception e) {
                LOGGER.debug(e.getMessage(),(Object[])e.getStackTrace());
            }
            
            try {
            WebElement size = product.findElement(By.className("btn-link"));
            p.setSize(size.getText());
            p.setUrl(size.getAttribute("href"));
            } catch (Exception e) {
                LOGGER.debug(e.getMessage(),(Object[])e.getStackTrace());
            }
            
            WebElement regularPrice = null;
            WebElement promotionPrice = null;
            
            try {
                regularPrice = product.findElement(By.xpath(".//*/span[contains(@class,\"precio-tachado\")]/span"));
                promotionPrice = product.findElement(By.xpath(".//*/span[contains(@class,\"precio-final\")]/span"));
            } catch (Exception e) {
                LOGGER.debug(e.getMessage());
                try { 
                    regularPrice = product.findElement(By.xpath(".//*/span[contains(@class,\"precio-final\")]/span"));;
                } catch (Exception e2) {
                    LOGGER.debug(e2.getMessage());
                }
            }
            
            if(regularPrice!=null)
                p.setRegularPrice(regularPrice.getText());
            if(promotionPrice!=null)
                p.setPromotionPrice(promotionPrice.getText());
            
            if( !isNullContent(p) ){
                ret.add(p);
            }
            
        } catch( Exception e2 ){
            
            LOGGER.error("Error parsing product");
            LOGGER.error(e2.getMessage(),(Object[])e2.getStackTrace());
        }
        
        return ret;
    }

    /**
     * isNullContent
     * @param product
     * @return
     */
    private boolean isNullContent(Product product) {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * getProductCount
     * @return
     */
    public int getProductCount() {
        try {
            WebDriverWait wait = SeleniumUtils.getWait(driver);
            wait.until(ExpectedConditions.presenceOfElementLocated( By.xpath(".//*/ul[contains(@class,\"thumbnails\")]/li") ));
        } catch ( Exception e ){
            LOGGER.debug(e.getMessage());
        }
        return products.size();
    }
    
 
}
