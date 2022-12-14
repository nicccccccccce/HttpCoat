package com.eshi.bridge.httpcoat;

import android.app.Activity;
import android.util.Log;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * Created by user on 2017/9/22.
 */

public abstract class AbHttp implements IHttp {
    private HttpParser httpParser;

    /**
     * 请求相应错误回调
     *
     * @param coatError
     * @param listener
     * @param <T>
     */
    protected <T> void coatErr(String url, Exception coatError, IErrorListener listener) {
        listener.OnError(url, coatError);
    }

    /**
     * 请求相应回调
     *
     * @param response
     * @param listener
     * @param cls
     * @param <T>
     */
    protected <T> void coatResp(String url, String response, Class<T> cls, IResponseListener<T> listener) {
        try {
            if (HttpCoatUtils.textIsNotNull(response)) {
                if (httpParser != null && listener != null) {
//                    Class<T> entityClass = GenericsUtils.getSuperClassGenricType(cls, 0);
//                    ParameterizedType type = (ParameterizedType) cls
//                            .getGenericSuperclass();
//                    Class<T> entityClass = (Class<T>) type.getActualTypeArguments()[0];
//                    Log.i("TAG",entityClass.getName());
                    listener.OnResponse(url, httpParser.parse(response, cls));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (listener != null) {
                listener.OnFailed(url, e);
            }
        }
    }
    protected <T> void coatResp(String url, String response, Type type, IResponseListener<T> listener) {
        try {
            if (HttpCoatUtils.textIsNotNull(response)) {
                if (httpParser != null && listener != null) {
                    listener.OnResponse(url, httpParser.parse(response, type));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (listener != null) {
                listener.OnFailed(url, e);
            }
        }
    }
    /**
     * @param httpParser
     */
    public void setHttpParser(HttpParser httpParser) {
        this.httpParser = httpParser;
    }
}
