package example.sse.server.adapter.http.sse.registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SseEmitterMapRegistry<ID> implements SseEmitterRegistry<ID> {

    private final Logger log = LoggerFactory.getLogger(SseEmitterMapRegistry.class);
    private final Map<ID, SseEmitter> registry;

    public SseEmitterMapRegistry() {
        this.registry = new ConcurrentHashMap<>();
    }

    @Override
    public void register(ID id, SseEmitter emitter) {

        emitter.onTimeout(() -> {
            log.info("timeout...");
            emitter.complete();
        });
        emitter.onCompletion(() -> {
            log.info("completion...");
            remove(id);
        });

        registry.put(id, emitter);
        log.info("emitter registered...id={}, total={}", id, registry.size());
    }

    @Override
    public void remove(ID id) {
        registry.remove(id);
        log.info("emitter removed...id={}, total={}", id, registry.size());
    }

    @Override
    public SseEmitter get(ID id) {
        return registry.get(id);
    }

    public Map<ID, SseEmitter> getRegistry() {
        return registry;
    }

    public Collection<SseEmitter> getEmitters() {
        return registry.values();
    }
}
