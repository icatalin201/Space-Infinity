package space.infinity.app.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import space.infinity.app.models.apod.APOD;

/**
 * Created by icatalin on 11.02.2018.
 */

public class SqlService {

    public static void insertImageDataIntoSql(Context context, APOD apodObject) {
        if (apodObject.getMedia_type().equals("movie")) return;
        SqlHelper sql = new SqlHelper(context);
        SQLiteDatabase sqLiteDatabase = sql.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SqlStructure.SqlData.date_column, apodObject.getDate());
        cv.put(SqlStructure.SqlData.hdurl_column, apodObject.getHdurl());
        cv.put(SqlStructure.SqlData.url_column, apodObject.getUrl());
        cv.put(SqlStructure.SqlData.title_column, apodObject.getTitle());
        cv.put(SqlStructure.SqlData.author_column, apodObject.getCopyright());
        sqLiteDatabase.insert(SqlStructure.SqlData.IMAGE_DATA_TABLE, null, cv);
        sqLiteDatabase.close();
    }

    public void verifyContentForDay() {

    }

    public void checkContent() {

    }

}
