package space.infinity.app.sql;

import android.provider.BaseColumns;

/**
 * Created by icatalin on 11.02.2018.
 */

public class SqlStructure {

    private SqlStructure() {}

    public static class SqlData implements BaseColumns {

        public static final String IMAGE_DATA_TABLE = "image_data";
        public static final String date_column = "date";
        public static final String hdurl_column = "hd_url";
        public static final String url_column = "url";
        public static final String title_column = "title";
        public static final String author_column = "author";

    }
}
