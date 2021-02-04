package com.hyuga.xinlv.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.hyuga.xinlv.MainApplication;
import com.hyuga.xinlv.bean.HttpResult;
import com.hyuga.xinlv.config.EnvConfig;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * RetrofitUtil
 * Created by Je on 2016/12/27.
 */

public class RetrofitUtil {

    public static <T> T getService(Class<T> clazz) {
        return createService(clazz);
    }

    private static <T> T createService(Class<T> clazz) {


        return new Retrofit.Builder()
                .baseUrl(EnvConfig.get().url.api)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(clazz);
    }


    private static <T> Observable.Transformer<T, T> applyScheduer() {
        return observable -> observable.map(t -> t)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


    public static <T> Subscription newObserver(Observable<T> observable, Observer<T> observer) {
        return observable.compose(applyScheduer()).subscribe(observer);
    }


    private static <T> Observable.Transformer<HttpResult<T>, T> applyScheduerHttp() {
        return observable -> observable.map(tHttpResult -> {
            if (tHttpResult.getData() != null && tHttpResult.getCode() == 200) {
                return tHttpResult.getData();
            } else {
                return null;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


    public static <T> Subscription newObserverHttp(Observable<HttpResult<T>> observable, Observer<T> observer) {
        return observable.compose(applyScheduerHttp()).subscribe(observer);
    }


    private static HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(message ->
            Log.i("网络请求", message)).setLevel(HttpLoggingInterceptor.Level.BODY);

//    private static HttpLoggingInterceptor cookieinterceptor = new HttpLoggingInterceptor(message ->
//            Log.i("设置cookie", message));


    /**
     * 将Cookies传递给服务器
     */
    static class AddCookiesInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();
            SharedPreferences sp = MainApplication.mainApplication.getSharedPreferences("react-native-sharepreference",
                    Context.MODE_PRIVATE);
            String value = sp.getString("user_info_key", null);
            if (value != null) {
                Gson gson = new Gson();
                JSONObject userData = gson.fromJson(value, JSONObject.class);
                try {
                    builder.addHeader("Authorization", userData.getString("token"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            String flag = sp.getString("request_flag_key", null);
            if (flag != null) {
                builder.addHeader("request-flag", flag);
            }
            return chain.proceed(builder.build());
        }
    }


    private static OkHttpClient client = new OkHttpClient.Builder()
            //添加interceptor,日志拦截器
            .addInterceptor(new AddCookiesInterceptor())
            //.addInterceptor(new ReceivedCookiesInterceptor())
            .addInterceptor(interceptor)
            //设置连接超时的时间
            .connectTimeout(10L, TimeUnit.SECONDS)
            //设置读取超时的时间
            .readTimeout(10L, TimeUnit.SECONDS)
            //设置失败重连
            .retryOnConnectionFailure(true)
            .build();
}

