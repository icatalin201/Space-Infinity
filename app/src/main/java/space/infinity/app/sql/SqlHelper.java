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
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "space_infinity.db";

    private static final String SQL_CREATE_IMAGE_DATA =
            "CREATE TABLE " + SqlStructure.SqlData.IMAGE_DATA_TABLE + " (" +
                    SqlStructure.SqlData._ID + " INTEGER PRIMARY KEY," +
                    SqlStructure.SqlData.date_column + " TEXT," +
                    SqlStructure.SqlData.hdurl_column + " TEXT," +
                    SqlStructure.SqlData.url_column + " TEXT," +
                    SqlStructure.SqlData.title_column + " TEXT," +
                    SqlStructure.SqlData.description_column + " TEXT," +
                    SqlStructure.SqlData.author_column + " TEXT)";

    private static final String SQL_CREATE_FACTS =
            "CREATE TABLE " + SqlStructure.SqlData.FACTS_TABLE + " (" +
                    SqlStructure.SqlData._ID + " INTEGER PRIMARY KEY," +
                    SqlStructure.SqlData.fact_name + " TEXT," +
                    SqlStructure.SqlData.is_fact_fav + " TEXT)";

    private static final String SQL_CREATE_FAVS_IMAGE =
            "CREATE TABLE " + SqlStructure.SqlData.FAVS_IMAGE_TABLE + " (" +
                    SqlStructure.SqlData._ID + " INTEGER PRIMARY KEY," +
                    SqlStructure.SqlData.fav_type + " TEXT," +
                    SqlStructure.SqlData.fav_url + " TEXT," +
                    SqlStructure.SqlData.fav_hdulr + " TEXT," +
                    SqlStructure.SqlData.fav_title + " TEXT," +
                    SqlStructure.SqlData.fav_description + " TEXT)";

    private static final String SQL_CREATE_WIKI_PLANETS =
            "CREATE TABLE " + SqlStructure.SqlData.WIKI_PLANETS_TABLE + " (" +
                    SqlStructure.SqlData._ID + " INTEGER PRIMARY KEY," +
                    SqlStructure.SqlData.wiki_planet_name + " TEXT," +
                    SqlStructure.SqlData.wiki_planet_description + " TEXT," +
                    SqlStructure.SqlData.wiki_planet_image + " TEXT," +
                    SqlStructure.SqlData.wiki_planet_diameter + " TEXT," +
                    SqlStructure.SqlData.wiki_planet_mass + " TEXT," +
                    SqlStructure.SqlData.wiki_planet_moons + " TEXT," +
                    SqlStructure.SqlData.wiki_planet_orbitDistance + " TEXT," +
                    SqlStructure.SqlData.wiki_planet_orbitPeriod + " TEXT," +
                    SqlStructure.SqlData.wiki_planet_surfaceTemperature + " TEXT," +
                    SqlStructure.SqlData.wiki_planet_firstRecord + " TEXT," +
                    SqlStructure.SqlData.wiki_planet_recordedBy + " TEXT," +
                    SqlStructure.SqlData.wiki_planet_quickFacts + " TEXT)";

    private static final String SQL_CREATE_WIKI_OTHERS =
            "CREATE TABLE " + SqlStructure.SqlData.WIKI_OTHERS_TABLE + " (" +
                    SqlStructure.SqlData._ID + " INTEGER PRIMARY KEY," +
                    SqlStructure.SqlData.wiki_name + " TEXT," +
                    SqlStructure.SqlData.wiki_description + " TEXT," +
                    SqlStructure.SqlData.wiki_image + " TEXT," +
                    SqlStructure.SqlData.wiki_age + " TEXT," +
                    SqlStructure.SqlData.wiki_type + " TEXT," +
                    SqlStructure.SqlData.wiki_diameter + " TEXT," +
                    SqlStructure.SqlData.wiki_mass + " TEXT," +
                    SqlStructure.SqlData.wiki_surface_temperature + " TEXT," +
                    SqlStructure.SqlData.wiki_info + " TEXT," +
                    SqlStructure.SqlData.wiki_other_info + " TEXT)";

    private static final String SQL_CREATE_WIKI_GALAXIES =
            "CREATE TABLE " + SqlStructure.SqlData.WIKI_GALAXY_TABLE + " (" +
                    SqlStructure.SqlData._ID + " INTEGER PRIMARY KEY," +
                    SqlStructure.SqlData.wiki_galaxy_name + " TEXT," +
                    SqlStructure.SqlData.wiki_galaxy_description + " TEXT," +
                    SqlStructure.SqlData.wiki_galaxy_image + " TEXT," +
                    SqlStructure.SqlData.wiki_galaxy_designation + " TEXT," +
                    SqlStructure.SqlData.wiki_galaxy_diameter + " TEXT," +
                    SqlStructure.SqlData.wiki_galaxy_distance + " TEXT," +
                    SqlStructure.SqlData.wiki_galaxy_mass + " TEXT," +
                    SqlStructure.SqlData.wiki_galaxy_constellation + " TEXT," +
                    SqlStructure.SqlData.wiki_galaxy_group + " TEXT," +
                    SqlStructure.SqlData.wiki_galaxy_numberOfStars + " TEXT," +
                    SqlStructure.SqlData.wiki_galaxy_type + " TEXT," +
                    SqlStructure.SqlData.wiki_galaxy_quickFacts + " TEXT)";

    private static final String SQL_CREATE_WIKI_MOONS =
            "CREATE TABLE " + SqlStructure.SqlData.WIKI_MOONS_TABLE + " (" +
                    SqlStructure.SqlData._ID + " INTEGER PRIMARY KEY," +
                    SqlStructure.SqlData.wiki_moons_name + " TEXT," +
                    SqlStructure.SqlData.wiki_moons_description + " TEXT," +
                    SqlStructure.SqlData.wiki_moons_image + " TEXT," +
                    SqlStructure.SqlData.wiki_moons_diameter + " TEXT," +
                    SqlStructure.SqlData.wiki_moons_mass + " TEXT," +
                    SqlStructure.SqlData.wiki_moons_orbits + " TEXT," +
                    SqlStructure.SqlData.wiki_moons_orbitDistance + " TEXT," +
                    SqlStructure.SqlData.wiki_moons_orbitPeriod + " TEXT," +
                    SqlStructure.SqlData.wiki_moons_surfaceTemperature + " TEXT," +
                    SqlStructure.SqlData.wiki_moons_firstRecord + " TEXT," +
                    SqlStructure.SqlData.wiki_moons_recordedBy + " TEXT," +
                    SqlStructure.SqlData.wiki_moons_quickFacts + " TEXT)";

    private static final String SQL_DELETE_IMAGE_DATA =
            "DROP TABLE IF EXISTS " + SqlStructure.SqlData.IMAGE_DATA_TABLE;

    private static final String SQL_DELETE_FAVS_IMAGE =
            "DROP TABLE IF EXISTS " + SqlStructure.SqlData.FAVS_IMAGE_TABLE;

    private static final String SQL_DELETE_FACTS =
            "DROP TABLE IF EXISTS " + SqlStructure.SqlData.FACTS_TABLE;

    private static final String SQL_DELETE_WIKI_PLANETS =
            "DROP TABLE IF EXISTS " + SqlStructure.SqlData.WIKI_PLANETS_TABLE;

    private static final String SQL_DELETE_WIKI_GALAXY =
            "DROP TABLE IF EXISTS " + SqlStructure.SqlData.WIKI_GALAXY_TABLE;

    private static final String SQL_DELETE_WIKI_MOONS =
            "DROP TABLE IF EXISTS " + SqlStructure.SqlData.WIKI_MOONS_TABLE;

    private static final String SQL_DELETE_WIKI_OTHERS =
            "DROP TABLE IF EXISTS " + SqlStructure.SqlData.WIKI_OTHERS_TABLE;

    public SqlHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_IMAGE_DATA);
        sqLiteDatabase.execSQL(SQL_CREATE_FAVS_IMAGE);
        sqLiteDatabase.execSQL(SQL_CREATE_FACTS);
        sqLiteDatabase.execSQL(SQL_CREATE_WIKI_PLANETS);
        sqLiteDatabase.execSQL(SQL_CREATE_WIKI_MOONS);
        sqLiteDatabase.execSQL(SQL_CREATE_WIKI_GALAXIES);
        sqLiteDatabase.execSQL(SQL_CREATE_WIKI_OTHERS);
        Log.i(TAG, "onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_IMAGE_DATA);
        sqLiteDatabase.execSQL(SQL_DELETE_FAVS_IMAGE);
        sqLiteDatabase.execSQL(SQL_DELETE_FACTS);
        sqLiteDatabase.execSQL(SQL_DELETE_WIKI_PLANETS);
        sqLiteDatabase.execSQL(SQL_DELETE_WIKI_GALAXY);
        sqLiteDatabase.execSQL(SQL_DELETE_WIKI_MOONS);
        sqLiteDatabase.execSQL(SQL_DELETE_WIKI_OTHERS);
        Log.i(TAG, "onUpgrade");
        onCreate(sqLiteDatabase);
    }
}
