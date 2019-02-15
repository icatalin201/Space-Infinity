package space.infinity.app.model.repository;

import android.app.Application;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import space.infinity.app.model.AppDatabase;
import space.infinity.app.model.dao.ImageItemDao;
import space.infinity.app.model.entity.APOD;
import space.infinity.app.model.entity.ImageItem;
import space.infinity.app.model.network.Client;
import space.infinity.app.model.network.Service;
import space.infinity.app.util.Constants;

public class ApodRepository {

    public interface ApodCallback {
        void onSuccess(ImageItem imageItem);
        void onFailure(String message);
    }

    private ImageItemDao imageItemDao;
    private ApodCallback apodCallback;

    public ApodRepository(Application application, ApodCallback apodCallback) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        imageItemDao = appDatabase.getImageItemDao();
        this.apodCallback = apodCallback;
    }

    public void loadData() {
        Service service = Client.getRetrofitClient(Constants.NASA_URL).create(Service.class);
        Call<APOD> apodCall = service.getAstronomyPictureOfTheDay(Constants.API_KEY);
        apodCall.enqueue(new Callback<APOD>() {
            @Override
            public void onResponse(Call<APOD> call, Response<APOD> response) {
                if (!response.isSuccessful()){
                    getImageFromDb();
                    return;
                }
                if (response.body() == null) {
                    getImageFromDb();
                    return;
                }
                APOD apod = response.body();
                if (apod.getMedia_type().equals("image")) {
                    ImageItem imageItem = new ImageItem();
                    imageItem.setDateCreated(apod.getDate());
                    imageItem.setDescription(apod.getExplanation());
                    imageItem.setImage(apod.getUrl());
                    imageItem.setHdImage(apod.getHdurl());
                    imageItem.setPhotographer(apod.getCopyright());
                    imageItem.setTitle(apod.getTitle());
                    apodCallback.onSuccess(imageItem);
                } else {
                    getImageFromDb();
                }
            }

            @Override
            public void onFailure(Call<APOD> call, Throwable t) {
                getImageFromDb();
            }
        });
    }

    private void getImageFromDb() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ImageItem imageItem = imageItemDao.getLastImage();
                apodCallback.onSuccess(imageItem);
            }
        }).start();
    }
}
