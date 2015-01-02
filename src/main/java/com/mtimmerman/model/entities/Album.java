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
 * Created by maarten on 29.12.14.
 */
@Entity
@SequenceGenerator(
        name = "album_id_seq",
        sequenceName = "album_id_seq"
)
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "album_id_seq")
    @AccessType(AccessType.Type.PROPERTY)
    private Integer id;

    private String plexName;

    private String plexKey;

    private String lastFMmbId;

    @NotNull
    private String lastFMName;

    @ManyToOne(optional = false)
    private Artist artist;

    public void setPlexName(String plexName) {
        this.plexName = plexName;
    }

    public void setPlexKey(String plexKey) {
        this.plexKey = plexKey;
    }

    public void setLastFMmbId(String lastFMmbId) {
        this.lastFMmbId = lastFMmbId;
    }

    public void setLastFMName(String lastFMName) {
        this.lastFMName = lastFMName;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public String getPlexKey() {
        return plexKey;
    }
}
