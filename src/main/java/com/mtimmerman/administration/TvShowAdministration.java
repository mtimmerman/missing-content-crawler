package com.mtimmerman.administration;

import com.mtimmerman.model.entities.TvShow;
import org.lightadmin.api.config.AdministrationConfiguration;
import org.lightadmin.api.config.builder.EntityMetadataConfigurationUnitBuilder;
import org.lightadmin.api.config.unit.EntityMetadataConfigurationUnit;
import org.lightadmin.api.config.utils.EntityNameExtractor;

/**
 * Created by maarten on 02.01.15.
 */
public class TvShowAdministration extends AdministrationConfiguration<TvShow> {
    public EntityMetadataConfigurationUnit configuration(EntityMetadataConfigurationUnitBuilder entityMetadataConfigurationUnitBuilder) {
        return entityMetadataConfigurationUnitBuilder.nameExtractor(
                tvShowExtractor()
        ).build();
    }

    private static EntityNameExtractor<TvShow> tvShowExtractor() {
        return new EntityNameExtractor<TvShow>() {
            @Override
            public String apply(TvShow tvShow) {
                return tvShow.getTheTVDbName();
            }
        };
    }
}
