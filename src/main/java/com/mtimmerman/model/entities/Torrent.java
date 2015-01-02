package com.mtimmerman.model.entities;

import org.springframework.data.annotation.AccessType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by maarten on 30.12.14.
 */
@Entity
@SequenceGenerator(
        name = "torrent_id_seq",
        sequenceName = "torrent_id_seq"
)
@Table(
        indexes = {
                @Index(
                        name="torrent_name_index",
                        unique = false,
                        columnList = "name"
                ),
                @Index(
                        name="torrent_torrent_id_index",
                        unique=true,
                        columnList = "torrentId"
                ),
                @Index(
                        name="torrent_type_index",
                        unique=false,
                        columnList = "type"
                )
        }
)
public class Torrent {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "torrent_id_seq")
    @AccessType(AccessType.Type.PROPERTY)
    private Integer id;

    private String torrentId;

    @NotNull
    @Size(max = 2048)
    private String name;

    @NotNull
    private String type;

    @NotNull
    @Size(max = 2048)
    private String url;

    @NotNull
    @Size(max = 2048)
    private String torrentUrl;

    public void setTorrentId(String torrentId) {
        this.torrentId = torrentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTorrentUrl(String torrentUrl) {
        this.torrentUrl = torrentUrl;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getTorrentUrl() {
        return torrentUrl;
    }

    public String getType() {
        return type;
    }
}
