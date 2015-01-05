package com.mtimmerman.rest.resources;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mtimmerman.model.enums.CrawlerType;
import com.mtimmerman.model.serializers.DateTimeSerializer;
import org.springframework.hateoas.ResourceSupport;

import java.util.Date;

/**
 * Created by maarten on 05.01.15.
 */
public class CrawlerInfoResource extends ResourceSupport {
    private Integer pk;
    private CrawlerType crawlerType;
    private Boolean processing;
    private Date lastProcessed;
    private String latestStackTrace;
    private String latestError;
    private Date latestErrorOn;

    public Integer getPk() {
        return pk;
    }

    public void setPk(Integer pk) {
        this.pk = pk;
    }

    public CrawlerType getCrawlerType() {
        return crawlerType;
    }

    public void setCrawlerType(CrawlerType crawlerType) {
        this.crawlerType = crawlerType;
    }

    public Boolean getProcessing() {
        return processing;
    }

    public void setProcessing(Boolean processing) {
        this.processing = processing;
    }

    @JsonSerialize(using= DateTimeSerializer.class)
    public Date getLastProcessed() {
        return lastProcessed;
    }

    public void setLastProcessed(Date lastProcessed) {
        this.lastProcessed = lastProcessed;
    }

    public String getLatestStackTrace() {
        return latestStackTrace;
    }

    public void setLatestStackTrace(String latestStackTrace) {
        this.latestStackTrace = latestStackTrace;
    }

    public String getLatestError() {
        return latestError;
    }

    public void setLatestError(String latestError) {
        this.latestError = latestError;
    }

    @JsonSerialize(using= DateTimeSerializer.class)
    public Date getLatestErrorOn() {
        return latestErrorOn;
    }

    public void setLatestErrorOn(Date latestErrorOn) {
        this.latestErrorOn = latestErrorOn;
    }
}
