package example.sse.server.domain.problem;

import java.util.Collection;

public interface ProblemRepository {

    Problem save(Problem problem);

    Problem findById(Long id);

    void removeById(Long id);

    Collection<Problem> findAll();

}
