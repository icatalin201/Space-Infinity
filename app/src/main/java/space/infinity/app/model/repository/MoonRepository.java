package space.infinity.app.model.repository;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.LiveData;
import space.infinity.app.model.AppDatabase;
import space.infinity.app.model.dao.MoonDao;
import space.infinity.app.model.entity.Moon;

public class MoonRepository {

    private MoonDao moonDao;
    private LiveData<List<Moon>> moons;

    public MoonRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        moonDao = appDatabase.getMoonDao();
        moons = moonDao.getMoonList();
    }

    public void insert(final Moon moon) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                moonDao.insert(moon);
            }
        }).start();
    }

    public void update(final Moon moon) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                moonDao.update(moon);
            }
        }).start();
    }

    public void delete(final Moon moon) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                moonDao.delete(moon);
            }
        }).start();
    }

    public LiveData<List<Moon>> getMoons() {
        return this.moons;
    }
}
