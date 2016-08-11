package com.demos.testapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.demos.testapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PullToRreshActivity extends AppCompatActivity {
    ListView listView;
    SwipeRefreshLayout refreshLayout;
    List<String> list;
    static final int REFRESH=1;
    ArrayAdapter<String> adapter;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case REFRESH:
                    list.addAll(Arrays.asList("Java","Python","Ruby"));
                    adapter.notifyDataSetChanged();
                    refreshLayout.setRefreshing(false);
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_to_rresh);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        initData();
        initView();
    }
    private void initData(){
        list=new ArrayList<>();
        for (int i=0;i<10;i++)
            list.add("list "+i);
    }
    private void initView(){
        listView= (ListView) findViewById(R.id.lv_refresh);
        refreshLayout= (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        adapter=new ArrayAdapter<>(this,R.layout.listview_content,R.id.list_content,list);
        listView.setAdapter(adapter);
        refreshLayout.setColorSchemeColors(R.color.theme_color);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.sendEmptyMessage(REFRESH);
            }
        });
    }

}
