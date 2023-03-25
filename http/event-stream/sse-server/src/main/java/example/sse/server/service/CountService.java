package example.sse.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;
import java.util.function.Consumer;

@Service
public class CountService {

    private final Logger log = LoggerFactory.getLogger(CountService.class);

    public Future<?> count(final int size, final long speed, Consumer<Integer> consumer) {

        log.info("start counting...size={}, speed={}", size, speed);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        return executor.submit(() -> {
            int offset = 0;
            while (offset++ < size) {
                sleep(speed);
                log.info("counting...now offset={}", offset);
                consumer.accept(offset);
            }
        });
    }

    protected void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
