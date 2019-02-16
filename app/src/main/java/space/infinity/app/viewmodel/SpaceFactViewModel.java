package space.infinity.app.viewmodel;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import space.infinity.app.model.entity.SpaceFact;
import space.infinity.app.model.repository.SpaceFactRepository;

public class SpaceFactViewModel extends AndroidViewModel {

    private SpaceFactRepository spaceFactRepository;
    private LiveData<List<SpaceFact>> spaceFacts;

    public SpaceFactViewModel(@NonNull Application application) {
        super(application);
        spaceFactRepository = new SpaceFactRepository(application);
        spaceFacts = spaceFactRepository.getSpaceFacts();
    }

    public void insert(SpaceFact spaceFact) {
        spaceFactRepository.insert(spaceFact);
    }

    public void update(SpaceFact spaceFact) {
        spaceFactRepository.update(spaceFact);
    }

    public void delete(SpaceFact spaceFact) {
        spaceFactRepository.delete(spaceFact);
    }

    public LiveData<List<SpaceFact>> getSpaceFacts() {
        return spaceFacts;
    }
}
