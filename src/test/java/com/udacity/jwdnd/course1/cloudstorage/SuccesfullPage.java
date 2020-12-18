package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SuccesfullPage {

    private final JavascriptExecutor js;

    @FindBy(id = "succesfull")
    private WebElement succesfull;

    public SuccesfullPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
    }



    public void clickOk() {
        js.executeScript("arguments[0].click();", succesfull);
    }
}
