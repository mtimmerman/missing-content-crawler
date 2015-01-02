package com.mtimmerman.rest.resources;

import org.springframework.hateoas.ResourceSupport;

/**
 * Created by maarten on 02.01.15.
 */
public class TvShowResource extends ResourceSupport {
    private Integer pk;
    private String theTVDbName;
    private String plexName;
    private String plexKey;

    public Integer getPk() {
        return pk;
    }

    public void setPk(Integer pk) {
        this.pk = pk;
    }

    public String getTheTVDbName() {
        return theTVDbName;
    }

    public void setTheTVDbName(String theTVDbName) {
        this.theTVDbName = theTVDbName;
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
