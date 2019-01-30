package space.infinity.app.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import space.infinity.app.dao.AstronautDao;
import space.infinity.app.dao.CosmicDao;
import space.infinity.app.dao.GalaxyDao;
import space.infinity.app.dao.ImageItemDao;
import space.infinity.app.dao.LaunchSiteDao;
import space.infinity.app.dao.LaunchSiteLocationDao;
import space.infinity.app.dao.MoonDao;
import space.infinity.app.dao.PlanetDao;
import space.infinity.app.dao.RocketDao;
import space.infinity.app.dao.RocketStageDao;
import space.infinity.app.dao.SpaceFactDao;
import space.infinity.app.dao.StarDao;
import space.infinity.app.models.Astronaut;
import space.infinity.app.models.Galaxy;
import space.infinity.app.models.ImageItem;
import space.infinity.app.models.LaunchSite;
import space.infinity.app.models.LaunchSiteLocation;
import space.infinity.app.models.Moon;
import space.infinity.app.models.Planet;
import space.infinity.app.models.Rocket;
import space.infinity.app.models.RocketStage;
import space.infinity.app.models.SpaceFact;
import space.infinity.app.models.Star;

@Database(entities = {Galaxy.class, ImageItem.class, Moon.class,
        Planet.class, SpaceFact.class, Star.class, Astronaut.class,
        Rocket.class, RocketStage.class, LaunchSite.class, LaunchSiteLocation.class},
        version = 8, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract GalaxyDao getGalaxyDao();
    public abstract ImageItemDao getImageItemDao();
    public abstract MoonDao getMoonDao();
    public abstract PlanetDao getPlanetDao();
    public abstract SpaceFactDao getSpaceFactDao();
    public abstract StarDao getStarDao();
    public abstract CosmicDao getCosmicDao();
    public abstract AstronautDao getAstronautDao();
    public abstract RocketDao getRocketDao();
    public abstract RocketStageDao getRocketStageDao();
    public abstract LaunchSiteDao getLaunchSiteDao();
    public abstract LaunchSiteLocationDao getLaunchSiteLocationDao();

}
