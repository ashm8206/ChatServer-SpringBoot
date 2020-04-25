package edu.udacity.java.nano;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static edu.udacity.java.nano.chat.Constants.USERNAME_ATTRIBUTE;

@SpringBootApplication
@RestController
public class WebSocketChatApplication {
    private static final String USERNAME_MODEL_PARAMETER = "username";
    private static final String WS_URL_MODEL_PARAMETER = "webSocketUrl";
    private static final String CHAT_VIEW = "chat";

    private static Logger logger = LoggerFactory.getLogger(WebSocketChatApplication.class);

    @Value("${webSocketUrl}")
    private String webSocketUrl;

    public static void main(String[] args) {
        SpringApplication.run(WebSocketChatApplication.class, args);
    }

    /**
     * Login Page
     */
    @GetMapping("/")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    /**
     * Chatroom Page
     */
    @GetMapping("/index")
    public ModelAndView index(String username, HttpServletRequest request) {
        request.getSession().setAttribute(USERNAME_ATTRIBUTE, username);
        ModelAndView modelAndView = getChatView();
        modelAndView.addObject(USERNAME_MODEL_PARAMETER, username);
        modelAndView.addObject(WS_URL_MODEL_PARAMETER, webSocketUrl);
        return modelAndView;
    }

    private ModelAndView getChatView() {
        int idx = webSocketUrl.lastIndexOf("/");
        String viewName = idx > 0 ? webSocketUrl.substring(idx) : CHAT_VIEW;
        return new ModelAndView(viewName);
    }
}