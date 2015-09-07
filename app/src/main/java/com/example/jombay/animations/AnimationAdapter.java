package com.example.jombay.animations;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jombay on 4/9/15.
 */
public class AnimationAdapter extends BaseAdapter {

    ArrayList<Animation> myList = new ArrayList<Animation>();
    LayoutInflater inflater;
    private Context context;


    public AnimationAdapter(Context context, ArrayList<Animation> myList) {
        this.myList = myList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Animation getItem(int position) {
        return myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_list_item, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        Animation currentListData = getItem(position);

        mViewHolder.tvTitle.setText(currentListData.getTitle());


        return convertView;
    }

    private class MyViewHolder {
        TextView tvTitle;

        public MyViewHolder(View item) {
            tvTitle = (TextView) item.findViewById(R.id.animationTitle);
        }
    }
}
