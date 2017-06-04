package com.demos.testapp.view;

/**
 * Created by Bill on 2017/1/11.
 * Email androidBaoCP@163.com
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.demos.testapp.R;

import java.util.List;


/*
    带ViewHolder的模板，目的用于提高ListView效率。
	本模式充分利用ListView的视图缓存机制，可以大大提高ListView的效率
*/
public class ViewHolderAdapter extends BaseAdapter {
    private List<String> mData;
    private LayoutInflater mInflater;

    public ViewHolderAdapter(Context content, List<String> data) {
        this.mData = data;
        mInflater = LayoutInflater.from(content);
    }
    public void setmData(List<String> data){
        this.mData=data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        //判断是否为缓存
        if (convertView == null) {
            holder = new ViewHolder();
            //通过LayoutIflater实例化布局
            convertView = mInflater.inflate(R.layout.listview_content, null);
            holder.title = (TextView) convertView.findViewById(R.id.list_content);
            //将holder装入convertView的Tag中
            convertView.setTag(holder);
        } else {
            //通过tag找到缓存中的布局
            holder = (ViewHolder) convertView.getTag();
        }
        //设置布局中药显示的视图
        holder.title.setText(mData.get(position));
        return convertView;
    }

    public final class ViewHolder {
        public TextView title;
    }

}
