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

        public static final String WIKI_TABLE = "wiki";

        public static final String FACTS_TABLE = "facts";

        public static final String FAVS_IMAGE_TABLE = "image_favorites";
        public static final String fav_type = "type";
        public static final String fav_url = "url";
        public static final String fav_hdulr = "hdurl";
        public static final String fav_title = "title";

        public static final String FAVS_FACTS_TABLE = "facts_favorites";
        public static final String fav_name = "name";


    }
}
