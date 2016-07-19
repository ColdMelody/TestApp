package com.demos.testapp.activity;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.demos.testapp.R;

import com.demos.testapp.imageloader.JUtils;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.File;


public class APP extends Application{
  //  private int screenWidth;
    @Override
    public void onCreate() {
        super.onCreate();
        JUtils.initialize(this);
        JUtils.setDebug(true, "debug");

        initUil();
    }
    private void initUil(){
        File cacheDir=getFileDir(this,"uilbitmap");
        if (!cacheDir.exists()){
            cacheDir.mkdirs();
        }
        DisplayImageOptions options=new DisplayImageOptions.Builder().showImageOnLoading(R.mipmap.ic_loading)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(false)
                .imageScaleType(ImageScaleType.EXACTLY)
                .build();
        ImageLoaderConfiguration configuration=new ImageLoaderConfiguration.Builder(this)
                .diskCache(new UnlimitedDiskCache(cacheDir))
                .defaultDisplayImageOptions(options)
                .denyCacheImageMultipleSizesInMemory()
                .threadPoolSize(5)
                .memoryCache(new WeakMemoryCache())
                .build();
        ImageLoader.getInstance().init(configuration);
    }
    private File getFileDir(Context context, String url) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + url);
    }
}
