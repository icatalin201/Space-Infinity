package space.infinity.app.viewmodel;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import space.infinity.app.model.entity.Astronaut;
import space.infinity.app.model.repository.AstronautRepository;

public class AstronautViewModel extends AndroidViewModel {

    private AstronautRepository astronautRepository;
    private LiveData<List<Astronaut>> astronauts;

    public AstronautViewModel(@NonNull Application application) {
        super(application);
        astronautRepository = new AstronautRepository(application);
        astronauts = astronautRepository.getAstronauts();
    }

    public void insert(Astronaut astronaut) {
        astronautRepository.insert(astronaut);
    }

    public void update(Astronaut astronaut) {
        astronautRepository.update(astronaut);
    }

    public void delete(Astronaut astronaut) {
        astronautRepository.delete(astronaut);
    }

    public LiveData<List<Astronaut>> getAstronauts() {
        return astronauts;
    }
}
