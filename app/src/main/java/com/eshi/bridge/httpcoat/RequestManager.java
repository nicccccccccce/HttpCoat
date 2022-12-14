package com.eshi.bridge.httpcoat;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kotlin.jvm.internal.TypeParameterReference;

/**
 * Created by user on 2017/9/25.
 */

public class RequestManager {
    //    String str = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg9.51tietu.net%2Fpic%2F20190920%2F4cj1m5orryy4cj1m5orryy.jpg&refer=http%3A%2F%2Fimg9.51tietu.net&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1673170751&t=65b64ea7f08a79995bec5de07ec1289a";
//    @CoatHttp(method = CoatHttp.Method.POST, url = "http://xxx/app/login")
//    public static RequestPart<TestBean> userRequest(@Param(value = "fj") String url) {
//        RequestPart<TestBean> rp = RequestPartFactory.create();
//        rp.setRespBean(TestBean.class);
//        rp.setRequestMethod(HttpMethod.RequestMethod.GET);
//        rp.setUrl("" + url);
////        req(rp);
//        return rp;
//    }
//    @CoatHttp(method = CoatHttp.Method.POST, url = "/app/login")
//    public static RequestPart<TestBean> userRequest(@Param(name = "password") String password, @Param(name = "username") String username) {
//        RequestPart<TestBean> rp = RequestPartFactory.create();
//        rp.setRespBean(TestBean.class);
//        rp.setRequestMethod(HttpMethod.RequestMethod.POST);
//        rp.setParam(map);
////        req(rp);
//        return rp;
//    }
//
//    public static <T> void req(RequestPart<T> rp) {
//        CoatApplication.getInstance().getIHttp()
//                .request(rp);
//    }
//
//    public static void parse(Object obj) throws Exception {
//        final Class<?> clazz = obj.getClass();
//        Field[] fields = clazz.getDeclaredFields();
//        for (Field field : fields) {
//            if (field.isAnnotationPresent(CoatHttp.class)) {
//                CoatHttp CoatHttp = field.getAnnotation(CoatHttp.class);
//                String url = CoatHttp.url();
//                if (url == null) {
//                    throw new Exception("url must not be null");
//                } else {
//                    field.setAccessible(true);
//                    field.set(obj, url);
//                    field.set(obj, CoatHttp.method());
//                }
//
//            }
//
//        }
//
//    }
//
//    public Map<String, String> parseMethod(Object obj) throws Exception {
//        final Class<?> clazz = obj.getClass();
//        Map<String, String> map = new HashMap<>();
//        Method[] m = clazz.getDeclaredMethods();
//        Annotation[][] an = null;
//        for (Method method : m) {
//            an = method.getParameterAnnotations();
//            System.out.println(method.getParameterTypes());
//            if (an.length > 0) {
//                for (int i = 0; i < an.length; i++) {
//                    for (int j = 0; j < an[i].length; j++) {
//                        Param t = (Param) an[i][j];
//                        map.put(method.getName(), t.name());
//                        System.out.println(method.getName() + "," + t.name());
//                    }
//                }
//            }
//
//        }
//        return map;
//
//    }
//    public static <T> void post(String url, Map<String, String> map, Class<T> cls, IResponseListener<BaseResponseVo<T>> responseListener, IErrorListener errorListener) {
//        RequestPart<BaseResponseVo<JsonElement>> rp = RequestPartFactory.create();
//        Type type = new TypeToken<BaseResponseVo<JsonElement>>() {
//        }.getType();
//        rp.setType(type);
//        request(HttpMethod.RequestMethod.POST, url, map, getResponseListener(cls, responseListener), errorListener, rp);
//    }
    public static <T> void post(String url, Map<String, String> map, Class<T> cls, IResponseListener<BaseResponseVo<T>> responseListener, IErrorListener errorListener) {
        RequestPart<BaseResponseVo<JsonElement>> rp = RequestPartFactory.create();
        Type type = new TypeToken<BaseResponseVo<JsonElement>>() {
        }.getType();
        rp.setType(type);
        request(HttpMethod.RequestMethod.POST, url, map, getResponseListener(cls, responseListener), errorListener, rp);
    }

    public static <T> void postList(String url, Map<String, String> map, Class<T> cls, IResponseListener<BaseResponseVo<List<T>>> responseListener, IErrorListener errorListener) {
        RequestPart<BaseResponseVo<JsonElement>> rp = RequestPartFactory.create();
        Type type = new TypeToken<BaseResponseVo<JsonElement>>() {
        }.getType();
        rp.setType(type);
        request(HttpMethod.RequestMethod.POST, url, map, getResponseListListener(cls, responseListener), errorListener, rp);
    }

    public static <T> void get(String url, Map<String, String> map, Class<T> cls, IResponseListener<BaseResponseVo<T>> responseListener, IErrorListener errorListener) {
        RequestPart<BaseResponseVo<JsonElement>> rp = RequestPartFactory.create();
        Type type = new TypeToken<BaseResponseVo<JsonElement>>() {
        }.getType();
        rp.setType(type);
        request(HttpMethod.RequestMethod.GET, url, map, getResponseListener(cls, responseListener), errorListener, rp);
    }

    public static <T> void getList(String url, Map<String, String> map, Class<T> cls, IResponseListener<BaseResponseVo<List<T>>> responseListener, IErrorListener errorListener) {
        RequestPart<BaseResponseVo<JsonElement>> rp = RequestPartFactory.create();
        Type type = new TypeToken<BaseResponseVo<JsonElement>>() {
        }.getType();
        rp.setType(type);
        request(HttpMethod.RequestMethod.GET, url, map, getResponseListListener(cls, responseListener), errorListener, rp);
    }

    @NonNull
    private static <T> IResponseListener<BaseResponseVo<JsonElement>> getResponseListener(Class<T> cls, IResponseListener<BaseResponseVo<T>> responseListener) {
        return new IResponseListener<BaseResponseVo<JsonElement>>() {

            @Override
            public void OnResponse(String url, BaseResponseVo<JsonElement> jsonElementBaseResponseVo) {
                if (jsonElementBaseResponseVo == null) {
                    return;
                }
                BaseResponseVo<T> baseResponseVo = new BaseResponseVo<>();
                baseResponseVo.setCode(jsonElementBaseResponseVo.getCode());
                baseResponseVo.setMessage(jsonElementBaseResponseVo.getMessage());
                if (jsonElementBaseResponseVo.getData() != null) {
                    if (jsonElementBaseResponseVo.getData().isJsonObject()) {
                        if (String.class.getName().equals(cls.getName())) {
                            baseResponseVo.setData((T) jsonElementBaseResponseVo.getData().toString());
                        } else {
                            baseResponseVo.setData(new Gson().fromJson(jsonElementBaseResponseVo.getData(), cls));
                        }
                    } else {
                        baseResponseVo.setData((T) jsonElementBaseResponseVo.getData().getAsString());
                    }
                }
                if (responseListener != null) {
                    responseListener.OnResponse(url, baseResponseVo);
                }
            }

            @Override
            public void OnFailed(String url, Exception coatError) {
                if (responseListener != null) {
                    responseListener.OnFailed(url, coatError);
                }
            }
        };
    }

    @NonNull
    private static <T> IResponseListener<BaseResponseVo<JsonElement>> getResponseListListener(Class<T> cls, IResponseListener<BaseResponseVo<List<T>>> responseListener) {
        return new IResponseListener<BaseResponseVo<JsonElement>>() {

            @Override
            public void OnResponse(String url, BaseResponseVo<JsonElement> jsonElementBaseResponseVo) {
                if (jsonElementBaseResponseVo == null) {
                    return;
                }
                BaseResponseVo<List<T>> baseResponseVo = new BaseResponseVo<>();
                baseResponseVo.setCode(jsonElementBaseResponseVo.getCode());
                baseResponseVo.setMessage(jsonElementBaseResponseVo.getMessage());
                if (jsonElementBaseResponseVo.getData() != null) {
                    if (jsonElementBaseResponseVo.getData().isJsonArray()) {
                        JsonArray jsa = jsonElementBaseResponseVo.getData().getAsJsonArray();
                        List<T> list = new ArrayList<>();
                        for (JsonElement jsonElement : jsa) {
                            if (String.class.getName().equals(cls.getName())) {
                                list.add((T) jsonElement.toString());
                            }else{
                                list.add(new Gson().fromJson(jsonElement, cls));
                            }
                        }
                        baseResponseVo.setData(list);
                    } else {
                        baseResponseVo.setData(new ArrayList<>());
                    }
                }
                if (responseListener != null) {
                    responseListener.OnResponse(url, baseResponseVo);
                }
            }

            @Override
            public void OnFailed(String url, Exception coatError) {
                if (responseListener != null) {
                    responseListener.OnFailed(url, coatError);
                }
            }
        };
    }

    private static <T> void request(HttpMethod.RequestMethod rm, String url, Map<String, String> map, IResponseListener<T> responseListener, IErrorListener errorListener, RequestPart<T> rp) {
        rp.setRequestMethod(rm);
        rp.setUrl(url);
        rp.setParam(map);
        rp.setResponseListener(responseListener);
        rp.setErrorListener(errorListener);
        CoatApplication.getInstance().getIHttp()
                .request(rp);
    }

}
