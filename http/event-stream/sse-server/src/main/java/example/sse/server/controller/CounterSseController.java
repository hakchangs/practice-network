package example.sse.server.controller;

import example.sse.server.controller.sse.emitter.registry.OneToOneRegistry;
import example.sse.server.service.CountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/counter")
public class CounterSseController {

    private final Logger log = LoggerFactory.getLogger(CounterSseController.class);

    private final OneToOneRegistry<String> registry;
    private final CountService countService;

    public CounterSseController(CountService countService) {
        this.registry = new OneToOneRegistry<>();
        this.countService = countService;
    }

    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    SseEmitter add() {

        final String id = generateId();

        SseEmitter emitter = new SseEmitter();
        registry.register(id, emitter);

        try {
            emitter.send(SseEmitter.event()
                    .id(id)
                    .name("connect")
                    .data("connected!"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return emitter;
    }

    @PostMapping("/count")
    void count(@RequestParam String id,
               @RequestParam(defaultValue = "10") Integer size,
               @RequestParam(defaultValue = "100") Long speed) {

        final SseEmitter emitter = registry.get(id);

        //1. count and send
        Future<?> future = countService.count(size, speed, count -> {
            send(emitter, SseEmitter.event()
                    .id(id).name("count").data(count));
        });

        //2. check done
        while (!future.isDone() && !future.isCancelled()) {
            log.info("checking done...");
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
