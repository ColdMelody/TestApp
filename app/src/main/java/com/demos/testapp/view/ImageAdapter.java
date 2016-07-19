package com.demos.testapp.view;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demos.testapp.bean.NetImage;

/**
 * Created by peng on 2016/7/6.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageViewHolder> {
    private NetImage[] data;
    private int layoutManagerType;
    public ImageAdapter(NetImage[] data, int type) {
        this.data = data;
        layoutManagerType = type;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        holder.setLayoutParams(data[position], layoutManagerType);
        holder.setData(data[position], layoutManagerType);
    }

    @Override
    public int getItemCount() {
        if (data==null){
            return 0;
        }
        return data.length;
    }
}
