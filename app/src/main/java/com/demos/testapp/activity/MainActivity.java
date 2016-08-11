package com.demos.testapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.demos.testapp.R;

public class MainActivity extends AppCompatActivity {

    private class Sample {
        private final CharSequence title;
        private final Class<? extends Activity> aClass;

        public Sample(int titleResId, Class<? extends Activity> activityClass) {
            this.title = getResources().getString(titleResId);
            this.aClass = activityClass;
        }

        @Override
        public String toString() {
            return title.toString();
        }
    }

    private static Sample[] mSamples;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView mLvToAcv = (ListView) findViewById(R.id.lv_to_acv);
        mSamples = new Sample[]{new Sample(R.string.first_in, SnackBarActivity.class),
                new Sample(R.string.qrcode, QRCodeActivity.class),
                new Sample(R.string.scroller, ScrollerActivity.class),
                new Sample(R.string.str_matrix, MatrixActivity.class),
                new Sample(R.string.str_cache, CacheActivity.class),
                new Sample(R.string.str_bro, BrocastActivity.class),
                new Sample(R.string.str_anim, AnimationActivity.class),
                new Sample(R.string.str_uil,UilActivity.class),
                new Sample(R.string.str_webview,WebActivity.class),
                new Sample(R.string.title_activity_pull_to_rresh,PullToRreshActivity.class)};
        if (mLvToAcv != null) {
            mLvToAcv.setAdapter(new ArrayAdapter<>(this, R.layout.listview_content,
                    R.id.list_content, mSamples));
        }
        if (mLvToAcv != null) {
            mLvToAcv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    startActivity(new Intent(MainActivity.this, mSamples[position].aClass));
                }
            });
        }
    }


}
