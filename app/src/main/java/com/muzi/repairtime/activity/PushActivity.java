package com.muzi.repairtime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.muzi.repairtime.R;

public class PushActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);

        textView = findViewById(R.id.text);

        Intent intent = getIntent();
        // 通知标题
        String title = intent.getStringExtra("title");
        // 通知内容
        String summary = intent.getStringExtra("summary");
        // 通知额外参数
        String extraMap = intent.getStringExtra("extraMap");

        textView.setText(title + "\n" + summary + "\n" + extraMap);
    }
}
