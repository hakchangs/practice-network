package example.sse.server.config;

import example.sse.server.adapter.http.sse.registry.PubSubSseRegistry;
import example.sse.server.usecase.alarm.AlarmEventPublisher;
import example.sse.server.usecase.alarm.AlarmEventSubscriber;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlarmConfig {

    @Bean
    public AlarmEventPublisher alarmPublisher(ApplicationEventPublisher applicationEventPublisher) {
        return new AlarmEventPublisher(applicationEventPublisher);
    }

    @Bean
    public AlarmEventSubscriber alarmSubscriber() {
        return new AlarmEventSubscriber();
    }

    @Bean
    public PubSubSseRegistry<String> alarmSseRegistry() {
        return new PubSubSseRegistry<>();
    }

}
