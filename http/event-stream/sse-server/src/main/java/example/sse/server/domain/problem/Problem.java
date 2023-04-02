package example.sse.server.domain.problem;

public class Problem {

    private final Long id;
    private final String name;
    private final String description;

    public Problem(String name, String description) {
        this(null, name, description);
    }

    public Problem(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
