package com.mtimmerman.service.exceptions;

import java.io.IOException;

/**
 * Created by maarten on 09.01.15.
 */
public class HttpException extends IOException {
    private Integer code;

    public HttpException(String message, Integer code) {
        super(message);

        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}