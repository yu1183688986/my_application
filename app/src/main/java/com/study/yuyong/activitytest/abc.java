package com.study.yuyong.activitytest;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class abc extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abc_layout);
        //获取firstActivity的数据内容
        Intent intent = getIntent();//获取启动 “abc” 的 Intent
        String data = intent.getStringExtra("extra_data");
        TextView textView = findViewById(R.id.textView2);
        textView.setTextSize(25);
        textView.setTextColor(Color.RED);
        textView.setText(data);
       // Log.d("abc",data);
        Button button2 = findViewById(R.id.button_2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //使用显式Intent
                //Intent intent_abc = new Intent(abc.this,FirstActivity.class);
                //startActivity(intent_abc);
                //返回数据给活动启动者
                Intent returnIntent = new Intent();//仅仅当做传递数据用
                returnIntent.putExtra("data_return","Hello FirstActivity");
                setResult(RESULT_OK,returnIntent);
                finish();
            }
        });
    }
}
