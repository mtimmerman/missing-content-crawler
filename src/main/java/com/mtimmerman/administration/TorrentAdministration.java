package com.mtimmerman.administration;

import com.mtimmerman.model.entities.Torrent;
import org.lightadmin.api.config.AdministrationConfiguration;
import org.lightadmin.api.config.builder.EntityMetadataConfigurationUnitBuilder;
import org.lightadmin.api.config.unit.EntityMetadataConfigurationUnit;
import org.lightadmin.api.config.utils.EntityNameExtractor;

/**
 * Created by maarten on 02.01.15.
 */
public class TorrentAdministration extends AdministrationConfiguration<Torrent> {
    public EntityMetadataConfigurationUnit configuration(EntityMetadataConfigurationUnitBuilder entityMetadataConfigurationUnitBuilder) {
        return entityMetadataConfigurationUnitBuilder.nameExtractor(
                torrentExtractor()
        ).build();
    }

    private static EntityNameExtractor<Torrent> torrentExtractor() {
        return new EntityNameExtractor<Torrent>() {
            @Override
            public String apply(Torrent torrent) {
                return torrent.getName();
            }
        };
    }
}
