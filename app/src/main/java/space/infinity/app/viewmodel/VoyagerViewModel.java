package space.infinity.app.viewmodel;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import space.infinity.app.model.entity.Voyager;
import space.infinity.app.model.repository.VoyagerRepository;

public class VoyagerViewModel extends AndroidViewModel {

    private VoyagerRepository voyagerRepository;
    private LiveData<List<Voyager>> voyagers;

    public VoyagerViewModel(@NonNull Application application) {
        super(application);
        voyagerRepository = new VoyagerRepository(application);
        voyagers = voyagerRepository.getVoyagers();
    }

    public void insert(Voyager voyager) {
        voyagerRepository.insert(voyager);
    }

    public void update(Voyager voyager) {
        voyagerRepository.update(voyager);
    }

    public void delete(Voyager voyager) {
        voyagerRepository.delete(voyager);
    }

    public LiveData<List<Voyager>> getVoyagers() {
        return voyagers;
    }
}
