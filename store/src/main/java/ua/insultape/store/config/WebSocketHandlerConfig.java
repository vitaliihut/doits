package ua.insultape.store.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
@EnableWebSocket
public class WebSocketHandlerConfig implements WebSocketConfigurer {

    private static final Map<Integer, Set<WebSocketSession>> subscriptions = new HashMap<>();

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new CustomWebSocketHandler(), "/ws/{user_id}");
    }

    private static class CustomWebSocketHandler implements WebSocketHandler {

        @Override
        public void afterConnectionEstablished(WebSocketSession session) throws Exception {
            Integer userId = extractUserIdFromSession(session);
            log.info("WebSocket user id: {}", userId);
            if (userId != null) {
                if (!subscriptions.containsKey(userId)) {
                    subscriptions.put(userId, new HashSet<>());
                }
                subscriptions.get(userId).add(session);
            }
        }

        @Override
        public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
            // Handle incoming messages if needed
        }

        @Override
        public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
            // Handle transport errors if needed
        }

        @Override
        public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
            Integer userId = extractUserIdFromSession(session);
            if (userId != null && subscriptions.containsKey(userId)) {
                subscriptions.get(userId).remove(session);
            }
        }

        @Override
        public boolean supportsPartialMessages() {
            return false;
        }

        private Integer extractUserIdFromSession(WebSocketSession session) {
            String[] parts = session.getUri().getPath().split("/");
            try {
                return Integer.parseInt(parts[parts.length - 1]);
            } catch (NumberFormatException e) {
                return null;
            }
        }
    }

    // Function to send data to subscribed users
    public void sendDataToSubscribers(Integer userId, String data) {
        if (subscriptions.containsKey(userId)) {
            for (WebSocketSession session : subscriptions.get(userId)) {
                try {
                    log.info("Sending message: {}", data);
                    session.sendMessage(new TextMessage(data));
                } catch (IOException e) {
                    log.error("Unexpected exception: ",e);
                }
            }
        }
    }
}