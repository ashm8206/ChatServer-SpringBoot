package edu.udacity.java.nano;

import edu.udacity.java.nano.pages.ChatPage;
import edu.udacity.java.nano.pages.LoginPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.htmlunit.webdriver.MockMvcHtmlUnitDriverBuilder;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class WebSocketChatApplicationTests {

    @Autowired
    private WebApplicationContext context;

    private WebDriver driver;

    @Before
    public void setup() {
        driver = MockMvcHtmlUnitDriverBuilder
                .webAppContextSetup(context)
                .build();
    }

    @After
    public void destroy() {
        if(driver != null) {
            driver.close();
        }
    }

    @Test
    public void loginTest() {
//        LoginPage loginPage = LoginPage.to(driver);
//        ChatPage chatPage = loginPage.login(ChatPage.class, "Andrew");
//        assertThat(chatPage.getTitle()).isEqualToIgnoringCase("Chat Room");
    }

    @Test
    public void chatTest() {

    }

    @Test
    public void leaveTest() {

    }
}