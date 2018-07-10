package com.study.yuyong.activitytest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

/**
 * @date on 2018/7/10
 * @author yuyong
 * @Email yu1183688986@163.com
 * @describe 该类作为所有活动的父类,并且动态注册一个广播接收器供它的子活动调用
 */
public class BaseActivity extends AppCompatActivity {

    private ForceOfflineReceive receive;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        //提示当前是哪个活动页面
        Toast.makeText(this,getClass().getSimpleName(),Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //动态注册自定义广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.study.yuyong.activitytest.FORCE_OFFLINE");
        receive = new ForceOfflineReceive();
        registerReceiver(receive,intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (receive != null){
            unregisterReceiver(receive);
            receive = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
    /**
     * 强制下线广播接收功能的内部类
     */
    class ForceOfflineReceive extends BroadcastReceiver{
        @Override
        public void onReceive(final Context context, Intent intent) {
            //创建一个获取当前活动焦点的警示对话框
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("warning");
            builder.setMessage("You are forced to be offline. Please try to login again.");
            builder.setCancelable(false);//设置对话框不可取消
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCollector.finishAll();//销毁所有活动
                    //重启登录活动页面
                    Intent intent = new Intent(context,FirstActivity.class);
                    context.startActivity(intent);
                }
            });
            builder.show();
        }
    }
}
