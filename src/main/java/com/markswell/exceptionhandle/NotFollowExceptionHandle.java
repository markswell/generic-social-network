package com.markswell.exceptionhandle;

import com.markswell.exception.NotFollowException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import static jakarta.ws.rs.core.Response.Status.BAD_REQUEST;

@Provider
public class NotFollowExceptionHandle implements ExceptionMapper<NotFollowException> {

    @Override
    public Response toResponse(NotFollowException e) {
        return Response
                .status(BAD_REQUEST)
                .entity(e)
                .build();
    }
}
