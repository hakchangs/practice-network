package example.websocket.server.adapter.count;

public class CountRequest {

    private final int size;
    private final int speed;

    public CountRequest(int size, int speed) {
        this.size = size;
        this.speed = speed;
    }

    public int getSize() {
        return size;
    }

    public int getSpeed() {
        return speed;
    }
}
