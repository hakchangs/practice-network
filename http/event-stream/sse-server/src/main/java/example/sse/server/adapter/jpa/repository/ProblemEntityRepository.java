package example.sse.server.adapter.jpa.repository;

import example.sse.server.adapter.jpa.entity.ProblemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemEntityRepository extends JpaRepository<ProblemEntity, Long> {
}
