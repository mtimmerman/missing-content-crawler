package com.mtimmerman.service.exceptions;

import com.mtimmerman.rest.resources.lastfm.enums.ErrorCode;

/**
 * Created by maarten on 29.12.14.
 */
public class LastFMException extends Exception {
    private ErrorCode errorCode;

    public LastFMException(String message, ErrorCode errorCode) {
        super(message);

        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
