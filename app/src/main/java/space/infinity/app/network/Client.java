package space.infinity.app.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Catalin on 12/28/2017.
 */

public class Client {

    private static Retrofit retrofit = null;

    public static Retrofit getRetrofitClient(String url){
        if (retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }

}
