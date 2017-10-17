package com.eshi.bridge.httpcoat;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    private OkHttpClient client = new OkHttpClient();


    public OKHttpRequest() {
        client.newBuilder().connectTimeout(30 * 1000, TimeUnit.SECONDS);
    }


    private <T> void get(final String url, final Class<T> cls, final IResponseListener<T> listener, final IErrorListener errorListener) {
        if (HttpCoatUtils.textIsNotNull(url))
            client.newCall(new Request.Builder().url(url).build()).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    coatErr(url, e, errorListener);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String result = response.body().string();
                    coatResp(url, result, cls, listener);

                }
            });
    }


    private <T> void post(final String url, Map<String, String> map, final Class<T> cls, final IResponseListener<T> listener, final IErrorListener errorListener) {
        if (HttpCoatUtils.textIsNotNull(url) && HttpCoatUtils.mapIsNotNull(map)) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                stringBuilder.append(entry.getKey() + "=" + entry.getValue());
                stringBuilder.append("&");
            }
            RequestBody rb = RequestBody.create(MediaType.parse("text/x-markdown; charset=utf-8"),
                    stringBuilder.toString().substring(0, stringBuilder.length() - 1));
            client.newCall(new Request.Builder().url(url)
                    .post(rb)
                    .build()).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    coatErr(url, e, errorListener);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String result = response.body().string();
                    coatResp(url, result, cls, listener);
                }
            });
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

    public OkHttpClient getClient() {
        return client;
    }
}
