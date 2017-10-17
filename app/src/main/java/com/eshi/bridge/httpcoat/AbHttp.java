package com.eshi.bridge.httpcoat;

import android.app.Activity;


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
        if (HttpCoatUtils.textIsNotNull(response)) {
            if (httpParser != null)
                listener.OnResponse(url, httpParser.parse(response, cls));
        }
    }

    /**
     * @param httpParser
     */
    public void setHttpParser(HttpParser httpParser) {
        this.httpParser = httpParser;
    }
}
