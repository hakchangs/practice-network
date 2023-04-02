package example.sse.server.adapter.jpa.mapper;

public interface EntityMapper<D, E> {

    E toEntity(D domain);

    D toDomain(E entity);

}
