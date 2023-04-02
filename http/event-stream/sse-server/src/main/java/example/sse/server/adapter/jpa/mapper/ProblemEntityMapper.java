package example.sse.server.adapter.jpa.mapper;

import example.sse.server.adapter.jpa.entity.ProblemEntity;
import example.sse.server.domain.problem.Problem;

public class ProblemEntityMapper implements EntityMapper<Problem, ProblemEntity> {

    @Override
    public ProblemEntity toEntity(Problem domain) {
        return new ProblemEntity(domain.getName(), domain.getDescription());
    }

    @Override
    public Problem toDomain(ProblemEntity entity) {
        return new Problem(entity.getId(), entity.getName(), entity.getDescription());
    }
}
