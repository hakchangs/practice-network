package example.sse.server.adapter.http;

import example.sse.server.adapter.http.sse.registry.PubSubSseRegistry;
import example.sse.server.usecase.alarm.AlarmEvent;
import example.sse.server.usecase.alarm.AlarmEventSubscriber;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/monitor")
public class MonitorController {

    private final PubSubSseRegistry<String> sseRegistry;
    private final AlarmEventSubscriber alarmSubscriber;

    public MonitorController(PubSubSseRegistry<String> alarmSseRegistry, AlarmEventSubscriber alarmEventSubscriber) {
        this.sseRegistry = alarmSseRegistry;
        this.alarmSubscriber = alarmEventSubscriber;
    }

    @GetMapping(path = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    SseEmitter connect() throws IOException {

        final String id = UUID.randomUUID().toString();
        SseEmitter emitter = new SseEmitter();
        sseRegistry.register(id, emitter);

        emitter.send(SseEmitter.event().id(id)
                .name("connect")
                .data("connected!"));

        return emitter;
    }

    @EventListener(AlarmEvent.class)
    public void onEvent(AlarmEvent<?> event) {
        alarmSubscriber.onSubscribe(alarmEvent -> sseRegistry
                .broadcast(SseEmitter.event()
                        .name(alarmEvent.getType().name())
                        .data(alarmEvent.getContent())),
                event);
    }

}
