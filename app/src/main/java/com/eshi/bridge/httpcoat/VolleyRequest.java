package com.eshi.bridge.httpcoat;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

/**
 * Created by user on 2017/9/21.
 */

public class VolleyRequest extends AbHttp {
    private static int SOCKET_TIMEOUT = 50000;
    RequestQueue queue;


    public VolleyRequest(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    private <T> void get(final String url, final Class<T> cls, final IResponseListener<T> listener, final IErrorListener errorListener) {
        final StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                coatResp(url, response, cls, listener);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                coatErr(url, error, errorListener);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(SOCKET_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    private <T> void post(final String url, final Map<String, String> map, final Class<T> cls, final IResponseListener<T> listener, final IErrorListener errorListener) {
        if (HttpCoatUtils.textIsNotNull(url) && HttpCoatUtils.mapIsNotNull(map)) {

            final StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    coatResp(url, response, cls, listener);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    coatErr(url, error, errorListener);
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return map == null ? super.getParams() : map;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(SOCKET_TIMEOUT,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(request);
        }
    }

    @Override
    public <T> void request(RequestPart<T> requestPart) {
        if (requestPart.getRequestMethod() == HttpMethod.RequestMethod.GET) {
            get(requestPart.getUrl(), requestPart.getRespBean(), requestPart.getResponseListener(), requestPart.getErrorListener());
        } else if (requestPart.getRequestMethod() == HttpMethod.RequestMethod.POST) {
            post(requestPart.getUrl(), requestPart.getParam(), requestPart.getRespBean(), requestPart.getResponseListener(), requestPart.getErrorListener());
        }
    }

    public RequestQueue getQueue() {
        return queue;
    }
}
