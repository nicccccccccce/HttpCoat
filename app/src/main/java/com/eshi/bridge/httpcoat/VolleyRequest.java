package com.eshi.bridge.httpcoat;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.NoCache;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by user on 2017/9/21.
 */

public class VolleyRequest extends AbHttp {
    private static int SOCKET_TIMEOUT = 50000;
    RequestQueue queue;


    public VolleyRequest(Context context) {
        try {
            final X509TrustManager trustManager = new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {

                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {

                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }
            };

            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{trustManager}, new SecureRandom());
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
//            Cache cache = new NoCache(); //new DiskBasedCache(CoatApplication.getInstance().getCacheDir(), 1024 * 1024); // 1MB cap
//            Network network = new BasicNetwork(new HurlStack());
//            queue = new RequestQueue(cache, network);
            queue =Volley.newRequestQueue(context,new HurlStack(originalUrl->{
                return originalUrl;
            }, sslSocketFactory));
        } catch (Exception e) {
            e.printStackTrace();
            queue = Volley.newRequestQueue(context);
        }
    }

    public RequestQueue getQueue() {
        return queue;
    }

    @Override
    public <J extends RequestPart<T>, T> void request(J requestPart) {
        if (requestPart.getRequestMethod() == HttpMethod.RequestMethod.GET) {
            String url = requestPart.getUrl();
            if (HttpCoatUtils.mapIsNotNull(requestPart.getParam())) {
                StringBuilder stringBuilder = new StringBuilder();
                for (Map.Entry<String, String> entry : requestPart.getParam().entrySet()) {
                    stringBuilder.append(entry.getKey() + "=" + entry.getValue());
                    stringBuilder.append("&");
                }
                url = String.format("%s?%s", url, stringBuilder.substring(0, stringBuilder.length() - 1));
            }
            final StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("TAG", response);
                    if (requestPart.getType() != null) {
                        coatResp(requestPart.getUrl(), response, requestPart.getType(), requestPart.getResponseListener());
                    } else {
                        coatResp(requestPart.getUrl(), response, requestPart.getEntityClass(), requestPart.getResponseListener());
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    coatErr(requestPart.getUrl(), error, requestPart.getErrorListener());
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json;charset=UTF-8");
                    headers.put("Accept", "*/*");
                    return headers;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(SOCKET_TIMEOUT,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(request);
        } else if (requestPart.getRequestMethod() == HttpMethod.RequestMethod.POST) {
            if (HttpCoatUtils.textIsNotNull(requestPart.getUrl()) && HttpCoatUtils.mapIsNotNull(requestPart.getParam())) {
                JSONObject jsonObject = new JSONObject(requestPart.getParam());
                JsonRequest<JSONObject> request = new JsonObjectRequest(Request.Method.POST, requestPart.getUrl(), jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response == null) {
                            return;
                        }
                        Log.i("TAG", response.toString());
                        if (requestPart.getType() != null) {
                            coatResp(requestPart.getUrl(), response.toString(), requestPart.getType(), requestPart.getResponseListener());
                        } else {
                            coatResp(requestPart.getUrl(), response.toString(), requestPart.getEntityClass(), requestPart.getResponseListener());
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        coatErr(requestPart.getUrl(), error, requestPart.getErrorListener());
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        return requestPart.getParam() == null ? super.getParams() : requestPart.getParam();
                    }


                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Content-Type", "application/json;charset=UTF-8");
                        headers.put("Accept", "*/*");
                        return headers;
                    }
                };
                request.setRetryPolicy(new DefaultRetryPolicy(SOCKET_TIMEOUT,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                queue.add(request);
            }
        }
    }
}
