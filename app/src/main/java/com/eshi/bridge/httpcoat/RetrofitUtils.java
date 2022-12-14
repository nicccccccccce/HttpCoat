package com.eshi.bridge.httpcoat;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * @auth:Administrator
 * @date:2022/12/14 0014
 */
public class RetrofitUtils {
    private static RetrofitUtils retrofitUtils;
    private RetrofitUtils() {
    }
    public static RetrofitUtils getInstance() {

        if (retrofitUtils == null) {
            synchronized (RetrofitUtils.class) {
                if (retrofitUtils == null) {
                    retrofitUtils = new RetrofitUtils();
                }
            }
        }
        return retrofitUtils;
    }
    //返回Retrofit
    public Retrofit getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
