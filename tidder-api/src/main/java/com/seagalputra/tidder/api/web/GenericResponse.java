package com.seagalputra.tidder.api.web;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenericResponse {
    private boolean success;
    private Object data;
    private String errorMessage;

    public static GenericResponse SuccessResponse(Object data) {
        return new GenericResponse(true, data, "");
    }

    public static GenericResponse ErrorResponse(String errorMessage) {
        return new GenericResponse(false, null, errorMessage);
    }
}
