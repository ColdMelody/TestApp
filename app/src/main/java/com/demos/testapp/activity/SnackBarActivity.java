package com.demos.testapp.activity;

import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.demos.testapp.R;

public class SnackBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snack_bar);
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.cdyt_show_snackbar);
        assert coordinatorLayout != null;
        Snackbar snackbar=Snackbar.make(coordinatorLayout,"Snackbartest",Snackbar.LENGTH_LONG).setAction("SnachAction", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SnackBarActivity.this,"你点击了Snackbar",Toast.LENGTH_LONG).show();
            }
        }).setCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                super.onDismissed(snackbar, event);
                Toast.makeText(SnackBarActivity.this,"Snackbar消失了",Toast.LENGTH_LONG).show();
            }
        });
        snackbar.getView().setBackgroundResource(R.color.color_bg);
        ((TextView)snackbar.getView().findViewById(R.id.snackbar_text)).setTextColor(Color.WHITE);
        snackbar.setActionTextColor(Color.GREEN);
        snackbar.show();
    }
}
