package example.sse.server.adapter.jpa;

import example.sse.server.adapter.jpa.entity.ProblemEntity;
import example.sse.server.adapter.jpa.repository.ProblemEntityRepository;
import example.sse.server.adapter.jpa.mapper.ProblemEntityMapper;
import example.sse.server.domain.problem.Problem;
import example.sse.server.domain.problem.ProblemRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class ProblemRepositoryJpaAdapter implements ProblemRepository {

    private final ProblemEntityRepository repository;
    private final ProblemEntityMapper mapper;

    public ProblemRepositoryJpaAdapter(ProblemEntityRepository repository,
                                       ProblemEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Problem save(Problem problem) {

        ProblemEntity sourceEntity = mapper.toEntity(problem);
        ProblemEntity savedEntity = repository.save(sourceEntity);

        return mapper.toDomain(savedEntity);
    }

    @Override
    public Problem findById(Long id) {
        Optional<ProblemEntity> entity = repository.findById(id);
        return mapper.toDomain(entity.get());
    }

    @Override
    public void removeById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Collection<Problem> findAll() {
        List<ProblemEntity> entities = repository.findAll();
        return entities.stream().map(mapper::toDomain).toList();
    }
}
