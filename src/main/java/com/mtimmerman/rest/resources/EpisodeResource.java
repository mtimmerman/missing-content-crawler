package com.mtimmerman.rest.resources;

import org.springframework.hateoas.ResourceSupport;

/**
 * Created by maarten on 02.01.15.
 */
public class EpisodeResource extends ResourceSupport {
    private Integer pk;
    private String plexName;
    private String plexKey;
    private String theTVDbEpisodeName;
    private Integer theTVDbEpisodeNumber;
    private String searchName;

    public void setPk(Integer pk) {
        this.pk = pk;
    }

    public void setPlexName(String plexName) {
        this.plexName = plexName;
    }

    public void setPlexKey(String plexKey) {
        this.plexKey = plexKey;
    }

    public void setTheTVDbEpisodeName(String theTVDbEpisodeName) {
        this.theTVDbEpisodeName = theTVDbEpisodeName;
    }

    public void setTheTVDbEpisodeNumber(Integer theTVDbEpisodeNumber) {
        this.theTVDbEpisodeNumber = theTVDbEpisodeNumber;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public Integer getPk() {
        return pk;
    }

    public String getPlexName() {
        return plexName;
    }

    public String getPlexKey() {
        return plexKey;
    }

    public String getTheTVDbEpisodeName() {
        return theTVDbEpisodeName;
    }

    public Integer getTheTVDbEpisodeNumber() {
        return theTVDbEpisodeNumber;
    }

    public String getSearchName() {
        return searchName;
    }
}
