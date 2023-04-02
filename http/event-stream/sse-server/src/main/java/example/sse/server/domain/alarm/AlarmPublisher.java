package example.sse.server.domain.alarm;

public interface AlarmPublisher<T extends Alarm<?>> {

    void publish(T alarm);

}
