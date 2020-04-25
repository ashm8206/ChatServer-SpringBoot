package edu.udacity.java.nano.pages;

import org.openqa.selenium.WebDriver;

public class ChatPage extends AbstractPage {

    public ChatPage(WebDriver driver) {
        super(driver);
    }

    public <T> T chat(Class<T> resultPage, String message) {
        return (T) resultPage;
    }

    public String getTitle() {
        return driver.getTitle();
    }
}
