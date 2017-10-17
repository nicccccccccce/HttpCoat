package com.eshi.bridge.httpcoat;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by user on 2017/9/25.
 */

public class RequestManager {
    @CoatHttp(method=CoatHttp.Method.GET,url = "http://mpic.tiankong.com/077/708/0777089ad18a688eee7c756a506e5f4a/640.jpg")
    public static RequestPart<TestBean> userRequest(@Param(value = "fj") String url) {
        RequestPart<TestBean> rp = RequestPartFactory.create();
        rp.setRespBean(TestBean.class);
        rp.setRequestMethod(HttpMethod.RequestMethod.GET);
        rp.setUrl("" + url);
        req(rp);
        return rp;
    }

    private static <T> void req(RequestPart<T> rp) {
        CoatApplication.getInstance().getIHttp()
                .request(rp);
    }
    public void parse(Object obj) throws Exception {
        final Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(CoatHttp.class)) {
                CoatHttp CoatHttp = field.getAnnotation(CoatHttp.class);
                String url = CoatHttp.url();
                if (url == null) {
                    throw new Exception("url must not be null");
                } else {
                    field.setAccessible(true);
                    field.set(obj, url);
                    field.set(obj, CoatHttp.method());
                }

            }

        }

    }
    public void parseMethod(Object obj) throws Exception {
        final Class<?> clazz = obj.getClass();

        Method[] m = clazz.getDeclaredMethods();
        Annotation[][] an = null;
        for(Method method:m){
            an =  method.getParameterAnnotations();
            System.out.println(method.getParameterTypes()  );
            if(an.length>0){
                for(int i=0;i<an.length;i++){
                    for(int j=0;j<an[i].length;j++){
                        Param t = (Param) an[i][j];
                        System.out.println(method.getName()+","+t.value());
                    }
                }
            }

        }


    }
}
