package space.infinity.app.model.repository;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.LiveData;
import space.infinity.app.model.AppDatabase;
import space.infinity.app.model.dao.ImageItemDao;
import space.infinity.app.model.entity.ImageItem;

public class ImageItemRepository {

    private ImageItemDao imageItemDao;
    private LiveData<List<ImageItem>> imageItems;

    public ImageItemRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        imageItemDao = appDatabase.getImageItemDao();
        imageItems = imageItemDao.getImageList();
    }

    public void insert(final ImageItem imageItem) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                imageItemDao.insert(imageItem);
            }
        }).start();
    }

    public void update(final ImageItem imageItem) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                imageItemDao.update(imageItem);
            }
        });
    }

    public void delete(final ImageItem imageItem) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                imageItemDao.delete(imageItem);
            }
        }).start();
    }

    public LiveData<List<ImageItem>> getImageItems() {
        return this.imageItems;
    }

}
