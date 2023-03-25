package example.sse.server.controller.sse.emitter.registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CountRegistry extends SseEmitterMapRegistry<UUID> {

    private final Logger log = LoggerFactory.getLogger(CountRegistry.class);
    private final PubSubRegistry<UUID> registry;
    private final AtomicLong counter;

    public CountRegistry() {
        super();
        this.registry = new PubSubRegistry<>();
        this.counter = new AtomicLong();
    }

    public long count() {
        long count = counter.incrementAndGet();
        log.info("count...now={}", count);
        return count;
    }

    public long getCount() {
        return counter.get();
    }

    @Override
    public void register(UUID uuid, SseEmitter emitter) {
        registry.register(uuid, emitter);
    }

    @Override
    public SseEmitter get(UUID uuid) {
        return registry.get(uuid);
    }

    @Override
    public void remove(UUID uuid) {
        registry.remove(uuid);
    }

    @Override
    public Collection<SseEmitter> getEmitters() {
        return registry.getEmitters();
    }

    public void publish(SseEmitter.SseEventBuilder builder) {
        registry.publish(builder);
    }
}
