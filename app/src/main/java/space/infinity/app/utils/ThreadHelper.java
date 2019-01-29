package space.infinity.app.utils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import space.infinity.app.models.Astronaut;
import space.infinity.app.models.CosmicItem;
import space.infinity.app.models.ImageItem;
import space.infinity.app.models.LaunchSite;
import space.infinity.app.models.Rocket;
import space.infinity.app.models.SpaceFact;

public class ThreadHelper {

    public interface CosmicInterface {
        void onResult(List<? extends CosmicItem> cosmicItemList);
    }

    public interface ImageInterface {
        void onResult(List<ImageItem> imageItemList);
    }

    public interface FactsInterface {
        void onResult(List<SpaceFact> spaceFactList);
    }

    public interface AstronautsInterface {
        void onResult(List<Astronaut> astronautList);
    }

    public interface RocketsInterface {
        void onResult(List<Rocket> rocketList);
    }

    public interface LaunchSitesInterface {
        void onResult(List<LaunchSite> launchSiteList);
    }

    private Thread thread;
    private ExecutorService executorService;

    public ThreadHelper(Runnable runnable) {
        thread = new Thread(runnable);
        executorService = Executors.newSingleThreadExecutor();
    }

    public void startExecutor() {
        executorService.execute(thread);
    }

    public void stopExecutor() {
        executorService.shutdown();
    }
}
