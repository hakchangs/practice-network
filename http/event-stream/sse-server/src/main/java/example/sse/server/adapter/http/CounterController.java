package example.sse.server.adapter.http;

import example.sse.server.adapter.http.sse.registry.OneToOneSseRegistry;
import example.sse.server.domain.counter.Counter;
import example.sse.server.adapter.http.dto.CountEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/counter")
public class CounterController {

    private final Logger log = LoggerFactory.getLogger(CounterController.class);

    private final OneToOneSseRegistry<String> registry;
    private final Counter counter;
    private final ApplicationEventPublisher eventPublisher;

    public CounterController(Counter counter, ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
        this.registry = new OneToOneSseRegistry<>();
        this.counter = counter;
    }

    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    SseEmitter connect() {

        //1. id 생성
        final String id = generateId();

        //2. sse emitter 등록
        SseEmitter emitter = new SseEmitter();
        registry.register(id, emitter);

        //3. 연결알림 전달
        send(emitter, SseEmitter.event()
                .id(id).name("connect").data("connected!"));

        return emitter;
    }

    @PostMapping("/count")
    void count(@RequestParam String id,
               @RequestParam(defaultValue = "10") Integer size,
               @RequestParam(defaultValue = "100") Long speed) {
        final SseEmitter emitter = registry.get(id);
        eventPublisher.publishEvent(new CountEvent(this, emitter, id, size, speed));
    }

    @EventListener(CountEvent.class)
    public void onCount(CountEvent event) {

        final SseEmitter emitter = event.getEmitter();
        final String id = event.getId();
        final int size = event.getSize();
        final long speed = event.getSpeed();

        //1. count and send
        Future<?> future = counter.count(size, speed, count -> {
            send(emitter, SseEmitter.event()
                    .id(id).name("count").data(count));
        });

        //2. check done
        while (!future.isDone() && !future.isCancelled()) {
            log.trace("checking done...");
        }

        //3. send close
        send(emitter, SseEmitter.event()
                .id(id).name("close").data("please close..."));
        emitter.complete();
    }

    protected String generateId() {
        return UUID.randomUUID().toString();
    }

    protected void send(SseEmitter emitter, SseEmitter.SseEventBuilder builder) {
        try {
            emitter.send(builder);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
