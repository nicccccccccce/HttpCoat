package com.eshi.bridge.httpcoat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {
    String str = "http://mpic.tiankong.com/077/708/0777089ad18a688eee7c756a506e5f4a/640.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RequestPart<TestBean> rp = RequestManager.userRequest(str);
        rp.setResponseListener(new IResponseListener<TestBean>() {
            @Override
            public void OnResponse(String url, TestBean testBean) {

            }
        });
        rp.setErrorListener(new IErrorListener() {
            @Override
            public void OnError(String url, Exception e) {
                
            }
        });
    }


}
