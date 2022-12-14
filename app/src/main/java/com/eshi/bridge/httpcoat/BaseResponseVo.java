package com.eshi.bridge.httpcoat;

/**
 * @auth:Administrator
 * @date:2022/12/10 0010
 */
public class BaseResponseVo<T> {
    private int code;
    private T data;
    private String message;

    public int getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
