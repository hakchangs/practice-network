package example.sse.server.usecase.alarm;

import example.sse.server.domain.alarm.Alarm;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class AlarmEvent<T> extends ApplicationEvent implements Alarm<T> {

    private final AlarmType type;
    private final T content;

    public AlarmEvent(Object source, AlarmType type, T content) {
        super(source);
        this.type = type;
        this.content = content;
    }

    public AlarmEvent(Object source, Clock clock, AlarmType type, T content) {
        super(source, clock);
        this.type = type;
        this.content = content;
    }

    public AlarmType getType() {
        return type;
    }

    @Override
    public T getContent() {
        return content;
    }
}
