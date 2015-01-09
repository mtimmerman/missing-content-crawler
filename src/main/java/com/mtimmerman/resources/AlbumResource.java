package com.mtimmerman.resources;

import org.springframework.hateoas.ResourceSupport;

/**
 * Created by maarten on 02.01.15.
 */
public class AlbumResource extends ResourceSupport {
    private Integer pk;
    private String plexName;
    private String plexKey;
    private String lastFMmbId;
    private String lastFMName;

    public Integer getPk() {
        return pk;
    }

    public void setPk(Integer pk) {
        this.pk = pk;
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

    public String getLastFMmbId() {
        return lastFMmbId;
    }

    public void setLastFMmbId(String lastFMmbId) {
        this.lastFMmbId = lastFMmbId;
    }

    public String getLastFMName() {
        return lastFMName;
    }

    public void setLastFMName(String lastFMName) {
        this.lastFMName = lastFMName;
    }
}
