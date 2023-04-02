package example.sse.server.adapter.http.dto.mapper;

import java.util.Collection;

public interface DtoMapper<REQUEST, RESPONSE, D> {

    D fromRequest(REQUEST request);

    D fromResponse(RESPONSE response);

    REQUEST toRequest(D domain);

    RESPONSE toResponse(D domain);

    Collection<RESPONSE> toResponses(Collection<D> domains);

}
