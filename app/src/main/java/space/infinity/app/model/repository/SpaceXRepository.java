package space.infinity.app.model.repository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import space.infinity.app.model.entity.SpaceXRoadster;
import space.infinity.app.model.network.Client;
import space.infinity.app.model.network.Service;
import space.infinity.app.util.Constants;

public class SpaceXRepository {

    public interface SpaceXCallback {
        void onSuccess(SpaceXRoadster spaceXRoadster);
        void onFailure();
        void onLoading();
    }

    private SpaceXCallback spaceXCallback;

    public SpaceXRepository(SpaceXCallback spaceXCallback) {
        this.spaceXCallback = spaceXCallback;
    }

    public void start() {
        spaceXCallback.onLoading();
        Call<SpaceXRoadster> call = Client.getRetrofitClient(Constants.SPACEX_API)
                .create(Service.class).getSpaceXRoadster();
        call.enqueue(new Callback<SpaceXRoadster>() {
            @Override
            public void onResponse(Call<SpaceXRoadster> call, Response<SpaceXRoadster> response) {
                if (response.body() != null) {
                    SpaceXRoadster spaceXRoadster = response.body();
                    spaceXCallback.onSuccess(spaceXRoadster);
                } else {
                    spaceXCallback.onFailure();
                }
            }

            @Override
            public void onFailure(Call<SpaceXRoadster> call, Throwable t) {
                t.printStackTrace();
                spaceXCallback.onFailure();
            }
        });
    }
}
