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
        name = "season_id_seq",
        sequenceName = "season_id_seq"
)
public class Season {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "season_id_seq")
    @AccessType(AccessType.Type.PROPERTY)
    private Integer id;

    @ManyToOne(optional = false)
    private TvShow tvShow;

    @NotNull
    private Integer theTVDbSeasonNumber;

    @NotNull
    private Integer theTVDbSeasonId;

    private String plexName;

    private String plexKey;

    private Integer episodesMissing;

    public void setTvShow(TvShow tvShow) {
        this.tvShow = tvShow;
    }

    public void setPlexName(String plexName) {
        this.plexName = plexName;
    }

    public void setPlexKey(String plexKey) {
        this.plexKey = plexKey;
    }

    public void setTheTVDbSeasonNumber(Integer theTVDbSeasonNumber) {
        this.theTVDbSeasonNumber = theTVDbSeasonNumber;
    }

    public void setTheTVDbSeasonId(Integer theTVDbSeasonId) {
        this.theTVDbSeasonId = theTVDbSeasonId;
    }

    public String getPlexName() {
        return plexName;
    }

    public String getPlexKey() {
        return plexKey;
    }

    public Integer getTheTVDbSeasonNumber() {
        return theTVDbSeasonNumber;
    }

    public TvShow getTvShow() {
        return tvShow;
    }

    public Integer getId() {
        return id;
    }

    public Integer getTheTVDbSeasonId() {
        return theTVDbSeasonId;
    }

    public Integer getEpisodesMissing() {
        return episodesMissing;
    }

    public void setEpisodesMissing(Integer episodesMissing) {
        this.episodesMissing = episodesMissing;
    }
}
