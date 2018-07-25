package com.study.yuyong.activitytest;
/**
 * @date on 2018/7/16
 * @author yuyong
 * @Email yu1183688986@163.com
 * @describe 定义一个服务
 */
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
// 服务创建时启动
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyService", "onCreate executed ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyService", "onDestroy executed");
    }
//服务启东时候调用
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MyService", "onStartCommand executed");
        return super.onStartCommand(intent, flags, startId);
    }
}
