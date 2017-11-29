package rxandroidapp.com.etognfd.testface.retrofit.api;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 14537 on 2017/11/20.
 * https://dfst.baiqishi.com
 * https://credit.baiqishi.com
 */

public class ServiceCreate {


    public ServiceCreate() {

    }

    public static APIInterface getServiceCreate() {
        return serviceApi;
    }
    public static APIInterface getSJMHServiceCreate() {
        return serviceSJMHApi;
    }

    public static APIInterface getBQSServiceCreate() {
        return serviceBQSApi;
    }

    private static APIInterface serviceApi = createApi();
    private static APIInterface serviceBQSApi = createBQSApi();
    private static APIInterface serviceSJMHApi = createSJMHApi();

    public static APIInterface createSJMHApi() {
        Log.e("tag", "APIInterface");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.shujumohe.com")
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(APIInterface.class);
    }

    public static APIInterface createApi() {
        Log.e("tag", "APIInterface");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.shujumohe.com")
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(APIInterface.class);
    }

    public static APIInterface createBQSApi() {
        Log.e("tag", "APIInterface");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dfst.baiqishi.com")
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(APIInterface.class);
    }

    public static OkHttpClient getOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(getLoggingInterceptor())
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        Request request = chain.request()
//                                .newBuilder()
//                                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
//                                .addHeader("Accept-Encoding", "gzip, deflate")
//                                .addHeader("Connection", "keep-alive")
//                                .addHeader("Accept", "*/*")
//                                .addHeader("Cookie", "add cookies here")
//                                .build();
//                        return chain.proceed(request);
//                    }
//                })
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        return okHttpClient;
    }


    public static HttpLoggingInterceptor getLoggingInterceptor() {
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.w("tag", "retrofit" + message);
            }
        });
        return loggingInterceptor.setLevel(level);
    }

}
