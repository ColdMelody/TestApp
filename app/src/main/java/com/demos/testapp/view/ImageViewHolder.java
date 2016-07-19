package com.demos.testapp.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.demos.testapp.R;
import com.demos.testapp.bean.NetImage;
import com.demos.testapp.imageloader.ImageLoader;
import com.demos.testapp.imageloader.JUtils;

/**
 * Created by peng on 2016/7/6.
 */
public class ImageViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;
    private float screenWidth;
    private int height;
    private float width;
    private ViewGroup.LayoutParams layoutParams;
    private Context context;

    public ImageViewHolder(ViewGroup root) {
        super(LayoutInflater.from(root.getContext()).inflate(R.layout.image_view_llyt, root, false));
        imageView = (ImageView) itemView.findViewById(R.id.itemview_img);
        context = root.getContext();
        screenWidth = JUtils.getScreenWidth();
        layoutParams = imageView.getLayoutParams();
    }

    //绑定数据
    public void setData(NetImage netImage, int layoutType) {
//        //垂直线性布局
//        if (layoutType == 1) {
//            //加载高清图片并按宽高压缩
//            ImageLoader.getInstance(context)
//                    .bindBitmap(netImage.getPic_url_noredirect(), imageView, (int) width, height);
//        }//错位式布局模式
//        else if (layoutType == 2) {
//            //加载小图片
//            ImageLoader.getInstance(context).bindBitmap(netImage.getThumbUrl(), imageView, (int) width, height);
//        }
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(netImage.getPic_url_noredirect(),imageView);
    }

    //这个会先执行
    public void setLayoutParams(NetImage netImage, int layoutType) {
        //垂直线性布局
        if (layoutType == 1) {
            width = screenWidth;
        }//错位式布局
        else if (layoutType == 2) {
            width = screenWidth / 2;
        }
        //图片根据频幕宽度等比缩放
        height = (int) (netImage.getThumb_height() * (width / netImage.getThumb_width()));
        layoutParams.width = (int) width;
        layoutParams.height = height;
        imageView.setLayoutParams(layoutParams);
    }

}
