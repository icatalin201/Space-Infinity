package space.infinity.app.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import space.infinity.app.models.APOD;
import space.infinity.app.models.ISS;
import space.infinity.app.models.MarsRovers;

/**
 * Created by Catalin on 12/28/2017.
 */

public interface Service {

    @GET("planetary/apod")
    Call<APOD> getAstronomyPictureOfTheDay(@Query("api_key") String apiKey);

    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    Call<MarsRovers> getRoverImages(@Query("sol") int sol, @Query("api_key") String apiKey);

    @GET("iss-now.json")
    Call<ISS> getISSNow();

}
