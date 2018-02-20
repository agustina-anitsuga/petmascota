package com.petmascota.robot.page;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.petmascota.robot.model.Product;

/**
 * TimoteoListingPage
 * @author agustinadagnino
 *
 */
public class TimoteoListingPage extends Page {

    /**
     * products
     */
    @FindBy(className = "item__info")
    private List<WebElement> products;
    
    /**
     * nextLink
     */
    @FindBy(xpath = "//*/li[contains(@class,\"pagination__next\")]")
    private WebElement nextLink;
    
    
    /**
     * TimoteoListingPage
     * @param driver
     */
    public TimoteoListingPage(WebDriver driver) {
        super(driver);
    }


    /**
     * go
     * @param url
     * @return
     */
    public TimoteoListingPage go(String url) {
        // go to url
        driver.get(url);
        // return page
        return new TimoteoListingPage(this.driver);
    }


    /**
     * getProducts
     * @return
     */
    public List<Product> getProducts() {
        List<Product> ret = new ArrayList<Product>();
        for (int i = 0; i < products.size(); i++) {
            WebElement product = products.get(i);
            List<Product> itemizedProducts = parseProducts(product);
            ret.addAll(itemizedProducts);
        }
        return ret;
    }


    /**
     * parseProducts
     * @param product
     * @return
     */
    private List<Product> parseProducts(WebElement product) {
        WebElement title = product.findElement(By.xpath(".//*/span[contains(@class,\"main-title\")]"));
        WebElement url = product.findElement(By.xpath(".//*/a"));
        WebElement price = product.findElement(By.xpath(".//*/span[contains(@class,\"price__fraction\")]"));
        
        List<Product> ret = new ArrayList<Product>();
        Product p = new Product();
        p.setTitle(title.getText());
        p.setRegularPrice(price.getText());
        p.setUrl(url.getAttribute("href"));
        ret.add(p);
        
        return ret;
    }


    /**
     * getNextPage
     * @return
     */
    public TimoteoListingPage getNextPage() {
        if (nextLink != null && nextLink.isEnabled()) {
            
            String className = nextLink.getAttribute("class");
            
            if(className.equals("pagination__next")){
                nextLink.findElement(By.xpath("a")).click();
                return new TimoteoListingPage(driver);
            }
        }
        
        return null;
    }

    
}
