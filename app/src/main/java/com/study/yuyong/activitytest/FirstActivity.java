package com.study.yuyong.activitytest;
/**
 * @date on 2018/7/11
 * @author yuyong
 * @Email yu1183688986@163.com
 * @describe
 */
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FirstActivity extends BaseActivity {
    private EditText accountEdit;//定义账户名编辑框

    private EditText passwoordEdit;//定义密码编辑框

    private SharedPreferences preferences;//用SharedPreferences来保存用户名和密码

    private SharedPreferences.Editor editor;

    private CheckBox rememberPass;//定义复选框来确定是否需要记住密码

    private Button login;//定义登录按钮
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);
        Button button1 = findViewById(R.id.button_1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Toast.makeText(FirstActivity.this,"hello world",Toast.LENGTH_SHORT).show();
             //   finish();
                //跳转abc
              //  Intent intent = new Intent(FirstActivity.this,abc.class);
                //使用隐式Intent
                String data = "Hello abc Activity";
                Intent intent = new Intent("com.study.yuyong.activitytest.ACTION_START");
                intent.putExtra("extra_data",data);//键，值
               // startActivity(intent);
                startActivityForResult(intent,1);
            }
        });
        //设置打开浏览器按钮
        Button browser = (Button) findViewById(R.id.browser_button);
        browser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //作用：用Intent打开浏览器
                //Intent intent = new Intent(Intent.ACTION_VIEW);
                Intent intent = new Intent(FirstActivity.this,ThirdActivity.class);
                //intent.setData(Uri.parse("http://www.baidu.com"));
               //作用：用Intent调用系统拨号界面
               /* Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("tel:10086"));*/
                startActivity(intent);
            }
        });
        //设置发送自定义广播的按钮
        Button sendBroadcast = (Button) findViewById(R.id.button);
        sendBroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.study.yuyong.activitytest.My_BROADCAST");
                intent.putExtra("broadcast","hello this is yuyong's broadcast");
               // sendBroadcast(intent);
                //发送有序广播
                sendOrderedBroadcast(intent,null);
            }
        });
        //模拟一个登录界面
        accountEdit = (EditText) findViewById(R.id.account);
        passwoordEdit = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        //得到SharedPreferences对象
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        rememberPass = (CheckBox) findViewById(R.id.remember_pass);//得到复选框对象
        //定义复选框的选中状态，开始默认不选中
        final boolean isRemember = preferences.getBoolean("remember_password",false);
        //如果被选中则将密码和账号设置到文本框中
        if (isRemember){
            String account = preferences.getString("account","");
            String password = preferences.getString("password","");
            accountEdit.setText(account);
            passwoordEdit.setText(password);
            rememberPass.setChecked(true);
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取输入的账户名和密码
                String account = accountEdit.getText().toString();
                String password = passwoordEdit.getText().toString();
                //判断如果账户名是admin密码是admin就认为登录成功
                if ("admin".equals(account) && ("admin".equals(password))){
                    //检查复选框是否被选中，若被选中则记录账号和密码
                    editor = preferences.edit();
                    if (rememberPass.isChecked()){
                        editor.putString("account",account);
                        editor.putString("password",password);
                        editor.putBoolean("remember_password",true);
                    }else {
                        editor.clear();
                    }
                    editor.apply();
                    //登录成功则界面跳转至FirstActivity
                    Intent intent = new Intent(FirstActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(FirstActivity.this,"account or password is invalid"
                            ,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_item:
                Toast.makeText(this, "添加", Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                Toast.makeText(this, "删除", Toast.LENGTH_SHORT).show();
                break;
            case R.id.exit_item:
                finish();
                break;
            default:
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK){
                    String returnData = data.getStringExtra("data_return");
                    TextView textView = findViewById(R.id.textView);
                    textView.setTextColor(Color.GREEN);
                    textView.setTextSize(20);
                    textView.setText(returnData);
                    //Log.d("FirstActivity",returnData);
                }
                break;
            default:
        }
    }
}
