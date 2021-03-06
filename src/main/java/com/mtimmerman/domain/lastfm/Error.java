package com.mtimmerman.domain.lastfm;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import com.mtimmerman.domain.lastfm.enums.ErrorCode;

/**
 * Created by maarten on 29.12.14.
 */
public class Error {
    @JacksonXmlProperty(isAttribute = true)
    private ErrorCode code;

    @JacksonXmlText
    private String message;

    public ErrorCode getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
