package com.petmascota.robot.page;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.petmascota.robot.model.BrandLink;

/**
 * MarketcanBrandPage
 * @author agustinadagnino
 *
 */
public class MarketcanBrandPage extends Page {

    @FindBy( xpath = "//*[@id=\"subcategories\"]/ul/li/div/a" )
    private List<WebElement> brands;
    
    /**
     * MarketcanBrandPage
     * @param driver
     */
    public MarketcanBrandPage(WebDriver driver) {
        super(driver);
    }

    /**
     * go
     * @param url
     * @return
     */
    public MarketcanBrandPage go(String url) {
        // go to url
        driver.get(url);
        // return page
        return new MarketcanBrandPage(this.driver);
    }
    
    /**
     * getBrandLinks
     * @return brand links
     */
    public List<BrandLink> getBrandLinks(){
        List<BrandLink> ret = new ArrayList<BrandLink>();
        
        for (WebElement brandLink : brands) {
            String brand = brandLink.getAttribute("title");
            String url = brandLink.getAttribute("href");
            // build result object
            BrandLink bl = new BrandLink();
            bl.setBrand(brand);
            bl.setLink(url);
            ret.add(bl);
        }
        
        return ret;
    }
    
    
}
