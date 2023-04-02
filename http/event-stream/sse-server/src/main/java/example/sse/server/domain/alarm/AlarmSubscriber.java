package example.sse.server.domain.alarm;

public interface AlarmSubscriber<T extends Alarm<?>> {

    void onSubscribe(T alarm);

}
