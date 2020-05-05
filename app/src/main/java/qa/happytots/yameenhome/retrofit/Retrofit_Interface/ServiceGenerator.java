package qa.happytots.yameenhome.retrofit.Retrofit_Interface;

import okhttp3.OkHttpClient;
import qa.happytots.yameenhome.datamodel.network.APIPrototype;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ajmal Bin Khalid on 24-01-2018.
 */

public class ServiceGenerator {

    // Not in use

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

   /* private static OkHttpClient.Builder setClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(httpLoggingInterceptor);

        return client;
    }*/


    public static Retrofit.Builder builder = new Retrofit.Builder()
            /*.client(setClient()
                    .addInterceptor(new BasicAuthInterceptor(APIPrototype.AUTHENTICATION_USERNAME, APIPrototype.AUTHENTICATION_PASSWORD))
                    .build())*/
            .baseUrl(APIPrototype.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());


}