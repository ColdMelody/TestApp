package com.demos.testapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.demos.testapp.R;
import com.demos.testapp.bean.NetImageResult;
import com.demos.testapp.imageloader.NetUtil;
import com.demos.testapp.view.ImageAdapter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UilActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uil);
        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        String word = "美女";
        try {
            word = URLEncoder.encode(word, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = "http://pic.sogou.com/pics?start=0&reqType=ajax&reqFrom=result&query=" + word;
        NetUtil.getRequest(url, NetImageResult.class, new NetUtil.BeanCallback<NetImageResult>() {

            @Override
            public void onSuccess(NetImageResult response) {
                adapter = new ImageAdapter(response.getItems(), 2);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onError(Exception exception, String errorInfo) {
                adapter = new ImageAdapter(null, 2);
                recyclerView.setAdapter(adapter);
            }
        });
    }
}

