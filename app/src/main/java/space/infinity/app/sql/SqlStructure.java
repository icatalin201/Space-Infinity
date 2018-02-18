package space.infinity.app.sql;

import android.provider.BaseColumns;

/**
 * Created by icatalin on 11.02.2018.
 */

public class SqlStructure {

    private SqlStructure() {}

    public static class SqlData implements BaseColumns {

        public static final String IMAGE_DATA_TABLE = "image_data";
        public static final String date_column = "image_date";
        public static final String hdurl_column = "image_hd_url";
        public static final String url_column = "image_url";
        public static final String title_column = "image_title";
        public static final String author_column = "image_author";
        public static final String description_column = "image_description";

        public static final String WIKI_PLANETS_TABLE = "wiki_planets";
        public static final String wiki_planet_name = "name";
        public static final String wiki_planet_image = "image";
        public static final String wiki_planet_description = "description";
        public static final String wiki_planet_diameter = "diameter";
        public static final String wiki_planet_mass = "mass";
        public static final String wiki_planet_moons = "moons";
        public static final String wiki_planet_orbitDistance = "orbit_distance";
        public static final String wiki_planet_orbitPeriod = "orbit_period";
        public static final String wiki_planet_surfaceTemperature = "surface_temperature";
        public static final String wiki_planet_firstRecord = "first_record";
        public static final String wiki_planet_recordedBy = "recorded_by";
        public static final String wiki_planet_quickFacts = "quick_facts";

        public static final String WIKI_GALAXY_TABLE = "wiki_galaxies";
        public static final String wiki_galaxy_name = "name";
        public static final String wiki_galaxy_image = "image";
        public static final String wiki_galaxy_description = "description";
        public static final String wiki_galaxy_designation = "designation";
        public static final String wiki_galaxy_type = "type";
        public static final String wiki_galaxy_diameter = "diameter";
        public static final String wiki_galaxy_distance = "distance";
        public static final String wiki_galaxy_mass = "mass";
        public static final String wiki_galaxy_numberOfStars = "number_of_stars";
        public static final String wiki_galaxy_constellation = "constellation";
        public static final String wiki_galaxy_group = "galaxy_group";
        public static final String wiki_galaxy_quickFacts = "quick_facts";

        public static final String WIKI_MOONS_TABLE = "wiki_moons";
        public static final String wiki_moons_name = "name";
        public static final String wiki_moons_image = "image";
        public static final String wiki_moons_description = "description";
        public static final String wiki_moons_diameter = "diameter";
        public static final String wiki_moons_mass = "mass";
        public static final String wiki_moons_orbits = "orbits";
        public static final String wiki_moons_orbitDistance = "orbit_distance";
        public static final String wiki_moons_orbitPeriod = "orbit_period";
        public static final String wiki_moons_surfaceTemperature = "surface_temperature";
        public static final String wiki_moons_firstRecord = "first_record";
        public static final String wiki_moons_recordedBy = "recorded_by";
        public static final String wiki_moons_quickFacts = "quick_facts";

        public static final String WIKI_OTHERS_TABLE = "wiki_others";
        public static final String wiki_name = "name";
        public static final String wiki_description = "description";
        public static final String wiki_age = "age";
        public static final String wiki_type = "type";
        public static final String wiki_diameter = "diameter";
        public static final String wiki_mass = "mass";
        public static final String wiki_image = "image";
        public static final String wiki_surface_temperature = "surface_temperature";
        public static final String wiki_info = "detailed_info";
        public static final String wiki_other_info = "other_info";

        public static final String FAVS_IMAGE_TABLE = "image_favorites";
        public static final String fav_type = "type";
        public static final String fav_url = "url";
        public static final String fav_hdulr = "hdurl";
        public static final String fav_title = "title";

        public static final String FACTS_TABLE = "facts";
        public static final String fact_name = "name";
        public static final String is_fact_fav = "is_fav";


    }
}
