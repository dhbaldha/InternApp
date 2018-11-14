package com.myinternapp.networking;

import android.content.Context;

import java.io.IOException;
import java.util.List;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkSetup {

    public static final String URL_END_POINT = "http://api.androidhive.info/";


    private Context context = null;

    public NetworkSetup(Context context) {
        this.context = context;
    }

    private InternAppClient.Builder internAppClient = null;


    public AppApi setupLogin(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        internAppClient = new InternAppClient().newBuilder();
        internAppClient.addInterceptor(interceptor);
        internAppClient.addInterceptor(httpLoggingInterceptor);


        return new Retrofit.Builder()
                .baseUrl(URL_END_POINT)
                .addConverterFactory(GsonConverterFactory.create())
                .client(internAppClient.build())
                .build()
                .create(AppApi.class);
    }

    Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            okhttp3.Request request = chain.request();

            Headers headers = request.headers().newBuilder().add("Authorization", "").build();

            request = request.newBuilder().headers(headers).build();

            return chain.proceed(request);
        }
    };

    private class InternAppClient extends OkHttpClient {

        private List<Interceptor> interceptorList = null;

        @Override
        public int connectTimeoutMillis() {
            return 60 * 1000;
        }

        @Override
        public int readTimeoutMillis() {
            return 60 * 1000;
        }

    }



}
