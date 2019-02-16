package space.infinity.app.viewmodel;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import space.infinity.app.model.entity.LaunchSite;
import space.infinity.app.model.repository.LaunchSiteRepository;

public class LaunchSiteViewModel extends AndroidViewModel {

    private LaunchSiteRepository launchSiteRepository;
    private LiveData<List<LaunchSite>> launchSites;

    public LaunchSiteViewModel(@NonNull Application application) {
        super(application);
        launchSiteRepository = new LaunchSiteRepository(application);
        launchSites = launchSiteRepository.getLaunchSites();
    }

    public void insert(LaunchSite launchSite) {
        launchSiteRepository.insert(launchSite);
    }

    public void update(LaunchSite launchSite) {
        launchSiteRepository.update(launchSite);
    }

    public void delete(LaunchSite launchSite) {
        launchSiteRepository.delete(launchSite);
    }

    public LiveData<List<LaunchSite>> getLaunchSites() {
        return launchSites;
    }
}
