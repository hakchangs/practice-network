package example.sse.server.usecase.alarm;

import example.sse.server.domain.alarm.AlarmSubscriber;

import java.util.function.Consumer;

public class AlarmEventSubscriber implements AlarmSubscriber<AlarmEvent<?>> {

    public void onSubscribe(Consumer<AlarmEvent<?>> consumer, AlarmEvent<?> event) {
        consumer.accept(event);
    }

    @Override
    public void onSubscribe(AlarmEvent<?> alarm) {
        throw new RuntimeException("not supported");
    }
}
