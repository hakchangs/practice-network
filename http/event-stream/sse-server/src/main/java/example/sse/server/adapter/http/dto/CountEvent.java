package example.sse.server.adapter.http.dto;

import org.springframework.context.ApplicationEvent;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public class CountEvent extends ApplicationEvent {

    private final SseEmitter emitter;
    private final String id;
    private final int size;
    private final long speed;

    public CountEvent(Object source, SseEmitter emitter, String id, int size, long speed) {
        super(source);
        this.emitter = emitter;
        this.id = id;
        this.size = size;
        this.speed = speed;
    }

    public SseEmitter getEmitter() {
        return emitter;
    }

    public String getId() {
        return id;
    }

    public int getSize() {
        return size;
    }

    public long getSpeed() {
        return speed;
    }
}
