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

import com.petmascota.robot.model.BrandLink;
import com.petmascota.robot.utils.SeleniumUtils;



/**
 * AnimalandiaCategoryPage
 * @author agustinadagnino
 *
 */
public class AnimalandiaCategoryPage extends Page {

    
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AnimalandiaCategoryPage.class.getName());

    /**
     * brandLinks
     */
    @FindBy(xpath = ".//*/div[contains(@class,\"sidebar listado-categorias\")]/ul/li/a")
    private List<WebElement> brandLinks;
    
    
    /**
     * ListingPagePuppis
     * @param driver
     */
    public AnimalandiaCategoryPage(WebDriver driver) {
        super(driver);
    }

    /**
     * go
     * @param url
     * @return
     */
    public AnimalandiaCategoryPage go(String url) {
        // go to url
        driver.get(url);

        // return page
        return new AnimalandiaCategoryPage(this.driver);
    }
    
    /**
     * getBrandLinks
     * @return
     */
    public List<BrandLink> getBrandLinks(){
        List<BrandLink> ret = new ArrayList<BrandLink>();
        try {
            WebDriverWait wait = SeleniumUtils.getWait(driver);
            wait.until(ExpectedConditions.presenceOfElementLocated( By.xpath(".//*/div[contains(@class,\"sidebar listado-categorias\")]/ul/li/a") ));
        } catch ( Exception e ){
            LOGGER.debug(e.getMessage());
        }
        
        for (WebElement brandLink : brandLinks) {
            // clean brand
            String brand = brandLink.getText();
            brand = brand.replaceAll("(\\d+)", "");
            brand = brand.replaceAll("\\(","").replaceAll("\\)","");
            // clean url
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
