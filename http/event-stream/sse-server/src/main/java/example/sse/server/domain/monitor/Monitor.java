package example.sse.server.domain.monitor;

import java.util.Collection;

public interface Monitor<T> {

    void addItem(T item);

    Collection<T> getAllItems();

    void notify(T item);

}
