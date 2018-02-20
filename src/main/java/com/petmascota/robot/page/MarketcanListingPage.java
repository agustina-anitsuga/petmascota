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
 * MarketcanListingPage
 * @author agustinadagnino
 *
 */
public class MarketcanListingPage extends Page {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MarketcanListingPage.class.getName());

    /**
     * products
     */
    @FindBy( xpath = ".//*/div[contains(@class,\"product-container\")]")
    private List<WebElement> products;
    
    /**
     * nextLink
     */
    @FindBy(xpath = "//*/li[contains(@class,\"pagination_next\")]/a")
    private WebElement nextLink;
    
    
    /**
     * MarketcanListingPage
     * @param driver
     */
    public MarketcanListingPage(WebDriver driver) {
        super(driver);
    }

    /**
     * go
     * @param url
     * @return
     */
    public MarketcanListingPage go(String url) {
        // go to url
        driver.get(url);
        // return page
        return new MarketcanListingPage(this.driver);
    }

    /**
     * product count
     * @return
     */
    public int getProductCount() {
        
        try {
            WebDriverWait wait = SeleniumUtils.getWait(driver);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*/div[contains(@class,\"product-container\")]")));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        
        try{
            Thread.sleep(1000);
        } catch (Exception e) {
            
        }
        
        return products.size();
    }

    /**
     * getProducts
     * @param brand
     * @return
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
     * parseProducts
     * @param product
     * @param brand
     * @return
     */
    private List<Product> parseProducts(WebElement product, String brand) {
        List<Product> ret = new ArrayList<Product>();
        
        try
        {
            Product p = new Product();
            p.setBrand(brand);
            
            try {
                WebElement title = product.findElement(By.className("product-name"));
                p.setTitle(title.getText());
                p.setUrl(title.getAttribute("href"));
            } catch (Exception e) {
                LOGGER.debug(e.getMessage(),(Object[])e.getStackTrace());
            }
            
            WebElement regularPrice = null;
            WebElement promotionPrice = null;
            
            try {
                regularPrice = product.findElement(By.xpath(".//*/span[contains(@class,\"old-price product-price\")]"));
                promotionPrice = product.findElement(By.xpath(".//*/span[contains(@class,\"price product-price\")]"));
            } catch (Exception e) {
                LOGGER.debug(e.getMessage());
                try { 
                    regularPrice = product.findElement(By.xpath(".//*/span[contains(@class,\"price product-price\")]/span"));
                } catch (Exception e2) {
                    LOGGER.debug(e2.getMessage());
                }
            }
            
            if(regularPrice!=null)
                p.setRegularPrice(regularPrice.getText());
            if(promotionPrice!=null)
                p.setPromotionPrice(promotionPrice.getText());
            
            ret.add(p);
            
        } catch( Exception e2 ){
            
            LOGGER.error("Error parsing product");
            LOGGER.error(e2.getMessage(),(Object[])e2.getStackTrace());
        }
        
        return ret;
    }

    /**
     * nextPage
     * @return
     */
    public MarketcanListingPage nextPage() {

        try { 
            if (nextLink != null && nextLink.isEnabled()) {
                nextLink.click();
                return new MarketcanListingPage(driver);
          }            
        } catch (Exception e) {
            // nothing
        }
        
        return null;
    }

    
    
}
