package com.demos.testapp.imageloader;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by peng on 2016/7/5.
 */
public class ImageDisLur {
    DiskLruCache diskLruCache;
    boolean isDisLurCreated;

    static final long DISK_CACHE_SIZE = 1024 * 1024 * 50;

    public ImageDisLur(Context context) {
        File file = getFileDir(context, "bitmap");
        if (!file.exists()) {
            file.mkdirs();
        }
        getDiskLruCache(file);
    }

    /**
     * 获取DiskLurCache的实例
     */
    private DiskLruCache getDiskLruCache(File file) {
        if (getUseableSpace(file) > DISK_CACHE_SIZE) {
            try {
                diskLruCache = DiskLruCache.open(file, 1, 1, DISK_CACHE_SIZE);
                isDisLurCreated = true;
            } catch (IOException e) {
                isDisLurCreated = false;
            }
        }else {
            isDisLurCreated=false;
        }
        return diskLruCache;
    }

    /**
     * 从本地缓存中获取bitmap
     */
    public Bitmap loadBitmapFromDis(String url, int width, int height) throws IOException {
        if (diskLruCache == null) {
            return null;
        }
        Bitmap bitmap = null;
        String key = Md5Util.hashKeyForDisk(url);
        DiskLruCache.Snapshot snapshot = diskLruCache.get(key);
        if (snapshot != null) {
            FileInputStream fis = (FileInputStream) snapshot.getInputStream(0);
            if (width <= 0 || height <= 0) {
                bitmap = BitmapFactory.decodeFileDescriptor(fis.getFD());
            } else {
                bitmap = BitmapUtils.getSmallBitmap(fis.getFD(), width, height);
            }
        }
        return bitmap;
    }


    /**
     *下载图片到本地缓存中
     */
    public void downloadImage(String url) throws IOException {
        if (diskLruCache==null){
            return;
        }
        String key=Md5Util.hashKeyForDisk(url);
        DiskLruCache.Editor editor=diskLruCache.edit(key);
        if (editor!=null){
            OutputStream os=editor.newOutputStream(0);
            if (NetUtil.downloadUrlToStream(url,os)){
                editor.commit();
            }else {
                editor.abort();
            }
            diskLruCache.flush();
        }
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

    private int getVision(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 1;
        }
    }

    private long getUseableSpace(File path) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return path.getUsableSpace();
        }
        return 0;
    }
    public boolean getDisState(){
        return isDisLurCreated;
    }
}
