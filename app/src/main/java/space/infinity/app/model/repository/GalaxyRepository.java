package space.infinity.app.model.repository;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.LiveData;
import space.infinity.app.model.AppDatabase;
import space.infinity.app.model.dao.GalaxyDao;
import space.infinity.app.model.entity.Galaxy;

public class GalaxyRepository {

    private GalaxyDao galaxyDao;
    private LiveData<List<Galaxy>> galaxies;

    public GalaxyRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        galaxyDao = appDatabase.getGalaxyDao();
        galaxies = galaxyDao.getGalaxyList();
    }

    public void insert(final Galaxy galaxy) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                galaxyDao.insert(galaxy);
            }
        }).start();
    }

    public void update(final Galaxy galaxy) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                galaxyDao.update(galaxy);
            }
        });
    }

    public void delete(final Galaxy galaxy) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                galaxyDao.delete(galaxy);
            }
        }).start();
    }

    public LiveData<List<Galaxy>> getGalaxies() {
        return this.galaxies;
    }
}
