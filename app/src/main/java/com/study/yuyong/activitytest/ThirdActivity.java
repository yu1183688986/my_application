package com.study.yuyong.activitytest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ThirdActivity extends BaseActivity {
    /**
     * 动态注册监听网络变化的广播broadcast
     */
    private  EditText editText;
    //得到用户想要访问的网址
    private String url;
    private WebView webView;
    private TextView data_show;
    private Button openURL;
    private Button analysis;
    private  volatile Boolean isSelected = false;//用来监视analysis按钮是否被按下
    //创建广播接收器对象
    private NetworkChangeReceiver networkChangeReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_layout);
        IntentFilter intentFilter = new IntentFilter();
        networkChangeReceiver = new NetworkChangeReceiver();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(networkChangeReceiver,intentFilter);
        editText =  findViewById(R.id.URL);
        //---------------WebView------------
        webView =  findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);//接收js脚本
        webView.setWebViewClient(new WebViewClient());
        openURL =  findViewById(R.id.open_URL);
        //给打开网址按钮定义单机事件
        openURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url = "http://"+editText.getText().toString();
                //webView显示网址
                webView.loadUrl(url);
               // editText.clearFocus();
              //  editText.setFocusable(false);
            }
        });

        //定义解析按钮事件
        analysis = findViewById(R.id.analysis);
        analysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSelected = true;
                sendRequestByOkhttp();
            }
        });
        //定义用于显示服务器返回数据的文本框
        data_show =  findViewById(R.id.dataShow);
        //定义按钮事件用于发送http请求并将服务器返回的数据显示出来
        Button request_Button =  findViewById(R.id.sendToServel);
        request_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // sendRequest();
                sendRequestByOkhttp();
            }
        });
    }

    //使用HttpURLConnection发送http请求
    private void sendRequest(){
        //启动线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    //创建URL对象
                    URL uri = new URL("http://"+editText.getText().toString());
                  //  URL uri = new URL("http://www.baidu.com");
                    connection = (HttpURLConnection) uri.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream inputStream = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line ;
                    while ((line=reader.readLine())!=null){
                        response.append(line);
                    }
                    showResponse(response.toString());

                }catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    //关闭流
                    if (reader!=null){
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
    //使用okhttp发送http请求
    private synchronized void sendRequestByOkhttp(){
        //创建URL对象
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    if (TextUtils.isEmpty(editText.getText().toString())){
                        //Toast.makeText(ThirdActivity.this,"请输入正确的网址",Toast.LENGTH_SHORT).show();
                       editText.setFocusable(true);
                    }else {
                         URL uri = new URL("http://"+editText.getText().toString());
                        Request request = new Request.Builder().url(uri).build();
                        Response response = client.newCall(request).execute();
                        String responseData = response.body().string();
                        if (isSelected){
                            parseXMLWithPull(responseData);
                            isSelected = false;
                        }else{
                            showResponse(responseData);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    // 在UI中显示服务器返回的数据
    private synchronized void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                data_show.setText(response);
            }
        });
    }

    /**
     * 使用Pull解析方式解析从tomcat服务器中的get_data.xml文件
     */
    private  void parseXMLWithPull(String xmlData){
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));
            //得到当前解析事件
            int eventType = xmlPullParser.getEventType();
            String id = "";
            String name = "";
            String version = "";
            StringBuilder builder = new StringBuilder();
            builder.append("Pull解析结果是："+"\n");
            while (eventType != XmlPullParser.END_DOCUMENT){
                String nodeName = xmlPullParser.getName();
                switch (eventType){
                    //开始解析某个节点
                    case XmlPullParser.START_TAG:{
                        if ("id".equals(nodeName)){
                            id = xmlPullParser.nextText();
                        }else if ("name".equals(nodeName)){
                            name = xmlPullParser.nextText();
                        }else if ("version".equals(nodeName)){
                            version = xmlPullParser.nextText();
                        }
                        break;
                    }
                    //完成解析某个节点
                    case XmlPullParser.END_TAG:{
                        if ("app".equals(nodeName)){
                            builder.append("id:"+id+"\n"+
                                    "name:"+name+"\n"+
                                    "version:"+version+"\n");
                        }
                        break;
                    }
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }
            showResponse(builder.toString());
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 使用Pull解析方式解析从tomcat服务器中的get_data.xml文件
     */

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }
    //定义内部类用于接收广播
    class NetworkChangeReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            //需要声明访问网络的权限在AndroidManifest.xml中,获取手机当前网络接口状态
            NetworkInfo networkInfo = null;
            if (connectivityManager != null) {
                networkInfo = connectivityManager.getActiveNetworkInfo();
            }
            if (networkInfo != null && networkInfo.isConnected()){
                Toast.makeText(context,"network is available",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context,"network is unavailable",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
