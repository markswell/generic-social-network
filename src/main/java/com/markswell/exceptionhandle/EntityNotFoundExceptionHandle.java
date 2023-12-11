package com.markswell.exceptionhandle;

import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

@Provider
public class EntityNotFoundExceptionHandle implements ExceptionMapper<EntityNotFoundException> {
    @Override
    public Response toResponse(EntityNotFoundException e) {
        return Response
                .status(NOT_FOUND)
                .entity(e.getMessage())
                .build();
    }
}
