package example.sse.server.adapter.http.sse.registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

public class PubSubSseRegistry<ID> extends SseEmitterMapRegistry<ID> {

    private final Logger log = LoggerFactory.getLogger(PubSubSseRegistry.class);

    public PubSubSseRegistry() {
        super();
    }

    public void publish(SseEmitter.SseEventBuilder builder) {
        log.info("publish...");
        broadcast(builder);
    }

    public void broadcast(SseEmitter.SseEventBuilder builder) {
        log.info("broadcast...");
        getRegistry().values().forEach(emitter -> {
            try {
                log.info("send to emitter...");
                emitter.send(builder);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
