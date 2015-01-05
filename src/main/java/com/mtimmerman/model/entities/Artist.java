package com.mtimmerman.model.entities;

import org.springframework.data.annotation.AccessType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

/**
 * Created by maarten on 29.12.14.
 */
@Entity
@SequenceGenerator(
        name = "artist_id_seq",
        sequenceName = "artist_id_seq"
)
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "artist_id_seq")
    @AccessType(AccessType.Type.PROPERTY)
    private Integer id;

    @NotNull
    private String plexKey;

    @NotNull
    private String plexName;

    private String lastFMName;

    public void setPlexKey(String plexKey) {
        this.plexKey = plexKey;
    }

    public void setPlexName(String plexName) {
        this.plexName = plexName;
    }

    public void setLastFMName(String lastFMName) {
        this.lastFMName = lastFMName;
    }

    public String getLastFMName() {
        return lastFMName;
    }

    public Integer getId() {
        return id;
    }

    public String getPlexKey() {
        return plexKey;
    }

    public String getPlexName() {
        return plexName;
    }
}
