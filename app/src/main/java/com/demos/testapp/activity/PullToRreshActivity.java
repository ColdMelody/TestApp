package com.demos.testapp.activity;

import android.os.AsyncTask;
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
import android.widget.Toast;

import com.demos.testapp.R;
import com.demos.testapp.utils.Fibonacci;
import com.demos.testapp.utils.ThreadPool;
import com.demos.testapp.view.CustomSwipRefreshLayout;
import com.demos.testapp.view.ViewHolderAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PullToRreshActivity extends AppCompatActivity {
    ListView listView;
    CustomSwipRefreshLayout refreshLayout;
    List<String> list;
    Fibonacci fibonacci;
    ViewHolderAdapter adapter;
    boolean smallToBig = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_to_rresh);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace", Snackbar.LENGTH_LONG)
                        .setAction("Reverse", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                list = reverse(list);
                                smallToBig = !smallToBig;
                                adapter.setmData(list);
                                Toast.makeText(PullToRreshActivity.this, "000", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });
        initData();
        initView();
    }

    private void initData() {
        fibonacci = new Fibonacci();
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            list.add(fibonacci.getFibonacci(i * i));
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.lv_refresh);
        refreshLayout = (CustomSwipRefreshLayout) findViewById(R.id.swipe_layout);
        adapter = new ViewHolderAdapter(this, list);
        listView.setAdapter(adapter);
        refreshLayout.setColorSchemeColors(R.color.theme_color);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MyAsyncTask task = new MyAsyncTask();
                task.execute(1000);
            }
        });
        refreshLayout.setOnLoadListener(new CustomSwipRefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                MyAsyncTask task = new MyAsyncTask();
                task.execute(1000);
            }
        });
    }

    private List<String> reverse(List<String> list) {
        List<String> temp = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            temp.add(list.get(i));
        }
        return temp;
    }

    private class MyAsyncTask extends AsyncTask<Integer, Integer, List<String>> {

        @Override
        protected List<String> doInBackground(Integer... params) {
            List<String> temp = new ArrayList<String>();
            if (smallToBig){
                temp.addAll(list);
            }else {
                temp.addAll(reverse(list));
            }
            for (int i = list.size(); i < list.size() + 20; i++) {
                temp.add(fibonacci.getFibonacci(i * i));
            }
            if (smallToBig){
                return temp;
            }else {
                return reverse(temp);
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(PullToRreshActivity.this, "start computing", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            list.clear();
            list.addAll(strings);
            adapter.setmData(strings);
            refreshLayout.setRefreshing(false);
            refreshLayout.setLoading(false);
        }
    }
}
