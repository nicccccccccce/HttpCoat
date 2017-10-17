package com.eshi.bridge.httpcoat;

/**
 * Created by user on 2017/9/20.
 */

public interface IErrorListener {
    void OnError(String url, Exception e);
}
