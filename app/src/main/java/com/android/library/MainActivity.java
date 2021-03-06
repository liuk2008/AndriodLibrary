package com.android.library;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.android.library.database.DatabaseDao;
import com.android.library.network.HttpDemo;
import com.android.library.network.RetrofitDemo;
import com.android.network.header.MyCookie;
import com.android.network.header.MyCookieJar;
import com.android.network.header.MyCookieManager;
import com.android.network.header.MyHeaderManager;
import com.android.network.http.engine.HttpEngine;
import com.android.network.retrofit.RetrofitEngine;
import com.viewinject.annotation.MyBindView;
import com.viewinject.bindview.MyViewInjector;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String token = "";
    private RetrofitDemo retrofitDemo;

    @MyBindView(R.id.btn_view)
    Button btn_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 测试APT机制
        MyViewInjector.bindView(this);
        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "btn_view", Toast.LENGTH_SHORT).show();
            }
        });

        // 添加Header
        MyHeaderManager myHeaderManager = MyHeaderManager.getInstance();
        myHeaderManager.addHeader("version", "1.0");
        myHeaderManager.addHeader("channel", "oppo");
        // 添加Cookie
        MyCookieManager myCookieManager = MyCookieManager.getInstance();
        myCookieManager.setMyCookieJar(new MyCookieJar() {
            @Override
            public List<MyCookie> cookieForRequest(String url) {
                Log.d("cookie", "cookieForRequest: " + url);
                MyCookie myCookie1 = new MyCookie.Builder().setName("version").setValue("1.0").setDomain(".lawcert.com").builder();
                MyCookie myCookie2 = new MyCookie.Builder().setName("").setValue(null).setDomain(".lawcert.com").builder();
                MyCookie myCookie3 = new MyCookie.Builder().setName("channel").setValue("huawei").setDomain("lawcert.com").builder();
                MyCookie myCookie5 = new MyCookie.Builder().setName("test").setValue("oppo").setDomain("baidu.com").builder();
                List<MyCookie> myCookies = new ArrayList<>();
                myCookies.add(myCookie1);
                myCookies.add(myCookie2);
                myCookies.add(myCookie3);
                myCookies.add(myCookie5);
                if (!TextUtils.isEmpty(token)) {
                    MyCookie myCookie4 = new MyCookie.Builder().setName("token").setValue(token).builder();
                    myCookies.add(myCookie4);
                }
                return myCookies;
            }

            @Override
            public void cookieFromResponse(String url, List<MyCookie> myCookies) {
                Log.d("cookie", "cookieFromResponse: " + url);
                for (MyCookie myCookie : myCookies) {
                    Log.d("cookie", "cookieFromResponse: " + myCookie);
                    if ("token".equalsIgnoreCase(myCookie.name)) {
                        token = myCookie.value;
                    }
                }
            }
        });
//        testDatabase();
//        testRetrofit();
        testHttp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (retrofitDemo != null)
            retrofitDemo.cancelAll();
        MyViewInjector.unbindView(this);
    }

    private void testHttp() {
        HttpEngine.getInstance().init(getApplicationContext());
        HttpDemo.userInfo();
    }

    private void testRetrofit() {
        RetrofitEngine.getInstance().init(getApplicationContext());
        retrofitDemo = new RetrofitDemo();
        retrofitDemo.userInfo();
//        retrofitDemo.userInfo1();
    }

    private void testDatabase() {
        DatabaseDao dao = DatabaseDao.getInstance();
        dao.init(getApplicationContext());
        dao.testMsgInfo();
        dao.testUserInfo();
    }

}
