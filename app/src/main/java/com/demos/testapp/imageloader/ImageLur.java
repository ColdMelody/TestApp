package com.demos.testapp.imageloader;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by peng on 2016/7/5.
 */
public class ImageLur extends LruCache<String, Bitmap> {
    static int maxMemory = (int) Runtime.getRuntime().maxMemory();
    static int cacheSize = maxMemory / 8;

    public ImageLur() {
        super(cacheSize);
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getByteCount();
    }

    public void addBitmap(String url, Bitmap bitmap) {
        String key = Md5Util.hashKeyForDisk(url);
        if (getBitmap(key) == null) {
            this.put(key, bitmap);
        }
    }

    private Bitmap getBitmap(String key) {
        return this.get(key);
    }

    public Bitmap loadBitmap(String url) {
        String key = Md5Util.hashKeyForDisk(url);
        return getBitmap(key);
    }

}
