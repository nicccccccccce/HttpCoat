package com.eshi.bridge.httpcoat;

/**
 * Created by user on 2017/9/21.
 */

public interface HttpParser {
    <T> T parse(String s, Class<T> t);
}
