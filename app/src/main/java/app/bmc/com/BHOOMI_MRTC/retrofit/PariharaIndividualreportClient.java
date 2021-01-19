package app.bmc.com.BHOOMI_MRTC.retrofit;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class PariharaIndividualreportClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String url) {

//        try {
//            Log.d("test123", ""+System.getProperties());
//            System.setProperty("jdk.tls.client.protocols", "TLSv1.2");
//            Log.d("test123", ""+System.getProperties());
//            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//            OkHttpClient client = new OkHttpClient.Builder()
//                    //.sslSocketFactory(new TLSSocketFactory())
//                    .connectTimeout(30, TimeUnit.SECONDS)
//                    .readTimeout(30, TimeUnit.SECONDS)
//                    .addInterceptor(interceptor)
//                    .build();
//
//            retrofit = new Retrofit.Builder()
//                    .baseUrl(url)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                    .client(client)
//                    .build();
//
//        } catch (Exception e){
//            e.printStackTrace();
//            Log.d("Exception", ""+ e.getLocalizedMessage());
//        }

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
