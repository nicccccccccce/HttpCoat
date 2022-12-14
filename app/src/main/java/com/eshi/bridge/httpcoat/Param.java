package com.eshi.bridge.httpcoat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Target 说明了Annotation所修饰的对象范围
 *
 * 取值(ElementType)有：
 *
 * 1.CONSTRUCTOR:用于描述构造器
 *
 * 2.FIELD:用于描述域
 *
 * 3.LOCAL_VARIABLE:用于描述局部变量
 *
 * 4.METHOD:用于描述方法
 *
 * 5.PACKAGE:用于描述包
 *
 * 6.PARAMETER:用于描述参数
 *
 * 7.TYPE:用于描述类、接口(包括注解类型) 或enum声明
 * ————————————————
 * Created by user on 2017/9/26.
 */
//@Target(ElementType.PARAMETER)
//@Retention(RetentionPolicy.RUNTIME)
//public @interface Param {
//    String name() default "";
//}
