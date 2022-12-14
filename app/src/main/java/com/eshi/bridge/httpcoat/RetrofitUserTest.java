package com.eshi.bridge.httpcoat;

import android.util.Log;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @auth:Administrator
 * @date:2022/12/14 0014
 */
public class RetrofitUserTest {
    /**
     * get
     *
     * @param username
     * @param password
     */
    private void getMethod(String username, String password) {
        RetrofitUserService service = RetrofitUtils.getInstance().getRetrofit().create(RetrofitUserService.class);
        Call<BaseResponseVo<TestBean>> call = service.loginGet(username, password);
        //  同步调用
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<BaseResponseVo<TestBean>> response = call.execute();
                    Log.e("TAG", "run: get同步请求 " + "code --- > " + response.body().getCode() + "msg  --- >" + response.body().getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        //异步调用
        call.enqueue(new Callback<BaseResponseVo<TestBean>>() {
            @Override
            public void onResponse(Call<BaseResponseVo<TestBean>> call, Response<BaseResponseVo<TestBean>> response) {
                Log.e("TAG", "onResponse: get异步请求 " + "code --- > " +
                        response.body().getCode() + "msg  --- >" + response.body().getMessage());
            }

            @Override
            public void onFailure(Call<BaseResponseVo<TestBean>> call, Throwable t) {

            }
        });
        //post
        Call<BaseResponseVo<TestBean>> call1 = service.loginPost(username, password);
        //  同步调用
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<BaseResponseVo<TestBean>> response = call1.execute();
                    Log.e("TAG", "run: post同步请求 " + "code --- > " + response.body().getCode() + "msg  --- >" + response.body().getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //异步调用
        call1.enqueue(new Callback<BaseResponseVo<TestBean>>() {
            @Override
            public void onResponse(Call<BaseResponseVo<TestBean>> call, Response<BaseResponseVo<TestBean>> response) {


                Log.e("TAG", "onResponse: post异步请求 "+"code --- > "+
                        response.body().getCode()+"msg  --- >"+response.body().getMessage() );
            }


            @Override
            public void onFailure(Call<BaseResponseVo<TestBean>> call, Throwable t) {

            }

        });

    }

}
