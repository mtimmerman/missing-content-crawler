package com.mtimmerman.rest.resources;

import org.springframework.hateoas.ResourceSupport;

/**
 * Created by maarten on 02.01.15.
 */
public class SeasonResource extends ResourceSupport {
    private Integer pk;
    private Integer theTVDbSeasonNumber;
    private Integer theTVDbSeasonId;
    private String plexName;
    private String plexKey;

    public Integer getPk() {
        return pk;
    }

    public void setPk(Integer pk) {
        this.pk = pk;
    }

    public Integer getTheTVDbSeasonNumber() {
        return theTVDbSeasonNumber;
    }

    public void setTheTVDbSeasonNumber(Integer theTVDbSeasonNumber) {
        this.theTVDbSeasonNumber = theTVDbSeasonNumber;
    }

    public Integer getTheTVDbSeasonId() {
        return theTVDbSeasonId;
    }

    public void setTheTVDbSeasonId(Integer theTVDbSeasonId) {
        this.theTVDbSeasonId = theTVDbSeasonId;
    }

    public String getPlexName() {
        return plexName;
    }

    public void setPlexName(String plexName) {
        this.plexName = plexName;
    }

    public String getPlexKey() {
        return plexKey;
    }

    public void setPlexKey(String plexKey) {
        this.plexKey = plexKey;
    }
}
