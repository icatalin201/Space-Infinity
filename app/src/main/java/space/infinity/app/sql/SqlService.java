package space.infinity.app.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import space.infinity.app.models.apod.APOD;
import space.infinity.app.models.encyclopedia.Galaxy;
import space.infinity.app.models.encyclopedia.Moon;
import space.infinity.app.models.encyclopedia.Other;
import space.infinity.app.models.encyclopedia.Planet;
import space.infinity.app.models.facts.SpaceFact;

/**
 * Created by icatalin on 11.02.2018.
 */

public class SqlService {

    public static List<SpaceFact> getFavoriteFacts(Context context) {
        List<SpaceFact> facts = new ArrayList<>();
        SqlHelper sqlHelper = new SqlHelper(context);
        SQLiteDatabase database = sqlHelper.getReadableDatabase();
        String[] where = {"yes"};
        Cursor cursor = database.query(SqlStructure.SqlData.FACTS_TABLE, null,
                SqlStructure.SqlData.is_fact_fav + " = ?", where,
                null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(SqlStructure.SqlData._ID));
            String name = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.fact_name));
            String isFav = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.is_fact_fav));
            facts.add(new SpaceFact(id, name, isFav));
        }
        cursor.close();
        database.close();
        return facts;
    }

    public static void handleImageFavs(Context context, String action, String title, String url,
                                       String hdurl, String description) {
        SqlHelper sqlHelper = new SqlHelper(context);
        SQLiteDatabase database = sqlHelper.getWritableDatabase();
        switch (action) {
            case "add":
                if (!isImageFav(context, title)) {
                    ContentValues cv = new ContentValues();
                    cv.put(SqlStructure.SqlData.fav_title, title);
                    cv.put(SqlStructure.SqlData.fav_type, "image");
                    cv.put(SqlStructure.SqlData.fav_url, url);
                    cv.put(SqlStructure.SqlData.fav_hdulr, hdurl);
                    cv.put(SqlStructure.SqlData.fav_description, description);
                    database.insert(SqlStructure.SqlData.FAVS_IMAGE_TABLE, null, cv);
                    Log.i("add", "image favorite");
                }
                break;
            case "remove":
                if (isImageFav(context, title)) {
                    String[] where = { title };
                    database.delete(SqlStructure.SqlData.FAVS_IMAGE_TABLE,
                            SqlStructure.SqlData.fav_title + " = ? ", where);
                    Log.i("remove", "image favorite");
                }
                break;
        }
        database.close();
    }

    private static boolean isImageFav(Context context, String title) {
        boolean check = false;
        SqlHelper sqlHelper = new SqlHelper(context);
        SQLiteDatabase database = sqlHelper.getReadableDatabase();
        String[] whereArgs = { title };
        Cursor cursor = database.query(SqlStructure.SqlData.FAVS_IMAGE_TABLE, null,
                SqlStructure.SqlData.fav_title + " = ?", whereArgs,
                null, null, null, null);
        if (cursor.getCount() == 1) {
            check = true;
        }
        cursor.close();
        database.close();
        return check;
    }

    public static List<Planet> getPlanets(Context context) {
        SqlHelper sqlHelper = new SqlHelper(context);
        SQLiteDatabase database = sqlHelper.getReadableDatabase();
        Cursor cursor = database.query(SqlStructure.SqlData.WIKI_PLANETS_TABLE, null,
                null, null, null, null, null, null);
        List<Planet> planets = new ArrayList<>();
        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(cursor.getColumnIndex(SqlStructure.SqlData._ID));
            String name = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_planet_name));
            String description = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_planet_description));
            String image = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_planet_image));
            String diameter = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_planet_diameter));
            String mass = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_planet_mass));
            String moons = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_planet_moons));
            String orbitDistance = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_planet_orbitDistance));
            String orbitPeriod = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_planet_orbitPeriod));
            String surfaceTemperature = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_planet_surfaceTemperature));
            String firstRecord = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_planet_firstRecord));
            String recordedBy = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_planet_recordedBy));
            String facts = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_planet_quickFacts));
            planets.add(new Planet(id, name, description, image, diameter, mass, moons, orbitDistance,
                    orbitPeriod, surfaceTemperature, firstRecord, recordedBy, facts));
        }
        cursor.close();
        database.close();
        return planets;
    }

    public static List<Moon> getMoons(Context context) {
        SqlHelper sqlHelper = new SqlHelper(context);
        SQLiteDatabase database = sqlHelper.getReadableDatabase();
        Cursor cursor = database.query(SqlStructure.SqlData.WIKI_MOONS_TABLE, null,
                null, null, null, null, null, null);
        List<Moon> moonList = new ArrayList<>();
        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(cursor.getColumnIndex(SqlStructure.SqlData._ID));
            String name = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_moons_name));
            String description = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_moons_description));
            String image = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_moons_image));
            String diameter = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_moons_diameter));
            String mass = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_moons_mass));
            String orbits = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_moons_orbits));
            String orbitDistance = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_moons_orbitDistance));
            String orbitPeriod = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_moons_orbitPeriod));
            String surfaceTemperature = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_moons_surfaceTemperature));
            String firstRecord = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_moons_firstRecord));
            String recordedBy = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_moons_recordedBy));
            String facts = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_moons_quickFacts));
            moonList.add(new Moon(id, name, description, image, diameter, mass, orbits, orbitDistance,
                    orbitPeriod, surfaceTemperature, firstRecord, recordedBy, facts));
        }
        cursor.close();
        database.close();
        return moonList;
    }

    public static List<Galaxy> getGalaxies(Context context) {
        SqlHelper sqlHelper = new SqlHelper(context);
        SQLiteDatabase database = sqlHelper.getReadableDatabase();
        Cursor cursor = database.query(SqlStructure.SqlData.WIKI_GALAXY_TABLE, null,
                null, null, null, null, null, null);
        List<Galaxy> galaxies = new ArrayList<>();
        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(cursor.getColumnIndex(SqlStructure.SqlData._ID));
            String name = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_galaxy_name));
            String description = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_galaxy_description));
            String image = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_galaxy_image));
            String diameter = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_galaxy_diameter));
            String mass = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_galaxy_mass));
            String designation = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_galaxy_designation));
            String distance = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_galaxy_distance));
            String constellation = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_galaxy_constellation));
            String galaxyGroup = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_galaxy_group));
            String numberOfStars = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_galaxy_numberOfStars));
            String type = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_galaxy_type));
            String facts = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_galaxy_quickFacts));
            galaxies.add(new Galaxy(id, name, description, image, designation, diameter, distance, mass,
                    constellation, galaxyGroup, numberOfStars, type, facts));
        }
        cursor.close();
        database.close();
        return galaxies;
    }

    public static List<Other> getOthers(Context context) {
        SqlHelper sqlHelper = new SqlHelper(context);
        SQLiteDatabase database = sqlHelper.getReadableDatabase();
        Cursor cursor = database.query(SqlStructure.SqlData.WIKI_OTHERS_TABLE, null,
                null, null, null, null, null, null);
        List<Other> others = new ArrayList<>();
        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(cursor.getColumnIndex(SqlStructure.SqlData._ID));
            String name = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_name));
            String description = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_description));
            String image = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_image));
            String age = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_age));
            String type = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_type));
            String diameter = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_diameter));
            String mass = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_mass));
            String surfaceTemperature = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_surface_temperature));
            String detailedInfo = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_info));
            String otherInfo = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.wiki_other_info));
            others.add(new Other(id, name, description, image, age, type, diameter, mass,
                    surfaceTemperature, detailedInfo, otherInfo));
        }
        cursor.close();
        database.close();
        return others;
    }

    public static List<SpaceFact> getSpaceFactsList(Context context) {
        SqlHelper sqlHelper = new SqlHelper(context);
        SQLiteDatabase sqLiteDatabase = sqlHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(SqlStructure.SqlData.FACTS_TABLE, null,
                null, null, null, null, null, null);
        List<SpaceFact> spaceFacts = new ArrayList<>();
        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(cursor.getColumnIndex(SqlStructure.SqlData._ID));
            String name = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.fact_name));
            String isFav = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.is_fact_fav));
            spaceFacts.add(new SpaceFact(id, name, isFav));
        }
        cursor.close();
        sqLiteDatabase.close();
        Log.i("db_action", "read facts");
        Log.i("size facts list", Integer.toString(spaceFacts.size()));
        return spaceFacts;
    }

    public static void handleFactToFavs(Context context, SpaceFact fact, String action) {
        SqlHelper sqlHelper = new SqlHelper(context);
        SQLiteDatabase database = sqlHelper.getWritableDatabase();
        String[] args = {Integer.toString(fact.getId())};
        switch (action) {
            case "add":
                if (!isFactFav(context, fact)) {
                    ContentValues cv = new ContentValues();
                    cv.put(SqlStructure.SqlData.is_fact_fav, "yes");
                    database.update(SqlStructure.SqlData.FACTS_TABLE, cv,
                            SqlStructure.SqlData._ID + " = ?", args);
                }
                break;
            case "remove":
                if (isFactFav(context, fact)) {
                    ContentValues cv = new ContentValues();
                    cv.put(SqlStructure.SqlData.is_fact_fav, "no");
                    database.update(SqlStructure.SqlData.FACTS_TABLE, cv,
                            SqlStructure.SqlData._ID + " = ?", args);
                }
                break;
        }
        database.close();
    }

    public static boolean isFactFav(Context context, SpaceFact fact) {
        boolean check = false;
        SqlHelper sqlHelper = new SqlHelper(context);
        SQLiteDatabase database = sqlHelper.getReadableDatabase();
        String[] whereArgs = {Integer.toString(fact.getId())};
        Cursor cursor = database.query(SqlStructure.SqlData.FACTS_TABLE, null,
                SqlStructure.SqlData._ID + " = ?", whereArgs,
                null, null, null, null);
        if (cursor.getCount() == 1) {
            cursor.moveToFirst();
            String name = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.fact_name));
            if (fact.getName().equals(name)) {
                String isFav = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.is_fact_fav));
                if (isFav.equals("yes")) {
                    check = true;
                }
                else if (isFav.equals("no")) {
                    check = false;
                }
            }
        }
        cursor.close();
        database.close();
        return check;
    }

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
            String description = cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.description_column));
            list.add(new APOD(date, description, url, hdurl, null, title, author));
        }
        cursor.close();
        sqLiteDatabase.close();
        Log.i("db_action", "read images");
        Log.i("size images list", Integer.toString(list.size()));
        return list;
    }

    public static APOD getApod(Context context) {
        SqlHelper sqlHelper = new SqlHelper(context);
        SQLiteDatabase sqLiteDatabase = sqlHelper.getReadableDatabase();
        //String[] whereArgs = {date};
        Cursor cursor = sqLiteDatabase.query(SqlStructure.SqlData.IMAGE_DATA_TABLE, null,
                null, null, null, null, null, null);
        APOD apod = new APOD();
        if (cursor.moveToNext()) {
            cursor.moveToLast();
            apod.setDate(cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.date_column)));
            apod.setCopyright(cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.author_column)));
            apod.setExplanation(cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.description_column)));
            apod.setUrl(cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.url_column)));
            apod.setHdurl(cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.hdurl_column)));
            apod.setTitle(cursor.getString(cursor.getColumnIndex(SqlStructure.SqlData.title_column)));
        }
        cursor.close();
        sqLiteDatabase.close();
        Log.i("db_action", "read apod");
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
            Log.i("db_action", "insert into images");
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
