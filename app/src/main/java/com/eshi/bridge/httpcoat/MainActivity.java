package com.eshi.bridge.httpcoat;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.eshi.bridge.httpcoat.databinding.ActivityMainBinding;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends FragmentActivity {
    String str = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg9.51tietu.net%2Fpic%2F20190920%2F4cj1m5orryy4cj1m5orryy.jpg&refer=http%3A%2F%2Fimg9.51tietu.net&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1673170751&t=65b64ea7f08a79995bec5de07ec1289a";
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        Map<String, String> map = new HashMap<>();
//        map.put("username", "admin");
//        map.put("password", "888888");
//        RequestManager.post("http://xxx/app/login", map,TestBean.class,new IResponseListener<BaseResponseVo<TestBean>>() {
//
//            @Override
//            public void OnResponse(String url,BaseResponseVo<TestBean> testBeanBaseResponseVo) {
//                if (testBeanBaseResponseVo.getData()!=null) {
//                    Log.d("TAG",testBeanBaseResponseVo.getData().maxExpireTime);
//                }
//
//            }
//
//            @Override
//            public void OnFailed(String url, Exception coatError) {
//
//            }
//        },(url,e)->{});
//        Map<String, String> map = new HashMap<>();
//        map.put("name","");
//        RequestManager.getList("https://xxxx", map, String.class, new IResponseListener<BaseResponseVo<List<String>>>() {
//            @Override
//            public void OnResponse(String url, BaseResponseVo<List<String>> listBaseResponseVo) {
//
//            }
//
//            @Override
//            public void OnFailed(String url, Exception coatError) {
//
//            }
//        }, (url, e) -> {
//        });
    }


}
