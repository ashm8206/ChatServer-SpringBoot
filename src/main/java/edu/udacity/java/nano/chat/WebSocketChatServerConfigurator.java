package edu.udacity.java.nano.chat;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

import static edu.udacity.java.nano.chat.Constants.USERNAME_ATTRIBUTE;

/**
 * Stores HttpSession attribute "username" in WebSocket session user properties
 */
public class WebSocketChatServerConfigurator extends ServerEndpointConfig.Configurator {
    @Override
    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        String username = (String) httpSession.getAttribute(USERNAME_ATTRIBUTE);
        config.getUserProperties().put(USERNAME_ATTRIBUTE, username);
    }
}