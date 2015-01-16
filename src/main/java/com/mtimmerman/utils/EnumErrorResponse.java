package com.mtimmerman.utils;

/**
 * Created by maarten on 13.01.15.
 */
public enum EnumErrorResponse {
    OAUTH_TOKEN_REVOKE_FAILED(2602, "oauth token revoke has failed.");

    int code;
    String message;

    private EnumErrorResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static EnumErrorResponse parse(int code) {
        EnumErrorResponse type = null;
        for (EnumErrorResponse t : EnumErrorResponse.values()) {
            if (code == t.getCode()) {
                type = t;
                break;
            }
        }
        return type;
    }
}