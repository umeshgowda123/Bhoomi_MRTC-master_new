package app.bmc.com.BHOOMI_MRTC.retrofit;

import okhttp3.OkHttpClient;

import retrofit2.Retrofit;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RtcViewInfoClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String url) {

//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(30, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .addInterceptor(interceptor).build();


        OkHttpClient client = UnsafeOkHttpClient.getUnsafeOkHttpClient();

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();


        return retrofit;
    }

}
