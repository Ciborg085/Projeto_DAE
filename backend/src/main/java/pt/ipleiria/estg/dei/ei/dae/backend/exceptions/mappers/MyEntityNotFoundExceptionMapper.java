package pt.ipleiria.estg.dei.ei.dae.backend.exceptions.mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;

import java.util.logging.Logger;

public class MyEntityNotFoundExceptionMapper
        implements ExceptionMapper<MyEntityNotFoundException> {
    private static final Logger logger = Logger.getLogger(MyEntityNotFoundExceptionMapper.class.getCanonicalName());

    @Override
    public Response toResponse(MyEntityNotFoundException e) {
        String errorMsg = e.getMessage();
        logger.warning("ERROR: " + errorMsg);
        return Response.status(Response.Status.NOT_FOUND)
                .entity(errorMsg)
                .build();
    }

}
