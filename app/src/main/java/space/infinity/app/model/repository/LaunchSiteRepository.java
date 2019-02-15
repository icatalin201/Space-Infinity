package space.infinity.app.model.repository;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.LiveData;
import space.infinity.app.model.AppDatabase;
import space.infinity.app.model.dao.LaunchSiteDao;
import space.infinity.app.model.entity.LaunchSite;

public class LaunchSiteRepository {

    private LaunchSiteDao launchSiteDao;
    private LiveData<List<LaunchSite>> launchSites;

    public LaunchSiteRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        launchSiteDao = appDatabase.getLaunchSiteDao();
        launchSites = launchSiteDao.getLaunchSites();
    }

    public void insert(final LaunchSite launchSite) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                launchSiteDao.insert(launchSite);
            }
        }).start();
    }

    public void update(final LaunchSite launchSite) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                launchSiteDao.update(launchSite);
            }
        }).start();
    }

    public void delete(final LaunchSite launchSite) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                launchSiteDao.delete(launchSite);
            }
        }).start();
    }

    public LiveData<List<LaunchSite>> getLaunchSites() {
        return this.launchSites;
    }
}
