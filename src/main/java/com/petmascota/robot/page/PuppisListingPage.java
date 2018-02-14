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
 * PuppisListingPage
 * @author agustinadagnino
 *
 */
public class PuppisListingPage extends Page {

    /**
     * subscription popup
     */
    private static final String[] SUBSCRIPTION_POPUP = { "mc-closeModal", "mc-closeModal" };
    
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PuppisListingPage.class.getName());
    
    /**
     * Notification Modal
     */
    @FindBy( id = "onesignal-popover-cancel-button" )
    private WebElement modalCloseButton ;
    
    /**
     * products
     */
    @FindBy(className = "product")
    private List<WebElement> products;
    
    /**
     * nextLink
     */
    @FindBy(xpath = "//*/div[contains(@class,\"pager bottom\")]/ul/li[contains(@class,\"next\")]")
    private WebElement nextLink;
    
    /**
     * currentUrl
     */
    private String currentUrl = null;
    
    
    /**
     * ListingPagePuppis
     * @param driver
     */
    public PuppisListingPage(WebDriver driver) {
        super(driver);
        this.currentUrl = driver.getCurrentUrl();
    }

    /**
     * go
     * @param url
     * @return
     */
    public PuppisListingPage go(String url) {
        // go to url
        driver.get(url);
        
        // close notification modal if present
        try {
            // wait for popup to appear
            WebDriverWait wait = SeleniumUtils.getWait(driver);
            wait.until(ExpectedConditions.elementToBeClickable(modalCloseButton));
            // close popup
            if (modalCloseButton != null && modalCloseButton.isDisplayed()) {
                modalCloseButton.click();
            }
        } catch (Exception e) {
            // do nothing. popup may never appear
            LOGGER.info("Notification modal did not appear", e);
        }
        
        // close popup if present
        closePopups();

        // return page
        return new PuppisListingPage(this.driver);
    }

    /**
     * closePopups
     */
    private void closePopups() {
        // close other subscription modals if present
        for (int i = 0; i < SUBSCRIPTION_POPUP.length; i++) {
            try {
                // wait for popup to appear
                WebDriverWait wait = SeleniumUtils.getWait(driver);
                wait.until(ExpectedConditions.presenceOfElementLocated(By.className(SUBSCRIPTION_POPUP[i])));
                // close popup
                WebElement popupCloseButton = driver.findElement(By.className(SUBSCRIPTION_POPUP[i]));
                if (popupCloseButton != null && popupCloseButton.isDisplayed()) {
                    popupCloseButton.click();
                }
            } catch (Exception e) {
                // do nothing. popup may never appear
                LOGGER.info("Subscription "+SUBSCRIPTION_POPUP[i]+" popup did not appear", e);
            }
        }
    }

    /**
     * getProducts
     * @return products
     */
    public List<Product> getProducts() {
        closePopups();
        List<Product> ret = new ArrayList<Product>();
        for (int i = 0; i < products.size(); i++) {
            WebElement product = products.get(i);
            List<Product> itemizedProducts = parseProducts(product);
            ret.addAll(itemizedProducts);
        }
        return ret;
    }

    /**
     * parseProduct
     * @param webElement
     * @return
     */
    private List<Product> parseProducts(WebElement product){
        List<Product> ret = new ArrayList<Product>();
        
        try
        {
        
            WebElement brand = product.findElement(By.className("brand"));
            WebElement title = product.findElement(By.className("productName"));
            
            Product p = new Product();
            p.setBrand(brand.getText());
            p.setTitle(title.getText());
            p.setUrl(title.getAttribute("href"));
            
            LOGGER.info("Analyzing product "+p);
            
            List<WebElement> options = product.findElements(By.xpath(".//*/div[contains(@class,\"variantes\")]/span"));
            for (WebElement option : options) {
    
                Product p2 = (Product) p.clone();
                
                String optionClass = option.getAttribute("className");
                if( !"available selected".equals(optionClass) ){
                    option.click();
                }
                
                p2.setSize(option.getText());
                
                try { 
                 
                    WebElement regularPriceDesc = product.findElement(By.xpath(".//*/div[contains(@class,\"prices table-cell\")]/div[1]/div[1]"));
                    WebElement regularPrice = product.findElement(By.xpath(".//*/div[contains(@class,\"prices table-cell\")]/div[1]/div[2]"));
                    WebElement promotionPriceDesc = product.findElement(By.xpath(".//*/div[contains(@class,\"prices table-cell\")]/div[2]/div[1]"));
                    WebElement promotionPrice = product.findElement(By.xpath(".//*/div[contains(@class,\"prices table-cell\")]/div[2]/div[2]"));
        
                    p2.setRegularPriceDesc(regularPriceDesc.getText());
                    p2.setRegularPrice(regularPrice.getText());
                    p2.setPromotionPriceDesc(promotionPriceDesc.getText());
                    p2.setPromotionPrice(promotionPrice.getText());
                    
                    LOGGER.info("Added product "+p2);
                    ret.add(p2);
    
                } catch (Exception e ){
                    
                    // bestPrice big
                    WebElement regularPrice = product.findElement(By.xpath(".//*/div[contains(@class,\"prices table-cell\")]/div[1]"));
                    p2.setRegularPrice(regularPrice.getText());
                    LOGGER.info("Added product (no promotion) "+p2);
                    ret.add(p2);
                }
                
            }
        
        
        } catch( Exception e2 ){
            
            LOGGER.error("Error parsing product");
            LOGGER.error(e2.getMessage(),(Object[])e2.getStackTrace());
            // SeleniumUtils.captureScreenshot(driver);
        }
        
        return ret;
    }

    /**
     * getNextPage
     * @return
     */
    public PuppisListingPage getNextPage() {
        
        if (nextLink != null && nextLink.isEnabled()) {
            nextLink.click();
            String curl = driver.getCurrentUrl();
            if( !currentUrl.equals(curl) ) 
                return new PuppisListingPage(driver);
            else 
                return null;
        }
        
        return null;
    }   
}
