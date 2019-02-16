package space.infinity.app.model.repository;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import space.infinity.app.model.entity.LaunchRocket;
import space.infinity.app.model.entity.LaunchRocketsResponse;
import space.infinity.app.model.network.Client;
import space.infinity.app.model.network.Service;
import space.infinity.app.util.Constants;

public class RocketsRepository {

    public interface RocketCallback {
        void onSuccess(List<LaunchRocket> rocketList);
        void onFailure(String message);
        void onLoading();
    }

    private boolean pagesOver = false;
    private int offset = 0;
    private boolean loading = true;
    private int previousTotal = 0;

    private RocketCallback rocketCallback;

    public RocketsRepository(RocketCallback rocketCallback) {
        this.rocketCallback = rocketCallback;
    }

    public void start() {
        if (pagesOver) return;
        rocketCallback.onLoading();
        Call<LaunchRocketsResponse> call = Client
                .getRetrofitClient(Constants.LAUNCH_LIBRARY_API)
                .create(Service.class).getRockets(offset, 50);
        call.enqueue(new Callback<LaunchRocketsResponse>() {
            @Override
            public void onResponse(Call<LaunchRocketsResponse> call,
                                   Response<LaunchRocketsResponse> response) {
                if (response.body() != null) {
                    List<LaunchRocket> rockets = response.body().getRockets();
                    rocketCallback.onSuccess(rockets);
                    offset += 50;
                }
                else {
                    pagesOver = true;
                }
            }

            @Override
            public void onFailure(Call<LaunchRocketsResponse> call, Throwable t) {
                t.printStackTrace();
                rocketCallback.onFailure(t.getMessage());
            }
        });
    }

    public void handleScroll(LinearLayoutManager linearLayoutManager) {
        int visibleItemCount = linearLayoutManager.getChildCount();
        int totalItemCount = linearLayoutManager.getItemCount();
        int firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        int visibleThreshold = 5;
        if (!loading && (totalItemCount - visibleItemCount) <=
                (firstVisibleItem + visibleThreshold)) {
            start();
            loading = true;
        }
    }

}
