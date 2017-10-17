package com.eshi.bridge.httpcoat;

import java.util.Map;

/**
 * Created by user on 2017/9/25.
 */

public class RequestPart<T> {
    private HttpMethod.RequestMethod rm;
    private String url;
    private Class<T> respBean;
    private Map<String, String> param;
    private IResponseListener<T> iResponseListener;
    private IErrorListener errorListener;

    public HttpMethod.RequestMethod getRequestMethod() {
        return rm;
    }

    public void setRequestMethod(HttpMethod.RequestMethod rm) {
        this.rm = rm;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Class<T> getRespBean() {
        return respBean;
    }

    public void setRespBean(Class<T> respBean) {
        this.respBean = respBean;
    }

    public Map<String, String> getParam() {
        return param;
    }

    public void setParam(Map<String, String> param) {
        this.param = param;
    }

    public IResponseListener<T> getResponseListener() {
        return iResponseListener;
    }

    public void setResponseListener(IResponseListener<T> iResponseListener) {
        this.iResponseListener = iResponseListener;
    }

    public IErrorListener getErrorListener() {
        return errorListener;
    }

    public void setErrorListener(IErrorListener errorListener) {
        this.errorListener = errorListener;
    }
}
