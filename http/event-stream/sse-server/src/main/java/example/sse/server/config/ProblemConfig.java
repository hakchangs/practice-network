package example.sse.server.config;

import example.sse.server.adapter.http.dto.mapper.ProblemDtoMapper;
import example.sse.server.adapter.jpa.ProblemRepositoryJpaAdapter;
import example.sse.server.adapter.jpa.mapper.ProblemEntityMapper;
import example.sse.server.adapter.jpa.repository.ProblemEntityRepository;
import example.sse.server.domain.problem.Problem;
import example.sse.server.usecase.alarm.AlarmEventPublisher;
import example.sse.server.usecase.monitor.ProblemMonitor;
import example.sse.server.domain.monitor.Monitor;
import example.sse.server.domain.problem.ProblemRepository;
import example.sse.server.usecase.alarm.AlarmEventSubscriber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProblemConfig {

    private final ProblemEntityRepository jpaRepository;

    public ProblemConfig(ProblemEntityRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Bean
    public ProblemDtoMapper problemDtoMapper() {
        return new ProblemDtoMapper();
    }

    @Bean
    public ProblemEntityMapper problemEntityMapper() {
        return new ProblemEntityMapper();
    }

    @Bean
    public ProblemRepository problemRepository() {
        return new ProblemRepositoryJpaAdapter(jpaRepository, problemEntityMapper());
    }

    @Bean
    public Monitor<Problem> problemMonitor(AlarmEventPublisher alarmPublisher, AlarmEventSubscriber alarmSubscriber) {
        return new ProblemMonitor(problemRepository(), alarmPublisher, alarmSubscriber);
    }

}
