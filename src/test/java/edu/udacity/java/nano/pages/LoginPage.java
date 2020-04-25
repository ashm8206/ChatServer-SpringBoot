package edu.udacity.java.nano.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends AbstractPage {

    @FindBy(css = "#username")
    private WebElement username;

    @FindBy(css = "a.submit")
    private WebElement submit;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public <T> T login(Class<T> resultPage, String username) {
        this.username.sendKeys(username);
        this.submit.click();
        return PageFactory.initElements(driver, resultPage);
    }

    public static LoginPage to(WebDriver driver) {
        driver.get("http://localhost:8080/");
        return PageFactory.initElements(driver, LoginPage.class);
    }
}
