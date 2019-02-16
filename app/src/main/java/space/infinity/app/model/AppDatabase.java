package space.infinity.app.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import space.infinity.app.model.dao.AstronautDao;
import space.infinity.app.model.dao.GalaxyDao;
import space.infinity.app.model.dao.ImageItemDao;
import space.infinity.app.model.dao.LaunchSiteDao;
import space.infinity.app.model.dao.MoonDao;
import space.infinity.app.model.dao.PlanetDao;
import space.infinity.app.model.dao.SpaceFactDao;
import space.infinity.app.model.dao.StarDao;
import space.infinity.app.model.dao.VoyagerDao;
import space.infinity.app.model.entity.Astronaut;
import space.infinity.app.model.entity.Galaxy;
import space.infinity.app.model.entity.ImageItem;
import space.infinity.app.model.entity.LaunchSite;
import space.infinity.app.model.entity.Moon;
import space.infinity.app.model.entity.Planet;
import space.infinity.app.model.entity.SpaceFact;
import space.infinity.app.model.entity.Star;
import space.infinity.app.model.entity.Voyager;

@Database(entities = {Galaxy.class, ImageItem.class, Moon.class,
        Planet.class, SpaceFact.class, Star.class, Astronaut.class,
        LaunchSite.class, Voyager.class},
        version = 10, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase appDatabase;

    public abstract GalaxyDao getGalaxyDao();
    public abstract ImageItemDao getImageItemDao();
    public abstract MoonDao getMoonDao();
    public abstract PlanetDao getPlanetDao();
    public abstract SpaceFactDao getSpaceFactDao();
    public abstract StarDao getStarDao();
    public abstract AstronautDao getAstronautDao();
    public abstract LaunchSiteDao getLaunchSiteDao();
    public abstract VoyagerDao getVoyagerDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (appDatabase == null) {
            appDatabase = Room
                    .databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "infinity")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return appDatabase;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };



}
