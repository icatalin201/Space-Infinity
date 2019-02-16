package space.infinity.app.viewmodel;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import space.infinity.app.model.entity.Galaxy;
import space.infinity.app.model.repository.GalaxyRepository;

public class GalaxyViewModel extends AndroidViewModel {

    private GalaxyRepository galaxyRepository;
    private LiveData<List<Galaxy>> galaxies;

    public GalaxyViewModel(@NonNull Application application) {
        super(application);
        galaxyRepository = new GalaxyRepository(application);
        galaxies = galaxyRepository.getGalaxies();
    }

    public void insert(Galaxy galaxy) {
        galaxyRepository.insert(galaxy);
    }

    public void update(Galaxy galaxy) {
        galaxyRepository.update(galaxy);
    }

    public void delete(Galaxy galaxy) {
        galaxyRepository.delete(galaxy);
    }

    public LiveData<List<Galaxy>> getGalaxies() {
        return galaxies;
    }
}
