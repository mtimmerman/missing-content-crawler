package com.mtimmerman.rest.resources.thetvdb;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

/**
 * Created by maarten on 29.12.14.
 */
public class SeriesList extends DataContainer {
    @JacksonXmlProperty(localName = "Series")
    private Series[] series;

    @JacksonXmlText
    private String value;

    public Series[] getSeries() {
        return series;
    }

    public String getValue() {
        return value;
    }
}
