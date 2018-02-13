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

    }
}
