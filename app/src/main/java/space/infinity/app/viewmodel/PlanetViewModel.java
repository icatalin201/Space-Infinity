package space.infinity.app.viewmodel;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import space.infinity.app.model.entity.Planet;
import space.infinity.app.model.repository.PlanetRepository;

public class PlanetViewModel extends AndroidViewModel {

    private PlanetRepository planetRepository;
    private LiveData<List<Planet>> planets;

    public PlanetViewModel(@NonNull Application application) {
        super(application);
        planetRepository = new PlanetRepository(application);
        planets = planetRepository.getPlanets();
    }

    public void insert(Planet planet) {
        planetRepository.insert(planet);
    }

    public void update(Planet planet) {
        planetRepository.update(planet);
    }

    public void delete(Planet planet) {
        planetRepository.delete(planet);
    }

    public LiveData<List<Planet>> getPlanets() {
        return planets;
    }
}
