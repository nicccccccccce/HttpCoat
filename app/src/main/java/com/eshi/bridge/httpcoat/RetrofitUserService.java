package com.eshi.bridge.httpcoat;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @auth:Administrator
 * @date:2022/12/14 0014
 */
public interface RetrofitUserService {
    @GET("boss/user/validlogin")
    Call<BaseResponseVo<TestBean>> loginGet(@Query("username") String username, @Query("password") String password);

    @POST("boss/user/validlogin")
    @FormUrlEncoded
    Call<BaseResponseVo<TestBean>> loginPost(@Field("username") String username, @Field("password") String password);
}
