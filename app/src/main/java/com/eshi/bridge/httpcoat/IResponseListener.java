package com.eshi.bridge.httpcoat;

/**
 * Created by user on 2017/9/20.
 */

public interface IResponseListener<T> {
    void OnResponse(String url, T t);
    void OnFailed(String url, Exception coatError);
}
