package example.websocket.server.adapter.registry;

public interface WebSocketRegistry<ID, SESSION> {

    void register(ID id, SESSION session);

    void remove(ID id);

    SESSION get(ID id);

}
