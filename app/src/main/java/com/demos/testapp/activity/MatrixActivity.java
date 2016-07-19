package com.demos.testapp.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.demos.testapp.R;

/**
 * Created by peng on 2016/6/13.
 */
public class MatrixActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    private ImageView imageView;
    private SeekBar seekBarHue;
    private SeekBar seekBarSaturation;
    private SeekBar seekBarLum;
    //色调
    private float mHue;
    //饱和度
    private float mSaturation;
    //亮度
    private float mLum;
    private Bitmap bitmap;
    private static final float MID_VALUE = 105.5f;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix);
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.cat);
        initView();
        initListener();


    }

    private void initView() {
        imageView = (ImageView) findViewById(R.id.iv_matrix);
        seekBarHue = (SeekBar) findViewById(R.id.sb_hue);
        seekBarSaturation = (SeekBar) findViewById(R.id.sb_saturation);
        seekBarLum = (SeekBar) findViewById(R.id.sb_lum);
    }
    private void initListener(){
        seekBarHue.setOnSeekBarChangeListener(this);
        seekBarSaturation.setOnSeekBarChangeListener(this);
        seekBarLum.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.sb_hue:
                mHue = (progress - MID_VALUE) * 1.0f / MID_VALUE * 180;
                break;
            case R.id.sb_saturation:
                mSaturation = progress * 1.0f / MID_VALUE;
                break;
            case R.id.sb_lum:
                mLum = progress * 1.0f / MID_VALUE;
                break;
        }
        imageView.setImageBitmap(ImageHelper.handleImageEffect(bitmap,mHue,mSaturation,mLum));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

}
