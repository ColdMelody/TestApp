package com.demos.testapp.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.demos.testapp.R;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by peng on 2016/7/5.
 */
public class ImageLoader {
    @SuppressWarnings("unused")
    private static ImageLoader imageLoader;
    private static ImageLur imageLur;
    private static ImageDisLur imageDisLur;
    private static ThreadPoolExecutor manager;
    private Context mContext;
    private static TaskHandler mMainHandler;

    private ImageLoader(Context context) {
        imageLur = new ImageLur();
        manager = ImageThreadPoolExecutor.getInstance();
        mContext = context.getApplicationContext();
        mMainHandler = new TaskHandler();
    }

    public static ImageLoader getInstance(Context context) {
        if (imageLoader == null) {
            synchronized (ImageLoader.class) {
                if (imageLoader == null) {
                    return new ImageLoader(context);
                }
            }
        }
        return imageLoader;
    }

    public void bindBitmap(String url, ImageView iv, int reqwidth, int reqheight) {
        iv.setBackgroundResource(R.mipmap.ic_loading);
        //防止加载图片混乱
        iv.setTag(url);
        String key = Md5Util.hashKeyForDisk(url);
        Bitmap bitmap = imageLur.loadBitmap(key);
        if (bitmap != null) {
            iv.setImageBitmap(bitmap);
            return;
        }
        LoadBitmaoTask task = new LoadBitmaoTask(mContext, mMainHandler, iv, url, reqwidth, reqheight);
        manager.execute(task);
    }

    public void getBitmap(String url, final BitmapCallback callback, int reqWidth, int reqHeight) {
        String key = Md5Util.hashKeyForDisk(url);
        final Bitmap bitmap = imageLur.loadBitmap(key);
        if (bitmap != null) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onResponse(bitmap);
                }
            });
            return;
        }
        LoadBitmaoTask task = new LoadBitmaoTask(mContext, callback, url, reqWidth, reqHeight);
        //使用线程池去执行Runnable对象
        manager.execute(task);
    }

    public interface BitmapCallback {
        void onResponse(Bitmap bitmap);
    }
    //返回内存缓存类,保持一个对象可以防止加载时的卡顿
    public static ImageLur getImageLrucache() {
        if (imageLur == null) {
            imageLur = new ImageLur();
        }
        return imageLur;
    }

    //返回本地缓存类
    public static ImageDisLur getImageDiskLrucache(Context context) {
        if (imageDisLur == null) {
            imageDisLur = new ImageDisLur(context);
        }
        return imageDisLur;
    }
}
