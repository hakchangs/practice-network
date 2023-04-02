package example.sse.server.adapter.http.dto;

public class ProblemRequest {

    private final String name;
    private final String description;

    public ProblemRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
