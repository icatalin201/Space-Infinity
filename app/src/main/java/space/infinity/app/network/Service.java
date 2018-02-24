package space.infinity.app.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import space.infinity.app.models.apod.APOD;
import space.infinity.app.models.mars.MarsRovers;

/**
 * Created by Catalin on 12/28/2017.
 */

public interface Service {

    @GET("planetary/apod")
    Call<APOD> getAstronomyPictureOfTheDay(@Query("api_key") String apiKey);

    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    Call<MarsRovers> getCuriosityImages(@Query("sol") int sol, @Query("api_key") String apiKey);

    @GET("mars-photos/api/v1/rovers/opportunity/photos")
    Call<MarsRovers> getOpportunityImages(@Query("sol") int sol, @Query("api_key") String apiKey);

    @GET("mars-photos/api/v1/rovers/spirit/photos")
    Call<MarsRovers> getSpiritImages(@Query("sol") int sol, @Query("api_key") String apiKey);

    @GET("planetary/apod")
    Call<APOD> getApodByDate(@Query("date") String date, @Query("api_key") String apiKey);
}
