package space.infinity.app.model.repository;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.LiveData;
import space.infinity.app.model.AppDatabase;
import space.infinity.app.model.dao.AstronautDao;
import space.infinity.app.model.entity.Astronaut;

public class AstronautRepository {

    private AstronautDao astronautDao;
    private LiveData<List<Astronaut>> astronauts;

    public AstronautRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        astronautDao = appDatabase.getAstronautDao();
        astronauts = astronautDao.getAstronautList();
    }

    public void insert(final Astronaut astronaut) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                astronautDao.insert(astronaut);
            }
        }).start();
    }

    public void update(final Astronaut astronaut) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                astronautDao.update(astronaut);
            }
        }).start();
    }

    public void delete(final Astronaut astronaut) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                astronautDao.delete(astronaut);
            }
        }).start();
    }

    public LiveData<List<Astronaut>> getAstronauts() {
        return this.astronauts;
    }
}
