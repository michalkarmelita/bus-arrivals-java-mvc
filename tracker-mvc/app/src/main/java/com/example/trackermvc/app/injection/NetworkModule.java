package com.example.trackermvc.app.injection;


import com.example.trackermvc.BuildConfig;
import com.example.trackermvc.app.injection.scopes.PerApplication;
import com.example.trackermvc.app.network.NetworkManager;
import com.example.trackermvc.app.network.NetworkManagerImpl;
import com.example.trackermvc.app.network.TflApi;
import com.example.trackermvc.app.network.interceptors.LogInInterceptor;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    private static final String BASE_URL = "https://api.tfl.gov.uk";

    @Provides
    @PerApplication
    LogInInterceptor provideLogInInterceptor() {
        return new LogInInterceptor();
    }

    @Provides
    @PerApplication
    HttpLoggingInterceptor provideLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        return loggingInterceptor;
    }

    @Provides
    @PerApplication
    OkHttpClient provideOkHttpClient(LogInInterceptor logInInterceptor, HttpLoggingInterceptor loggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(logInInterceptor)
                .addInterceptor(loggingInterceptor)
                .build();
    }

    @Provides
    @PerApplication
    Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
    }

    @Provides
    @PerApplication
    TflApi provideTflApiService(Retrofit retrofit) {
        return retrofit.create(TflApi.class);
    }

    @Provides
    @PerApplication
    NetworkManager provideNetworkManager(NetworkManagerImpl manager) {
        return manager;
    }

}
