package com.eshi.bridge.httpcoat;

import android.content.Context;

/**
 * Created by user on 2017/9/20.
 */

public class CoatFactory {
    public static IHttp create(Context context, HttpParser httpParser, HttpMethod method) {
        if (method == HttpMethod.OKHTTP) {
            OKHttpRequest or = new OKHttpRequest();
            or.setHttpParser(httpParser);
            return or;
        } else if (method == HttpMethod.VOLLEY) {
            VolleyRequest vr = new VolleyRequest(context);
            vr.setHttpParser(httpParser);
            return vr;
        }
        return null;
    }
}
