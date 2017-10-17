package com.eshi.bridge.httpcoat;

import java.util.Map;

/**
 * Created by user on 2017/9/21.
 */

public class HttpCoatUtils {
    public static boolean textIsNotNull(String text) {
        if (text == null || "".equals(text))
            return false;
        return true;
    }

    public static  boolean mapIsNotNull(Map map) {
        if (map == null || map.isEmpty())
            return false;
        return true;
    }
}
