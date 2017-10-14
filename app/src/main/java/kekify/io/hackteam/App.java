package kekify.io.hackteam;

import android.app.Application;
import android.content.Context;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by djavid on 14.10.17.
 */


public class App extends Application {

    private static App appInstance;
    private ApiInterface apiInterface;


    @Override
    public void onCreate() {
        super.onCreate();

        appInstance = (App) getApplicationContext();
    }

    public ApiInterface getApiInterface() {
        if (apiInterface == null)
            apiInterface = buildApiInterface();
        return apiInterface;
    }

    private ApiInterface buildApiInterface() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://blockchain.info/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build();

        return retrofit.create(ApiInterface.class);
    }

    public static Context getContext() {
        return appInstance.getApplicationContext();
    }

    public static App getAppInstance() {
        return appInstance;
    }

}
