package space.infinity.app.viewmodel;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import space.infinity.app.model.entity.Moon;
import space.infinity.app.model.repository.MoonRepository;

public class MoonViewModel extends AndroidViewModel {

    private MoonRepository moonRepository;
    private LiveData<List<Moon>> moons;

    public MoonViewModel(@NonNull Application application) {
        super(application);
        moonRepository = new MoonRepository(application);
        moons = moonRepository.getMoons();
    }

    public void insert(Moon moon) {
        moonRepository.insert(moon);
    }

    public void update(Moon moon) {
        moonRepository.update(moon);
    }

    public void delete(Moon moon) {
        moonRepository.delete(moon);
    }

    public LiveData<List<Moon>> getMoons() {
        return moons;
    }
}
