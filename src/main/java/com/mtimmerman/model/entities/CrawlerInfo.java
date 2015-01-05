package com.mtimmerman.model.entities;

import com.mtimmerman.model.enums.CrawlerType;
import org.springframework.data.annotation.AccessType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by maarten on 02.01.15.
 */
@Entity
@SequenceGenerator(
        name = "crawler_info_id_seq",
        sequenceName = "crawler_info_id_seq"
)
public class CrawlerInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "crawler_info_id_seq")
    @AccessType(AccessType.Type.PROPERTY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private CrawlerType crawlerType;

    @NotNull
    private Boolean processing;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastProcessed;

    @Column(name = "latest_stack_trace", columnDefinition = "text")
    private String latestStackTrace;

    @Column(name = "latest_error")
    private String latestError;

    @Column(name = "latest_error_on")
    private Date latestErrorOn;

    @Column(name = "log", columnDefinition = "text")
    private String log;

    public void setCrawlerType(CrawlerType crawlerType) {
        this.crawlerType = crawlerType;
    }

    public void setProcessing(Boolean processing) {
        this.processing = processing;
    }

    public void setLastProcessed(Date lastProcessed) {
        this.lastProcessed = lastProcessed;
    }

    public void setLatestStackTrace(String latestStackTrace) {
        this.latestStackTrace = latestStackTrace;
    }

    public void setLatestError(String latestError) {
        this.latestError = latestError;
    }

    public void setLatestErrorOn(Date latestErrorOn) {
        this.latestErrorOn = latestErrorOn;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public Boolean getProcessing() {
        return processing;
    }

    public Date getLastProcessed() {
        return lastProcessed;
    }

    public CrawlerType getCrawlerType() {
        return crawlerType;
    }
}
