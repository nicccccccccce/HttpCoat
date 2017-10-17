package com.eshi.bridge.httpcoat;

import android.app.Application;

import com.google.gson.Gson;

/**
 * Created by user on 2017/9/25.
 */

public class CoatApplication extends Application {
    private static CoatApplication coatApplication;

    public static CoatApplication getInstance() {
        return coatApplication;
    }

    private IHttp iHttp;

    public IHttp getIHttp() {
        return iHttp;
    }

    /**
     * 选择解析工具和网络请求工具
     */
    private void initIHttp() {
        iHttp = CoatFactory.create(this, new HttpParser() {
            @Override
            public <T> T parse(String s, Class<T> t) {
                return new Gson().fromJson(s, t);
            }
        }, HttpMethod.VOLLEY);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        coatApplication = this;
        initIHttp();
    }
}
