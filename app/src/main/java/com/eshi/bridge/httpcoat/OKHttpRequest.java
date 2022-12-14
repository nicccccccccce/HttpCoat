package com.eshi.bridge.httpcoat;

import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by user on 2017/9/21.
 */

public class OKHttpRequest extends AbHttp {

    private OkHttpClient client;
    private static long DEFAULT_MILLISECONDS = 30 * 1000L;

    public OKHttpRequest() {
        try {
            client = getSSLOkHttpClient();
        } catch (Exception e) {
            client = new OkHttpClient.Builder()
                    .followRedirects(true)
                    .readTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)
                    .writeTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)
                    .connectTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)
                    .hostnameVerifier((hostname, session) -> true)
                    .build();
            ;
        }
    }

    /**
     * 设置https 访问的时候对所有证书都进行信任
     *
     * @throws Exception
     */
    public static OkHttpClient getSSLOkHttpClient() throws Exception {
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

        return new OkHttpClient.Builder()
                .followRedirects(true)
//                .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                .sslSocketFactory(sslSocketFactory, trustManager)
                .readTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)
                .writeTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)
                .connectTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)
//                .addInterceptor(new LoggerInterceptor("HTTP"))
                .hostnameVerifier((hostname, session) -> true)
                .build();
    }

    public OkHttpClient getClient() {
        return client;
    }

    @Override
    public <J extends RequestPart<T>, T> void request(J requestPart) {
        if (requestPart.getRequestMethod() == HttpMethod.RequestMethod.GET) {

            if (HttpCoatUtils.textIsNotNull(requestPart.getUrl())) {
                String url = requestPart.getUrl();
                if (HttpCoatUtils.mapIsNotNull(requestPart.getParam())) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (Map.Entry<String, String> entry : requestPart.getParam().entrySet()) {
                        stringBuilder.append(entry.getKey() + "=" + entry.getValue());
                        stringBuilder.append("&");
                    }
                    url = String.format("%s?%s", url, stringBuilder.substring(0, stringBuilder.length() - 1));
                }
                client.newCall(new Request.Builder().url(url).build()).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        coatErr(requestPart.getUrl(), e, requestPart.getErrorListener());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        Log.i("TAG", result);
                        if (requestPart.getEntityClass() != null) {
                            coatResp(requestPart.getUrl(), result, requestPart.getEntityClass(), requestPart.getResponseListener());
                        } else {
                            coatResp(requestPart.getUrl(), result, requestPart.getType(), requestPart.getResponseListener());
                        }


                    }
                });
            }
        } else if (requestPart.getRequestMethod() == HttpMethod.RequestMethod.POST) {
            if (HttpCoatUtils.textIsNotNull(requestPart.getUrl()) && HttpCoatUtils.mapIsNotNull(requestPart.getParam())) {
                JSONObject jsonObject = new JSONObject(requestPart.getParam());
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody rb = RequestBody.create(jsonObject.toString(), JSON);
                client.newCall(new Request.Builder().url(requestPart.getUrl())
                        .post(rb)
                        .build()).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        coatErr(requestPart.getUrl(), e, requestPart.getErrorListener());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        Log.i("TAG", result);
                        if (requestPart.getEntityClass() != null) {
                            coatResp(requestPart.getUrl(), result, requestPart.getEntityClass(), requestPart.getResponseListener());
                        } else {
                            coatResp(requestPart.getUrl(), result, requestPart.getType(), requestPart.getResponseListener());
                        }

                    }
                });
            }
        }
    }
}
