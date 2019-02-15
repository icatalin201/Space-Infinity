package space.infinity.app.model.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Catalin on 12/28/2017.
 */

public class Client {

    public static Retrofit getRetrofitClient(String url) {
        return new Retrofit.Builder().baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create()).build();
    }

}
