package com.seagalputra.tidder.api.exception;

public class SpringTidderException extends RuntimeException {
    public SpringTidderException(String message, Exception exception) {
        super(message, exception);
    }

    public SpringTidderException(String message) {
        super(message);
    }
}
