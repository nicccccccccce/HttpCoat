package com.eshi.bridge.httpcoat;

/**
 * @auth:Administrator
 * @date:2022/12/13 0013
 */
public class TestBean {

    public int firstUpdatePwdFlag;
    public String maxExpireTime;
    public String token;

    @Override
    public String toString() {
        return "TestBean{" +
                "firstUpdatePwdFlag=" + firstUpdatePwdFlag +
                ", maxExpireTime='" + maxExpireTime + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
