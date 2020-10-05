package app.bmc.com.BHOOMI_MRTC.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static Retrofit retrofit = null;

   public static Retrofit getClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

     //  http://parihara.karnataka.gov.in/CLWSTEST/LoaneeReportService/Service1.svc
        retrofit = new Retrofit.Builder()
//                .baseUrl("http://218.248.32.25/PacsCertificatePayment/Service1.svc/")
                .baseUrl("http://192.168.0.139/PacsCertificatePayment/Service1.svc/") //SUSMITA 4:20.P.M
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();


        return retrofit;
    }


}
