package com.petmascota.robot.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Page
 * @author agustinadagnino
 *
 */
public class Page {

    /**
     * driver
     */
    protected WebDriver driver;

    /**
     * Default constructor
     * 
     * @param driver
     */
    public Page(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

}
