package com.mtimmerman.model.entities;

import org.springframework.data.annotation.AccessType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

/**
 * Created by maarten on 30.12.14.
 */
@Entity
@SequenceGenerator(
        name = "episode_id_seq",
        sequenceName = "episode_id_seq"
)
public class Episode {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "episode_id_seq")
    @AccessType(AccessType.Type.PROPERTY)
    private Integer id;

    @ManyToOne(optional = false)
    private Season season;

    private String plexName;

    private String plexKey;

    @NotNull
    private String theTVDbEpisodeName;

    @NotNull
    private Integer theTVDbEpisodeNumber;

    @NotNull
    private String searchName;

    public void setSeason(Season season) {
        this.season = season;
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

    public String getSearchName() {
        return searchName;
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

    public Integer getId() {
        return id;
    }

    public Integer getTheTVDbEpisodeNumber() {
        return theTVDbEpisodeNumber;
    }

    public Season getSeason() {
        return season;
    }
}
