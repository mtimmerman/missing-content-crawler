package com.mtimmerman.domain.thetvdb;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Created by maarten on 30.12.14.
 */
public class FullSeriesRecord extends DataContainer {
    @JacksonXmlProperty(localName = "Series")
    private BaseSeriesRecord baseSeriesRecord;
    @JacksonXmlProperty(localName = "Episode")
    private BaseEpisodeRecord[] baseEpisodeRecords;

    public BaseEpisodeRecord[] getBaseEpisodeRecords() {
        return baseEpisodeRecords;
    }

    public BaseSeriesRecord getBaseSeriesRecord() {
        return baseSeriesRecord;
    }
}
