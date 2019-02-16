package space.infinity.app.model.repository;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.LiveData;
import space.infinity.app.model.AppDatabase;
import space.infinity.app.model.dao.StarDao;
import space.infinity.app.model.entity.Star;

public class StarRepository {

    private StarDao starDao;
    private LiveData<List<Star>> stars;

    public StarRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        starDao = appDatabase.getStarDao();
        stars = starDao.getStarList();
    }

    public void insert(final Star star) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                starDao.insert(star);
            }
        }).start();
    }

    public void update(final Star star) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                starDao.update(star);
            }
        }).start();
    }

    public void delete(final Star star) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                starDao.delete(star);
            }
        }).start();
    }

    public LiveData<List<Star>> getStars() {
        return stars;
    }
}
