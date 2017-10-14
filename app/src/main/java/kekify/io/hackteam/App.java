package kekify.io.hackteam;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by djavid on 14.10.17.
 */


public class App extends Application {

    public static final String TWIST_CLIENT_ID = "2850e856d2a4460d3330eab29c5e4df079f";
    public static final String TWIST_CLIENT_SECRET = "285571fdaa9756bbd2a5ab8b05b980f2d2a";

    private static App appInstance;
    private ApiInterface apiInterface;
    private SharedPreferences sharedPreferences;
    public static String SHARED_PREFERENCES_CODE="hackteam";


    @Override
    public void onCreate() {
        getPreferencesWrapper();
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
                .baseUrl("https://twistapp.com/")
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

    public SharedPreferences getRawPreferences() {
        return getSharedPreferences(SHARED_PREFERENCES_CODE, MODE_PRIVATE);
    }

    public PreferencesWrapper getPreferencesWrapper() {
        return new PreferencesWrapper(getRawPreferences());
    }

}
