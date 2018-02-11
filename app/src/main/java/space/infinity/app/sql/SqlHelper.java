package space.infinity.app.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by icatalin on 11.02.2018.
 */

public class SqlHelper extends SQLiteOpenHelper{

    private static final String TAG = "SQL_HELPER";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "space_infinity.db";

    private static final String SQL_CREATE_IMAGE_DATA =
            "CREATE TABLE " + SqlStructure.SqlData.IMAGE_DATA_TABLE + " (" +
                    SqlStructure.SqlData._ID + " INTEGER PRIMARY KEY," +
                    SqlStructure.SqlData.date_column + " TEXT," +
                    SqlStructure.SqlData.hdurl_column + " TEXT," +
                    SqlStructure.SqlData.url_column + " TEXT," +
                    SqlStructure.SqlData.title_column + "TEXT," +
                    SqlStructure.SqlData.author_column + " TEXT)";

    private static final String SQL_DELETE_IMAGE_DATA =
            "DROP TABLE IF EXISTS " + SqlStructure.SqlData.IMAGE_DATA_TABLE;

    public SqlHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_IMAGE_DATA);
        Log.i(TAG, "onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_IMAGE_DATA);
        Log.i(TAG, "onUpgrade");
    }
}
