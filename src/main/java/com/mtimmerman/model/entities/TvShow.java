package com.mtimmerman.model.entities;

import org.springframework.data.annotation.AccessType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

/**
 * Created by maarten on 30.12.14.
 */
@Entity
@SequenceGenerator(
        name = "tv_show_id_seq",
        sequenceName = "tv_show_id_seq"
)
public class TvShow {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tv_show_id_seq")
    @AccessType(AccessType.Type.PROPERTY)
    private Integer id;

    @NotNull
    private String theTVDbName;

    @NotNull
    private String plexName;

    @NotNull
    private String plexKey;

    private Integer episodesMissing;

    public void setTheTVDbName(String theTVDbName) {
        this.theTVDbName = theTVDbName;
    }

    public void setPlexName(String plexName) {
        this.plexName = plexName;
    }

    public void setPlexKey(String plexKey) {
        this.plexKey = plexKey;
    }

    public String getTheTVDbName() {
        return theTVDbName;
    }

    public Integer getId() {
        return id;
    }

    public String getPlexName() {
        return plexName;
    }

    public String getPlexKey() {
        return plexKey;
    }

    public Integer getEpisodesMissing() {
        return episodesMissing;
    }

    public void setEpisodesMissing(Integer episodesMissing) {
        this.episodesMissing = episodesMissing;
    }
}
