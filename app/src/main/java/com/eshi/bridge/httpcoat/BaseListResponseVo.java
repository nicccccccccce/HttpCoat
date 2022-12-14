package com.eshi.bridge.httpcoat;

import java.util.List;

/**
 * @auth:Administrator
 * @date:2022/12/10 0010
 */
public class BaseListResponseVo<T> {
    private int code;
    private List<T> data;
    private String message;
}
