package com.mtimmerman.administration;

import com.mtimmerman.model.entities.Episode;
import org.lightadmin.api.config.AdministrationConfiguration;
import org.lightadmin.api.config.builder.EntityMetadataConfigurationUnitBuilder;
import org.lightadmin.api.config.unit.EntityMetadataConfigurationUnit;
import org.lightadmin.api.config.utils.EntityNameExtractor;

/**
 * Created by maarten on 02.01.15.
 */
public class EpisodeAdministration extends AdministrationConfiguration<Episode> {
    public EntityMetadataConfigurationUnit configuration(EntityMetadataConfigurationUnitBuilder entityMetadataConfigurationUnitBuilder) {
        return entityMetadataConfigurationUnitBuilder.nameExtractor(
                episodeExtractor()
        ).build();
    }

    private static EntityNameExtractor<Episode> episodeExtractor() {
        return new EntityNameExtractor<Episode>() {
            @Override
            public String apply(Episode episode) {
                return String.format(
                        "%s - %s",
                        episode.getSearchName(),
                        episode.getTheTVDbEpisodeName()
                );
            }
        };
    }
}
