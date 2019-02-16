package space.infinity.app.util;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import space.infinity.app.R;
import space.infinity.app.model.AppDatabase;
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
import space.infinity.app.model.entity.CosmicItem;
import space.infinity.app.model.entity.Galaxy;
import space.infinity.app.model.entity.ImageItem;
import space.infinity.app.model.entity.LaunchSite;
import space.infinity.app.model.entity.Moon;
import space.infinity.app.model.entity.Planet;
import space.infinity.app.model.entity.SpaceFact;
import space.infinity.app.model.entity.Star;
import space.infinity.app.model.entity.Voyager;

import static androidx.core.app.NotificationCompat.DEFAULT_SOUND;
import static androidx.core.app.NotificationCompat.DEFAULT_VIBRATE;
import static space.infinity.app.util.Constants.CHANNEL_ID;

public class DownloadService extends IntentService {

    public static final String ACTION_COMPLETE = "space.infinity.app.util.action.COMPLETE";
    private AppDatabase appDatabase;


    public DownloadService() {
        super("DownloadService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appDatabase = AppDatabase.getInstance(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            PendingIntent pendingIntent = PendingIntent.getActivity(this,
                    0, new Intent(), 0);
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Space Infinity")
                    .setContentText("Downloading data. Please wait...")
                    .setSmallIcon(R.drawable.ic_baseline_cloud_download_24px)
                    .setFullScreenIntent(pendingIntent, true)
                    .setDefaults(DEFAULT_SOUND | DEFAULT_VIBRATE)
                    .setAutoCancel(false)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .build();
            startForeground(1, notification);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        appDatabase.clearAllTables();
        JSONObject images = new JSONHandler()
                .makeHttpRequest(Constants.SPACE_INFINITY_API.concat(Constants.SPACE_IMAGES));
        JSONObject galaxies = new JSONHandler()
                .makeHttpRequest(Constants.SPACE_INFINITY_API.concat(Constants.SPACE_GALAXIES));
        JSONObject planets = new JSONHandler()
                .makeHttpRequest(Constants.SPACE_INFINITY_API.concat(Constants.SPACE_PLANETS));
        JSONObject moons = new JSONHandler()
                .makeHttpRequest(Constants.SPACE_INFINITY_API.concat(Constants.SPACE_MOONS));
        JSONObject stars = new JSONHandler()
                .makeHttpRequest(Constants.SPACE_INFINITY_API.concat(Constants.SPACE_STARS));
        JSONObject astronauts = new JSONHandler()
                .makeHttpRequest(Constants.SPACE_INFINITY_API.concat(Constants.SPACE_ASTRONAUTS));
        JSONObject launchSites = new JSONHandler()
                .makeHttpRequest(Constants.SPACE_INFINITY_API.concat(Constants.SPACE_LAUNCH_SITES));
        JSONObject facts = new JSONHandler()
                .makeHttpRequest(Constants.SPACE_INFINITY_API.concat(Constants.SPACE_FACTS));
        JSONObject voyagers = new JSONHandler()
                .makeHttpRequest(Constants.SPACE_INFINITY_API.concat(Constants.SPACE_VOYAGERS));
        handleImages(images);
        handleGalaxies(galaxies);
        handlePlanets(planets);
        handleMoons(moons);
        handleStars(stars);
        handleAstronauts(astronauts);
        handleLaunchSites(launchSites);
        handleFacts(facts);
        handleVoyagers(voyagers);
        Helper.putInSharedPreferences(Constants.FIRST_TIME_FLAG,
                this, Constants.FIRST_TIME_FLAG, "yes");
        doBroadcast();
    }

    private void doBroadcast() {
        Intent intent = new Intent(ACTION_COMPLETE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void handleAstronauts(JSONObject jsonObject) {
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            AstronautDao astronautDao = appDatabase.getAstronautDao();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                Astronaut astronaut = new Astronaut();
                astronaut.setImage(jsonObject1.getString("image"));
                astronaut.setPersonalData(jsonObject1.getString("personal_data"));
                astronaut.setName(jsonObject1.getString("name"));
                astronaut.setExperience(jsonObject1.getString("experience"));
                astronaut.setSummary(jsonObject1.getString("summary"));
                astronautDao.insert(astronaut);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void handleGalaxies(JSONObject jsonObject) {
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            GalaxyDao galaxyDao = appDatabase.getGalaxyDao();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                Galaxy galaxy = new Galaxy();
                galaxy.setDescription(jsonObject1.getString("description"));
                galaxy.setImage(jsonObject1.getString("image"));
                galaxy.setName(jsonObject1.getString("name"));
                galaxy.setType(CosmicItem.CosmicType.GALAXY);
                galaxy.setNumberOfStars(jsonObject1.getString("number_of_stars"));
                galaxy.setMass(jsonObject1.getString("mass"));
                galaxy.setConstellation(jsonObject1.getString("constellation"));
                galaxy.setDesignation(jsonObject1.getString("designation"));
                galaxy.setDiameter(jsonObject1.getString("diameter"));
                galaxy.setDistance(jsonObject1.getString("distance"));
                galaxy.setFacts(jsonObject1.getString("facts"));
                galaxy.setGalaxyGroup(jsonObject1.getString("galaxy_group"));
                galaxy.setGalaxyType(jsonObject1.getString("type"));
                galaxyDao.insert(galaxy);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void handlePlanets(JSONObject jsonObject) {
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            PlanetDao planetDao = appDatabase.getPlanetDao();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                Planet planet = new Planet();
                planet.setImage(jsonObject1.getString("image"));
                planet.setDescription(jsonObject1.getString("description"));
                planet.setName(jsonObject1.getString("name"));
                planet.setType(CosmicItem.CosmicType.PLANET);
                planet.setDiameter(jsonObject1.getString("diameter"));
                planet.setFacts(jsonObject1.getString("facts"));
                planet.setFirstRecord(jsonObject1.getString("first_record"));
                planet.setMass(jsonObject1.getString("mass"));
                planet.setMoons(jsonObject1.getString("moons"));
                planet.setOrbitDistance(jsonObject1.getString("orbit_distance"));
                planet.setOrbitPeriod(jsonObject1.getString("orbit_period"));
                planet.setRecordedBy(jsonObject1.getString("recorded_by"));
                planet.setSurfaceTemperature(jsonObject1.getString("surface_temperature"));
                planetDao.insert(planet);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void handleMoons(JSONObject jsonObject) {
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            MoonDao moonDao = appDatabase.getMoonDao();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                Moon moon = new Moon();
                moon.setImage(jsonObject1.getString("image"));
                moon.setDescription(jsonObject1.getString("description"));
                moon.setName(jsonObject1.getString("name"));
                moon.setType(CosmicItem.CosmicType.MOON);
                moon.setDiameter(jsonObject1.getString("diameter"));
                moon.setFacts(jsonObject1.getString("facts"));
                moon.setFirstRecord(jsonObject1.getString("first_record"));
                moon.setMass(jsonObject1.getString("mass"));
                moon.setOrbits(jsonObject1.getString("orbits"));
                moon.setOrbitDistance(jsonObject1.getString("orbit_distance"));
                moon.setOrbitPeriod(jsonObject1.getString("orbit_period"));
                moon.setRecordedBy(jsonObject1.getString("recorded_by"));
                moon.setSurfaceTemperature(jsonObject1.getString("surface_temperature"));
                moonDao.insert(moon);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void handleImages(JSONObject jsonObject) {
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            ImageItemDao imageItemDao = appDatabase.getImageItemDao();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                ImageItem imageItem = new ImageItem();
                imageItem.setImage(jsonObject1.getString("image"));
                imageItem.setHdImage(jsonObject1.getString("hd_image"));
                imageItem.setTitle(jsonObject1.getString("title"));
                imageItem.setDescription(jsonObject1.getString("description"));
                imageItem.setDateCreated(jsonObject1.getString("date_created"));
                imageItem.setPhotographer(jsonObject1.getString("photographer"));
                imageItemDao.insert(imageItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void handleStars(JSONObject jsonObject) {
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            StarDao starDao = appDatabase.getStarDao();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                Star star = new Star();
                star.setImage(jsonObject1.getString("image"));
                star.setDescription(jsonObject1.getString("description"));
                star.setName(jsonObject1.getString("name"));
                star.setType(CosmicItem.CosmicType.STAR);
                star.setDiameter(jsonObject1.getString("diameter"));
                star.setFacts(jsonObject1.getString("facts"));
                star.setMass(jsonObject1.getString("mass"));
                star.setStarType(jsonObject1.getString("type"));
                star.setAge(jsonObject1.getString("age"));
                star.setSurfaceTemperature(jsonObject1.getString("surface_temperature"));
                starDao.insert(star);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void handleFacts(JSONObject jsonObject) {
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            SpaceFactDao spaceFactDao = appDatabase.getSpaceFactDao();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                SpaceFact spaceFact = new SpaceFact();
                spaceFact.setName(jsonObject1.getString("name"));
                spaceFact.setFavorite(false);
                spaceFactDao.insert(spaceFact);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void handleLaunchSites(JSONObject jsonObject) {
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            LaunchSiteDao launchSiteDao = appDatabase.getLaunchSiteDao();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                LaunchSite launchSite = new LaunchSite();
                launchSite.setName(jsonObject1.getString("name"));
                launchSite.setImage(jsonObject1.getString("image"));
                launchSite.setLatitude(jsonObject1.getDouble("latitude"));
                launchSite.setLongitude(jsonObject1.getDouble("longitude"));
                launchSiteDao.insert(launchSite);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void handleVoyagers(JSONObject jsonObject) {
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            VoyagerDao voyagerDao = appDatabase.getVoyagerDao();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                Voyager voyager = new Voyager();
                voyager.setName(jsonObject1.getString("name"));
                voyager.setImage(jsonObject1.getString("image"));
                voyager.setVelocity(jsonObject1.getString("velocity"));
                voyager.setDistanceFromEarthAU(jsonObject1.getString("distance_from_earth_au"));
                voyager.setDistanceFromEarthKM(jsonObject1.getString("distance_from_earth_km"));
                voyager.setDistanceFromSunAU(jsonObject1.getString("distance_from_sun_au"));
                voyager.setDistanceFromSunKM(jsonObject1.getString("distance_from_sun_km"));
                voyager.setLaunchDate(jsonObject1.getString("launch_date"));
                voyager.setLaunchDateStamp(jsonObject1.getString("launch_date_stamp"));
                voyager.setOneWayLightTime(jsonObject1.getString("one_way_light_time"));
                voyagerDao.insert(voyager);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
