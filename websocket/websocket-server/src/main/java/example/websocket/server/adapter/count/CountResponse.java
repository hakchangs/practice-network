package example.websocket.server.adapter.count;

public class CountResponse {

    private final String id;
    private final String message;

    public CountResponse(String id, String message) {
        this.id = id;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
