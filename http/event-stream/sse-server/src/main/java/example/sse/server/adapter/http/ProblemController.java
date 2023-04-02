package example.sse.server.adapter.http;

import example.sse.server.adapter.http.dto.ProblemResponse;
import example.sse.server.adapter.http.dto.mapper.ProblemDtoMapper;
import example.sse.server.adapter.http.dto.ProblemRequest;
import example.sse.server.domain.problem.Problem;
import example.sse.server.domain.monitor.Monitor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/problems")
public class ProblemController {

    private final ProblemDtoMapper dtoMapper;
    private final Monitor<Problem> monitor;

    public ProblemController(ProblemDtoMapper dtoMapper, Monitor<Problem> monitor) {
        this.dtoMapper = dtoMapper;
        this.monitor = monitor;
    }

    @PostMapping
    void add(@RequestBody ProblemRequest request) {
        Problem problem = dtoMapper.fromRequest(request);
        monitor.addItem(problem);
    }

    @GetMapping
    Collection<ProblemResponse> getAll() {
        Collection<Problem> problems = monitor.getAllItems();
        return dtoMapper.toResponses(problems);
    }

}
