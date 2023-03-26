package example.websocket.server.config;

import example.websocket.server.adapter.count.CountHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;

@Configuration
@EnableWebSocket
public class WebSocketConfig {

    @Autowired CountHandler countHandler;

    @Bean
    WebSocketConfigurer webSocketConfigurer() {
        return registry -> {
            registry.addHandler(countHandler, "/ws/counter").setAllowedOrigins("*");
        };
    }

}
