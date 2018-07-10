package com.study.yuyong.activitytest;
/**
 * @date on 2018/7/10
 * @author yuyong
 * @Email yu1183688986@163.com
 * @describe
 */
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        Button forceOffline = (Button) findViewById(R.id.force_offline);
        forceOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.study.yuyong.activitytest.FORCE_OFFLINE");
                sendBroadcast(intent);
            }
        });
    }
}
