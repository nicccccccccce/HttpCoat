package com.eshi.bridge.httpcoat;

/**
 * Created by user on 2017/9/21.
 */

public interface IHttp {
    <J extends RequestPart<T>,T> void request(J requestPart);
}
