package space.infinity.app.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import space.infinity.app.models.apod.APOD;

/**
 * Created by icatalin on 11.02.2018.
 */

public class SqlService {

    public static List<APOD> getImageDataList(Context context) {
        SqlHelper sqlHelper = new SqlHelper(context);
        SQLiteDatabase sqLiteDatabase = sqlHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(SqlStructure.SqlData.IMAGE_DATA_TABLE, null,
                null, null, null, null, null ,null);
        List<APOD> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            String date = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.date_column));
            String hdurl = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.hdurl_column));
            String url = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.url_column));
            String title = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.title_column));
            String author = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.author_column));
            list.add(new APOD(date, null, url, hdurl, null, title, author));
        }
        cursor.close();
        sqLiteDatabase.close();
        Log.i("db_action", "read");
        Log.i("size", Integer.toString(list.size()));
        return list;
    }

    public static APOD getApod(Context context, String date) {
        SqlHelper sqlHelper = new SqlHelper(context);
        SQLiteDatabase sqLiteDatabase = sqlHelper.getReadableDatabase();
        String[] whereArgs = {date};
        Cursor cursor = sqLiteDatabase.query(SqlStructure.SqlData.IMAGE_DATA_TABLE, null,
                SqlStructure.SqlData.date_column + " = ? ", whereArgs,
                null, null, null, null);
        APOD apod = new APOD();
        if (cursor.getCount() == 1) {
            cursor.moveToFirst();
            apod.setDate(cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.date_column)));
            apod.setCopyright(cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.author_column)));
            apod.setExplanation(cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.description_column)));
            apod.setUrl(cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.url_column)));
            apod.setHdurl(cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.hdurl_column)));
            apod.setTitle(cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.title_column)));
        }
        cursor.close();
        sqLiteDatabase.close();
        Log.i("db_action", "read");
        return apod;
    }

    public static void insertImageDataIntoSql(Context context, APOD apodObject) {
        if (apodObject.getMedia_type().equals("movie")) return;
        SqlHelper sql = new SqlHelper(context);
        SQLiteDatabase sqLiteDatabase = sql.getWritableDatabase();
        ContentValues cv = new ContentValues();
        if (!existContentForToday(context, apodObject)) {
            cv.put(SqlStructure.SqlData.date_column, apodObject.getDate());
            cv.put(SqlStructure.SqlData.hdurl_column, apodObject.getHdurl());
            cv.put(SqlStructure.SqlData.url_column, apodObject.getUrl());
            cv.put(SqlStructure.SqlData.title_column, apodObject.getTitle());
            cv.put(SqlStructure.SqlData.description_column, apodObject.getExplanation());
            String author = checkContent(apodObject);
            cv.put(SqlStructure.SqlData.author_column, author);
            sqLiteDatabase.insert(SqlStructure.SqlData.IMAGE_DATA_TABLE, null, cv);
            Log.i("db_action", "insert");
        }
        sqLiteDatabase.close();
    }

    private static boolean existContentForToday(Context context, APOD apodObject) {
        boolean exist = false;
        SqlHelper sqlHelper = new SqlHelper(context);
        SQLiteDatabase sqLiteDatabase = sqlHelper.getReadableDatabase();
        String date = apodObject.getDate();
        String[] whereArgs = {date};
        Cursor cursor = sqLiteDatabase.query(SqlStructure.SqlData.IMAGE_DATA_TABLE, null,
                SqlStructure.SqlData.date_column + " = ? ", whereArgs, null, null, null);
        if (cursor.getCount() == 1) exist = true;
        cursor.close();
        sqLiteDatabase.close();
        return exist;
    }

    private static String checkContent(APOD apod) {
        String author = "";
        if (apod.getCopyright() == null) {
            author = " ";
        }
        return author;
    }

}
