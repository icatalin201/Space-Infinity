package space.infinity.app.model.repository;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.LiveData;
import space.infinity.app.model.AppDatabase;
import space.infinity.app.model.dao.SpaceFactDao;
import space.infinity.app.model.entity.SpaceFact;

public class SpaceFactRepository {

    private SpaceFactDao spaceFactDao;
    private LiveData<List<SpaceFact>> spaceFacts;

    public SpaceFactRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        spaceFactDao = appDatabase.getSpaceFactDao();
        spaceFacts = spaceFactDao.getSpaceFactList();
    }

    public void insert(final SpaceFact spaceFact) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                spaceFactDao.insert(spaceFact);
            }
        }).start();
    }

    public void update(final SpaceFact spaceFact) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                spaceFactDao.update(spaceFact);
            }
        }).start();
    }

    public void delete(final SpaceFact spaceFact) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                spaceFactDao.delete(spaceFact);
            }
        }).start();
    }

    public LiveData<List<SpaceFact>> getSpaceFacts() {
        return spaceFacts;
    }
}
