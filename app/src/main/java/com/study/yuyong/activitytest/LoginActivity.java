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

    private MyDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        /**
         * 发送强制下线广播
         */
        Button forceOffline = (Button) findViewById(R.id.force_offline);
        forceOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.study.yuyong.activitytest.FORCE_OFFLINE");
                sendBroadcast(intent);
            }
        });
        /**
         * 用于创建数据库的按钮
         */
        //指定数据库路径,还需要给他删除的权限等
       // final String name = "/mnt/sdcard/temp/BookStore.db";
        final String name = "BookStore_1.db";
        //修改参数version可以调用onUpgrade（）方法对数据库进行升级
        databaseHelper = new MyDatabaseHelper(this,name,null,2);
        Button createDatabase = (Button) findViewById(R.id.create_database);
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper.getWritableDatabase();//打开数据库若不存在则创建
            }
        });

        //创建按钮用于开启一个服务
        final Button startService = findViewById(R.id.myService);
        startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(LoginActivity.this,MyService.class);
                startService(startIntent);//开启服务
            }
        });
        //创建按钮用于关闭服务
        final Button stopService = findViewById(R.id.stopService);
        stopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stopIntent = new Intent(LoginActivity.this,MyService.class);
                stopService(stopIntent);//停止服务
            }
        });
    }
}
