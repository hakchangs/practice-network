package example.sse.server.adapter.http.dto.mapper;

import example.sse.server.adapter.http.dto.ProblemRequest;
import example.sse.server.adapter.http.dto.ProblemResponse;
import example.sse.server.domain.problem.Problem;

import java.util.Collection;

public class ProblemDtoMapper implements DtoMapper<ProblemRequest, ProblemResponse, Problem> {
    @Override
    public Problem fromRequest(ProblemRequest request) {
        return new Problem(request.getName(), request.getDescription());
    }

    @Override
    public Problem fromResponse(ProblemResponse response) {
        return new Problem(response.getName(), response.getDescription());
    }

    @Override
    public ProblemRequest toRequest(Problem domain) {
        return new ProblemRequest(domain.getName(), domain.getDescription());
    }

    @Override
    public ProblemResponse toResponse(Problem domain) {
        return new ProblemResponse(domain.getName(), domain.getDescription());
    }

    @Override
    public Collection<ProblemResponse> toResponses(Collection<Problem> domains) {
        return domains.stream().map(d -> toResponse(d)).toList();
    }
}
