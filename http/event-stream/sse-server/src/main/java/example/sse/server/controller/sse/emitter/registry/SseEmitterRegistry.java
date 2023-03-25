package example.sse.server.controller.sse.emitter.registry;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SseEmitterRegistry<ID> {

    void register(ID id, SseEmitter emitter);

    void remove(ID id);

    SseEmitter get(ID id);
}
