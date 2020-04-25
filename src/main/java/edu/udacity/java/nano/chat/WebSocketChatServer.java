package edu.udacity.java.nano.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.alibaba.fastjson.JSON.parseObject;
import static com.alibaba.fastjson.JSON.toJSONString;
import static edu.udacity.java.nano.chat.Constants.USERNAME_ATTRIBUTE;
import static java.lang.String.format;

/**
 * WebSocket Server
 *
 * @see ServerEndpoint WebSocket Client
 * @see Session   WebSocket Session
 */

@Component
@ServerEndpoint(value = "/chat", configurator = WebSocketChatServerConfigurator.class)
public class WebSocketChatServer {

    private static Logger logger = LoggerFactory.getLogger(WebSocketChatServer.class);

    /**
     * All chat sessions.
     */
    private static Map<String, Session> onlineSessions = new ConcurrentHashMap<>();

    private static void sendMessageToAll(String msg) {
        onlineSessions.values().forEach(session -> {
            try {
                session.getBasicRemote().sendText(msg);
            } catch (IOException e) {
                logger.error("Error while sending message: {}", msg, e);
                e.printStackTrace();
            }
        });
    }

    /**
     * Open connection, 1) add session, 2) add user.
     */
    @OnOpen
    public void onOpen(Session session) {
        onlineSessions.put(session.getId(), session);
        String username = getUserProperty(session, USERNAME_ATTRIBUTE);
        sayHello(username);
        logger.info("User \"{}\" joined chat (session id: \"{}\")", username, session.getId());
    }

    private String getUserProperty(Session session, String parameterName) {
        return (String) session.getUserProperties().get(parameterName);
    }

    private void sayHello(String username) {
        final String author = "System";
        final String text = format("User \"%s\" joined the chat.", username);
        final String type = "ENTER";
        Message welcomeMessage = new Message(author, text, type);
        welcomeMessage.setOnlineCount(getSessionsCount());
        sendMessageToAll(toJSONString(welcomeMessage));
    }

    private int getSessionsCount() {
        return onlineSessions.entrySet().size();
    }

    /**
     * Send message, 1) get username and session, 2) send message to all.
     */
    @OnMessage
    public void onMessage(Session session, String jsonStr) {
        if (onlineSessions.containsKey(session.getId())) {
            Message parseResult = parseObject(jsonStr, Message.class);
            Message messageToSend = new Message(parseResult.getAuthor(), parseResult.getText(), "SPEAK");
            messageToSend.setOnlineCount(getSessionsCount());
            sendMessageToAll(toJSONString(messageToSend));
            logger.info("Received new message (session id: {}): {}", session.getId(), jsonStr);
        }
    }

    /**
     * Close connection, 1) remove session, 2) update user.
     */
    @OnClose
    public void onClose(Session session) {
        if (onlineSessions.containsKey(session.getId())) {
            onlineSessions.remove(session.getId());
            String username = getUserProperty(session, USERNAME_ATTRIBUTE);
            sayGoodBye(username);
            logger.info("User \"{}\" left chat (session id: \"{}\")", username, session.getId());
        }
    }

    private void sayGoodBye(String username) {
        final String author = "System";
        final String text = format("User \"%s\" left the chat.", username);
        final String type = "LEAVE";
        Message goodByeMessage = new Message(author, text, type);
        goodByeMessage.setOnlineCount(getSessionsCount());
        sendMessageToAll(toJSONString(goodByeMessage));
    }

    /**
     * Print exception.
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

}
