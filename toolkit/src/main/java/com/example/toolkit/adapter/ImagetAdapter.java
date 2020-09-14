package com.example.toolkit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;

import com.example.toolkit.R;

import java.util.List;

public class ImagetAdapter extends PagerAdapter {
    List<Integer> mImageList;
    Context mContext;


    public ImagetAdapter(@NonNull Context context, @NonNull List<Integer> data) {
        mContext = context;
        mImageList = data;
    }

    @Override
    public int getCount() {
        return mImageList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_image, container, false);
        ImageView image = view.findViewById(R.id.image);
        image.setImageDrawable(ContextCompat.getDrawable(mContext, mImageList.get(position)));
        container.addView(view);
        return view;
    }
}
