package com.mtimmerman.administration;

import com.mtimmerman.model.entities.Artist;
import org.lightadmin.api.config.AdministrationConfiguration;
import org.lightadmin.api.config.builder.EntityMetadataConfigurationUnitBuilder;
import org.lightadmin.api.config.unit.EntityMetadataConfigurationUnit;
import org.lightadmin.api.config.utils.EntityNameExtractor;

/**
 * Created by maarten on 02.01.15.
 */
public class ArtistAdministration extends AdministrationConfiguration<Artist> {
    public EntityMetadataConfigurationUnit configuration(EntityMetadataConfigurationUnitBuilder entityMetadataConfigurationUnitBuilder) {
        return entityMetadataConfigurationUnitBuilder.nameExtractor(
                artistExtractor()
        ).build();
    }

    private static EntityNameExtractor<Artist> artistExtractor() {
        return new EntityNameExtractor<Artist>() {
            @Override
            public String apply(Artist artist) {
                return artist.getLastFMName();
            }
        };
    }
}
