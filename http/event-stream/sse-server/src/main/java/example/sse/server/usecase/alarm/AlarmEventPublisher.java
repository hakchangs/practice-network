package example.sse.server.usecase.alarm;

import example.sse.server.domain.alarm.AlarmPublisher;
import org.springframework.context.ApplicationEventPublisher;

public class AlarmEventPublisher implements AlarmPublisher<AlarmEvent<?>> {

    private final ApplicationEventPublisher eventPublisher;

    public AlarmEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void publish(AlarmEvent<?> alarm) {
        eventPublisher.publishEvent(alarm);
    }
}
