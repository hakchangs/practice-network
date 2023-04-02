package example.sse.server.domain.counter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;
import java.util.function.Consumer;

@Component
public class Counter {

    private final Logger log = LoggerFactory.getLogger(Counter.class);

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
