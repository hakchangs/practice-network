package example.sse.server.adapter.http.sse.registry;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SseEmitterRegistry<ID> {

    void register(ID id, SseEmitter emitter);

    void remove(ID id);

    SseEmitter get(ID id);
}
