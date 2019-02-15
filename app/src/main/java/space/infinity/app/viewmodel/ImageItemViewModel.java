package space.infinity.app.viewmodel;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import space.infinity.app.model.entity.ImageItem;
import space.infinity.app.model.repository.ImageItemRepository;

public class ImageItemViewModel extends AndroidViewModel {

    private ImageItemRepository imageItemRepository;
    private LiveData<List<ImageItem>> imageItems;

    public ImageItemViewModel(@NonNull Application application) {
        super(application);
        imageItemRepository = new ImageItemRepository(application);
        imageItems = imageItemRepository.getImageItems();
    }

    public void insert(ImageItem imageItem) {
        imageItemRepository.insert(imageItem);
    }

    public void update(ImageItem imageItem) {
        imageItemRepository.update(imageItem);
    }

    public void delete(ImageItem imageItem) {
        imageItemRepository.delete(imageItem);
    }

    public LiveData<List<ImageItem>> getImageItems() {
        return imageItems;
    }
}
