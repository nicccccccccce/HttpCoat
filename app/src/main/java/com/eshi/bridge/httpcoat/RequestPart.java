package com.eshi.bridge.httpcoat;

import android.os.Build;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by user on 2017/9/25.
 */

public class RequestPart<T>{
    private HttpMethod.RequestMethod rm;
    private String url;
    private Map<String, String> param;
    private IResponseListener<T> iResponseListener;
    private IErrorListener errorListener;
    private Class<T> entityClass;
    private Type type;
    public RequestPart() {
    }

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

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
