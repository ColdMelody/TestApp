package com.demos.testapp.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.demos.testapp.R;
import com.demos.testapp.brocast.MyReceiver;

/**
 * Created by peng on 2016/7/13.
 */
public class BrocastActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brocast);
        //动态注册广播
        MyReceiver receiver=new MyReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction("android.intent.action.MYBROCAST");
        registerReceiver(receiver,filter);


        Button btnSendBro= (Button) findViewById(R.id.send_bro);
        btnSendBro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBro();
            }
        });
    }
    private void sendBro(){
        Intent intent=new Intent("android.intent.action.MYBROCAST");
        intent.putExtra("msg","this is brocast from MyBrocast");
        sendBroadcast(intent);
    }
}
