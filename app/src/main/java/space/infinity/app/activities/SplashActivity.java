package space.infinity.app.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.eyalbira.loadingdots.LoadingDots;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import space.infinity.app.R;
import space.infinity.app.dao.AstronautDao;
import space.infinity.app.dao.GalaxyDao;
import space.infinity.app.dao.ImageItemDao;
import space.infinity.app.dao.LaunchSiteDao;
import space.infinity.app.dao.MoonDao;
import space.infinity.app.dao.PlanetDao;
import space.infinity.app.dao.SpaceFactDao;
import space.infinity.app.dao.StarDao;
import space.infinity.app.dao.VoyagerDao;
import space.infinity.app.database.AppDatabase;
import space.infinity.app.database.AppDatabaseHelper;
import space.infinity.app.models.Astronaut;
import space.infinity.app.models.CosmicItem;
import space.infinity.app.models.Galaxy;
import space.infinity.app.models.ImageItem;
import space.infinity.app.models.LaunchSite;
import space.infinity.app.models.Moon;
import space.infinity.app.models.Planet;
import space.infinity.app.models.SpaceFact;
import space.infinity.app.models.Star;
import space.infinity.app.models.Voyager;
import space.infinity.app.network.CheckingConnection;
import space.infinity.app.utils.Constants;
import space.infinity.app.utils.Helper;
import space.infinity.app.utils.JSONHandler;
import space.infinity.app.utils.ThreadHelper;

public class SplashActivity extends AppCompatActivity {

    private class ActivityHelper
            extends space.infinity.app.models.ActivityHelper
            implements ThreadHelper.OnRequestCompleted {

        private AppDatabase appDatabase;

        private String[] requests = new String[] {
            Constants.SPACE_IMAGES,
            Constants.SPACE_GALAXIES,
            Constants.SPACE_PLANETS,
            Constants.SPACE_MOONS,
            Constants.SPACE_STARS,
            Constants.SPACE_ASTRONAUTS,
            Constants.SPACE_LAUNCH_SITES,
            Constants.SPACE_FACTS,
            Constants.SPACE_VOYAGERS
        };

        private int countdown = requests.length;
        private int current = 0;
        private boolean isAllGood = true;

        ActivityHelper(Context context) {
            super(context);
            appDatabase = AppDatabaseHelper.getDatabase(context);
        }

        private void launchThread() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String model = requests[current];
                    JSONObject jsonObject = new JSONHandler()
                            .makeHttpRequest(Constants.SPACE_INFINITY_API.concat(model));
                    onResult(jsonObject, model);
                }
            }).start();
        }

        @Override
        public void onStart() {
            if (CheckingConnection.isConnected(getContext())) {
                loadingDots.setVisibility(View.VISIBLE);
                funny.setText(R.string.funny);
                launchThread();
            } else {
                Snackbar snackbar = Snackbar
                        .make(coordinator, "No Internet Connection", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                recreate();
                            }
                        });
                snackbar.setActionTextColor(getResources().getColor(R.color.primaryTextColor));
                snackbar.show();
            }
        }

        @Override
        public void onDestroy() {
            appDatabase.close();
        }

        @Override
        public void showLayout() { }

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
                isAllGood = false;
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
                isAllGood = false;
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
                isAllGood = false;
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
                isAllGood = false;
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
                    imageItem.setTitle(jsonObject1.getString("title"));
                    imageItem.setDescription(jsonObject1.getString("description"));
                    imageItem.setDateCreated(jsonObject1.getString("date_created"));
                    imageItem.setPhotographer(jsonObject1.getString("photographer"));
                    imageItemDao.insert(imageItem);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                isAllGood = false;
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
                isAllGood = false;
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
                isAllGood = false;
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
                isAllGood = false;
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
                isAllGood = false;
            }
        }

        @Override
        public void onResult(JSONObject jsonObject, String model) {
            countdown--;
            current++;
            switch (model) {
                case Constants.SPACE_IMAGES:
                    handleImages(jsonObject);
                    break;
                case Constants.SPACE_GALAXIES:
                    handleGalaxies(jsonObject);
                    break;
                case Constants.SPACE_PLANETS:
                    handlePlanets(jsonObject);
                    break;
                case Constants.SPACE_MOONS:
                    handleMoons(jsonObject);
                    break;
                case Constants.SPACE_STARS:
                    handleStars(jsonObject);
                    break;
                case Constants.SPACE_ASTRONAUTS:
                    handleAstronauts(jsonObject);
                    break;
                case Constants.SPACE_LAUNCH_SITES:
                    handleLaunchSites(jsonObject);
                    break;
                case Constants.SPACE_FACTS:
                    handleFacts(jsonObject);
                    break;
                case Constants.SPACE_VOYAGERS:
                    handleVoyagers(jsonObject);
                    break;
            }
            if (countdown == 0) {
                Helper.putInSharedPreferences(Constants.FIRST_TIME_FLAG,
                        getContext(), Constants.FIRST_TIME_FLAG, "yes");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        start();
                    }
                });
                onDestroy();
            } else {
                launchThread();
            }
        }
    }

    private LoadingDots loadingDots;
    private TextView funny;
    private CoordinatorLayout coordinator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        loadingDots = findViewById(R.id.progress_bar);
        coordinator = findViewById(R.id.coordinator);
        funny = findViewById(R.id.funny);
        String firstTime = Helper.getFromSharedPreferences(Constants.FIRST_TIME_FLAG,
                this, Constants.FIRST_TIME_FLAG);
        if (!firstTime.equals("yes")) {
            ActivityHelper activityHelper = new ActivityHelper(this);
            activityHelper.onStart();
        } else {
            start();
        }
    }

    private void start() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 700);
    }

    @Override
    public void onBackPressed() { }
}
