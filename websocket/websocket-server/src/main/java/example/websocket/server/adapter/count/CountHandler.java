package example.websocket.server.adapter.count;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import example.websocket.server.adapter.registry.WebSocketMapRegistry;
import example.websocket.server.adapter.registry.WebSocketRegistry;
import example.websocket.server.service.CountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Component
public class CountHandler extends TextWebSocketHandler {

    private final WebSocketRegistry<String, WebSocketSession> registry;
    private final CountService countService;
    private final ObjectMapper objectMapper;

    private final Logger log = LoggerFactory.getLogger(CountHandler.class);

    public CountHandler(CountService countService, ObjectMapper objectMapper) {
        this.countService = countService;
        this.objectMapper = objectMapper;
        this.registry = new WebSocketMapRegistry<>();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        log.info("connected...session={}", session);
        registry.register(session.getId(), session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("received...session={}, message={}", session, message);
        CountRequest request = objectMapper.readValue(message.getPayload(), CountRequest.class);
        countService.count(request.getSize(), request.getSpeed(), count -> {
            CountResponse response = new CountResponse(session.getId(), String.valueOf(count));
            try {
                String responseString = objectMapper.writeValueAsString(response);
                session.sendMessage(new TextMessage(responseString));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
        log.info("error...session={}, exception={}", session, exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        super.afterConnectionClosed(session, closeStatus);
        log.info("closed...session={}, closeStatus={}", session, closeStatus);
        registry.remove(session.getId());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
