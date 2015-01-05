package com.mtimmerman.administration;

import com.mtimmerman.model.entities.Album;
import org.lightadmin.api.config.AdministrationConfiguration;
import org.lightadmin.api.config.builder.EntityMetadataConfigurationUnitBuilder;
import org.lightadmin.api.config.unit.EntityMetadataConfigurationUnit;
import org.lightadmin.api.config.utils.EntityNameExtractor;

/**
 * Created by maarten on 02.01.15.
 */
public class AlbumAdministration extends AdministrationConfiguration<Album> {
    public EntityMetadataConfigurationUnit configuration(EntityMetadataConfigurationUnitBuilder entityMetadataConfigurationUnitBuilder) {
        return entityMetadataConfigurationUnitBuilder.nameExtractor(
                albumExtractor()
        ).build();
    }

    private static EntityNameExtractor<Album> albumExtractor() {
        return new EntityNameExtractor<Album>() {
            @Override
            public String apply(Album album) {
                return album.getLastFMName();
            }
        };
    }
}
