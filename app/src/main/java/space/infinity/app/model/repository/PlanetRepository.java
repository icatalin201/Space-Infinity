package space.infinity.app.model.repository;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.LiveData;
import space.infinity.app.model.AppDatabase;
import space.infinity.app.model.dao.PlanetDao;
import space.infinity.app.model.entity.Planet;

public class PlanetRepository {

    private PlanetDao planetDao;
    private LiveData<List<Planet>> planets;

    public PlanetRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        planetDao = appDatabase.getPlanetDao();
        planets = planetDao.getPlanetList();
    }

    public void insert(final Planet planet) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                planetDao.insert(planet);
            }
        }).start();
    }

    public void update(final Planet planet) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                planetDao.update(planet);
            }
        }).start();
    }

    public void delete(final Planet planet) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                planetDao.delete(planet);
            }
        }).start();
    }

    public LiveData<List<Planet>> getPlanets() {
        return planets;
    }
}
