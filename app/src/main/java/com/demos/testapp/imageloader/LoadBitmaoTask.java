package com.demos.testapp.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import java.io.IOException;

/**
 * Created by peng on 2016/7/5.
 */
public class LoadBitmaoTask implements Runnable {

    public static final int MESSAGE_POST_RESULT = 1;
    private String url;
    private int reqWidth;
    private int reqHeight;
    private Context mContext;
    private Handler mMainHandler;
    private ImageView imageView;
    private ImageLoader.BitmapCallback callback;

    public LoadBitmaoTask(Context context, Handler handler, ImageView imageView, String url, int reqWidth, int reqHeight) {
        this.mMainHandler = handler;
        this.imageView = imageView;
        this.url = url;
        this.reqWidth = reqWidth;
        this.reqHeight = reqHeight;
        mContext=context.getApplicationContext();
    }

    public LoadBitmaoTask(Context context, ImageLoader.BitmapCallback callback, String url, int reqWidth, int reqHeight) {
        this.callback = callback;
        this.url = url;
        this.reqHeight = reqHeight;
        this.reqWidth = reqWidth;
        mContext=context.getApplicationContext();
    }

    @Override
    public void run() {
        final Bitmap bitmap = loadBitmap(url, reqWidth, reqHeight);
        if (mMainHandler != null) {
            TaskResult result = new TaskResult(imageView, url, bitmap);
            mMainHandler.obtainMessage(MESSAGE_POST_RESULT, result).sendToTarget();
        }
        if (callback != null) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onResponse(bitmap);
                }
            });
        }
    }

    private Bitmap loadBitmap(String url, int reqWidth, int reqHeight) {
        Bitmap bitmap = null;
        try {
            bitmap = ImageLoader.getImageDiskLrucache(mContext).loadBitmapFromDis(url, reqWidth, reqHeight);
            if (bitmap != null) {
                ImageLoader.getImageLrucache().addBitmap(url, bitmap);
                return bitmap;
            } else {
                bitmap = loadBitmaoFromNet(url, reqWidth, reqHeight);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //本地缓存已满的情况
        if (bitmap == null && ImageLoader.getImageDiskLrucache(mContext).getDisState()) {
            bitmap = NetUtil.downloadBitmapFromUrl(url);
        }
        return bitmap;
    }

    private Bitmap loadBitmaoFromNet(String url, int reqWidth, int reqHeight) throws IOException {
        ImageLoader.getImageDiskLrucache(mContext).downloadImage(url);
        return ImageLoader.getImageDiskLrucache(mContext).loadBitmapFromDis(url, reqWidth, reqHeight);
    }

}
