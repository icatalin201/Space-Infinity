package space.infinity.app.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import space.infinity.app.models.APOD;
import space.infinity.app.models.LaunchResponse;
import space.infinity.app.models.SpaceXRoadster;

/**
 * Created by Catalin on 12/28/2017.
 */

public interface Service {

    @GET("planetary/apod")
    Call<APOD> getAstronomyPictureOfTheDay(@Query("api_key") String apiKey);

    @GET("roadster")
    Call<SpaceXRoadster> getSpaceXRoadster();

    @GET("launch/")
    Call<LaunchResponse> getLaunches(@Query("offset") int offset,
                                     @Query("limit") int count,
                                     @Query("sort") String sort,
                                     @Query("startdate") String startdate,
                                     @Query("enddate") String enddate);

    @GET("launch/next/{count}")
    Call<LaunchResponse> getNextLaunches(@Path("count") int count);
}
