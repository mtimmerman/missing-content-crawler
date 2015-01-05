package com.mtimmerman.administration;

import com.mtimmerman.model.entities.Season;
import org.lightadmin.api.config.AdministrationConfiguration;
import org.lightadmin.api.config.builder.EntityMetadataConfigurationUnitBuilder;
import org.lightadmin.api.config.unit.EntityMetadataConfigurationUnit;
import org.lightadmin.api.config.utils.EntityNameExtractor;

/**
 * Created by maarten on 02.01.15.
 */
public class SeasonAdministration extends AdministrationConfiguration<Season> {
    public EntityMetadataConfigurationUnit configuration(EntityMetadataConfigurationUnitBuilder entityMetadataConfigurationUnitBuilder) {
        return entityMetadataConfigurationUnitBuilder.nameExtractor(
                seasonExtractor()
        ).build();
    }

    private static EntityNameExtractor<Season> seasonExtractor() {
        return new EntityNameExtractor<Season>() {
            @Override
            public String apply(Season season) {
                return String.format(
                        "%s - Season %s",
                        season.getTvShow().getTheTVDbName(),
                        season.getTheTVDbSeasonNumber()
                );
            }
        };
    }
}
