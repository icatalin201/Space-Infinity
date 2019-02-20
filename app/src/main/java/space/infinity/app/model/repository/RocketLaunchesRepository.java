package space.infinity.app.model.repository;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import space.infinity.app.model.entity.Launch;
import space.infinity.app.model.entity.LaunchResponse;
import space.infinity.app.model.network.Client;
import space.infinity.app.model.network.Service;
import space.infinity.app.util.Constants;
import space.infinity.app.util.Helper;

public class RocketLaunchesRepository {

    public interface RocketLaunchCallback {
        void onSuccess(List<Launch> launches);
        void onFailure(String message);
        void onLoading();
    }

    private RocketLaunchCallback rocketLaunchCallback;
    private boolean pagesOver = false;
    private int offset = 0;
    private boolean loading = true;
    private int previousTotal = 0;

    public RocketLaunchesRepository(RocketLaunchCallback rocketLaunchCallback) {
        this.rocketLaunchCallback = rocketLaunchCallback;
    }

    public void start(int pos) {
        offset = 0;
        if (pos == 0) {
            makeFutureCall();
        } else {
            makeAllCall();
        }
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
            makeAllCall();
            loading = true;
        }
    }

    private void makeFutureCall() {
        Call<LaunchResponse> launchResponseCall = Client.getRetrofitClient(Constants.LAUNCH_LIBRARY_API)
                .create(Service.class).getNextLaunches(50);
        launchResponseCall.enqueue(new Callback<LaunchResponse>() {
            @Override
            public void onResponse(Call<LaunchResponse> call, Response<LaunchResponse> response) {
                Log.e("URL", call.request().url().toString());
                if (response.body() != null) {
                    List<Launch> launchList = new ArrayList<>();
                    for (Launch launch : response.body().getLaunches()) {
                        if (launch.getRocket() == null) continue;
                        launchList.add(launch);
                    }
                    rocketLaunchCallback.onSuccess(launchList);
                }
            }

            @Override
            public void onFailure(Call<LaunchResponse> call, Throwable t) {
                t.printStackTrace();
                rocketLaunchCallback.onFailure(t.getMessage());
            }
        });
    }

    private void makeAllCall() {
        if (pagesOver) return;
        rocketLaunchCallback.onLoading();
        String today = Helper.dateToString(Calendar.getInstance().getTime(), Constants.DATE_FORMAT_1);
        Call<LaunchResponse> launchResponseCall = Client.getRetrofitClient(Constants.LAUNCH_LIBRARY_API)
                .create(Service.class).getLaunches(offset, 50, "desc",
                        "2000-01-01", today);
        launchResponseCall.enqueue(new Callback<LaunchResponse>() {
            @Override
            public void onResponse(Call<LaunchResponse> call, Response<LaunchResponse> response) {
                Log.e("URL", call.request().url().toString());
                if (response.body() != null) {
                    List<Launch> launchList = new ArrayList<>();
                    for (Launch launch : response.body().getLaunches()) {
                        if (launch.getRocket() == null) continue;
                        launchList.add(launch);
                    }
                    rocketLaunchCallback.onSuccess(launchList);
                    offset += 50;
                } else {
                    pagesOver = true;
                }
            }

            @Override
            public void onFailure(Call<LaunchResponse> call, Throwable t) {
                t.printStackTrace();
                rocketLaunchCallback.onFailure(t.getMessage());
            }
        });
    }
}
