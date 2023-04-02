package example.sse.server.adapter.http.dto;

public class ProblemResponse {

    private final String name;
    private final String description;

    public ProblemResponse(String name, String description) {
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
