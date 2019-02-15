package space.infinity.app.util;

import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import space.infinity.app.model.entity.Astronaut;
import space.infinity.app.model.entity.CosmicItem;
import space.infinity.app.model.entity.ImageItem;
import space.infinity.app.model.entity.LaunchSite;
import space.infinity.app.model.entity.SpaceFact;

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

    public interface LaunchSitesInterface {
        void onResult(List<LaunchSite> launchSiteList);
    }

    public interface OnRequestCompleted {
        void onResult(JSONObject jsonObject, String model);
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
