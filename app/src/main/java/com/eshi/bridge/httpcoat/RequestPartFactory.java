package com.eshi.bridge.httpcoat;

/**
 * Created by user on 2017/9/25.
 */

public class RequestPartFactory {
    public static <T> RequestPart<T> create() {
        return new RequestPart<>();
    }
}
