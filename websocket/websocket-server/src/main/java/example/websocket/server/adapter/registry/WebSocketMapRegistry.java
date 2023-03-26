package example.websocket.server.adapter.registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketMapRegistry<ID, SESSION> implements WebSocketRegistry<ID, SESSION> {

    private final Logger log = LoggerFactory.getLogger(WebSocketMapRegistry.class);

    private final Map<ID, SESSION> registry;

    public WebSocketMapRegistry() {
        this.registry = new ConcurrentHashMap<>();
        log.info("registry prepared!");
    }

    @Override
    public void register(ID id, SESSION session) {
        registry.put(id, session);
        log.info("registered...id={}, session={}, size={}", id, session, registry.size());
    }

    @Override
    public void remove(ID id) {
        SESSION session = registry.remove(id);
        log.info("removed...id={}, session={}, size={}", id, session, registry.size());
    }

    @Override
    public SESSION get(ID id) {
        return registry.get(id);
    }
}
