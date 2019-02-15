package space.infinity.app.viewmodel;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import space.infinity.app.model.entity.Star;
import space.infinity.app.model.repository.StarRepository;

public class StarViewModel extends AndroidViewModel {

    private StarRepository starRepository;
    private LiveData<List<Star>> stars;

    public StarViewModel(@NonNull Application application) {
        super(application);
        starRepository = new StarRepository(application);
        stars = starRepository.getStars();
    }

    public void insert(Star star) {
        starRepository.insert(star);
    }

    public void update(Star star) {
        starRepository.update(star);
    }

    public void delete(Star star) {
        starRepository.delete(star);
    }

    public LiveData<List<Star>> getStars() {
        return stars;
    }
}
