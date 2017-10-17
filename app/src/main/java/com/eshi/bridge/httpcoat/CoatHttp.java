package com.eshi.bridge.httpcoat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by user on 2017/9/25.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CoatHttp {
    public  enum Method {GET, POST}
    String url() default "";
    Method   method() default Method.GET;
}
