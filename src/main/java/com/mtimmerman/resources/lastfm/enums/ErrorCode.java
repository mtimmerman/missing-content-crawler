package com.mtimmerman.resources.lastfm.enums;

/**
 * Created by maarten on 29.12.14.
 */
public enum ErrorCode {
    INVALID_SERVICE(2),
    INVALID_METHOD(3),
    AUTHENTICATION_FAILED(4),
    INVALID_FORMAT(5),
    INVALID_PARAMETERS(6),
    INVALID_RESOURCE(7),
    OPERATION_FAILED(8),
    INVALID_SESSION_KEY(9),
    INVALID_API_KEY(10),
    SERVICE_OFFLINE(11),
    INVALID_METHOD_SIGNATURE(13),
    TEMPORARY_ERROR(16),
    SUSPENDED_API_KEY(26),
    RATE_LIMIT_EXCEEDED(29),
    NO_ALBUMS_FOUND(1000);

    int value;

    private ErrorCode(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    public static ErrorCode parse(int value) {
        ErrorCode type = null;
        for (ErrorCode errorCode : ErrorCode.values()) {
            if (value == errorCode.value()) {
                type = errorCode;
                break;
            }
        }
        return type;
    }
}
