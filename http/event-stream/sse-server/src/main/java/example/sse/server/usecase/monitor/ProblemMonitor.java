package example.sse.server.usecase.monitor;

import example.sse.server.domain.monitor.Monitor;
import example.sse.server.domain.problem.Problem;
import example.sse.server.domain.problem.ProblemRepository;
import example.sse.server.usecase.alarm.*;

import java.util.Collection;

public class ProblemMonitor implements Monitor<Problem> {

    private final ProblemRepository problemRepository;
    private final AlarmEventPublisher eventPublisher;
    private final AlarmEventSubscriber eventSubscriber;

    public ProblemMonitor(ProblemRepository problemRepository,
                          AlarmEventPublisher eventPublisher,
                          AlarmEventSubscriber eventSubscriber) {
        this.problemRepository = problemRepository;
        this.eventPublisher = eventPublisher;
        this.eventSubscriber = eventSubscriber;
    }

    @Override
    public void addItem(Problem input) {
        Problem saved = problemRepository.save(input);
        notify(saved);
    }

    @Override
    public Collection<Problem> getAllItems() {
        return problemRepository.findAll();
    }

    @Override
    public void notify(Problem item) {
        AlarmEvent event = new AlarmEvent(this, AlarmType.problem, item);
        eventPublisher.publish(event);
    }
}
