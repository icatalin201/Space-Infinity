package space.infinity.app.model.repository;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.LiveData;
import space.infinity.app.model.AppDatabase;
import space.infinity.app.model.dao.VoyagerDao;
import space.infinity.app.model.entity.Voyager;

public class VoyagerRepository {

    private VoyagerDao voyagerDao;
    private LiveData<List<Voyager>> voyagers;

    public VoyagerRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        voyagerDao = appDatabase.getVoyagerDao();
        voyagers = voyagerDao.getVoyagerList();
    }

    public void insert(final Voyager voyager) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                voyagerDao.insert(voyager);
            }
        }).start();
    }

    public void update(final Voyager voyager) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                voyagerDao.update(voyager);
            }
        }).start();
    }

    public void delete(final Voyager voyager) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                voyagerDao.delete(voyager);
            }
        }).start();
    }

    public LiveData<List<Voyager>> getVoyagers() {
        return voyagers;
    }
}
