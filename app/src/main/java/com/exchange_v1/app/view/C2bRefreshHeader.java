package com.exchange_v1.app.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.exchange_v1.R;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.internal.InternalClassics;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;


/**
 * Created by zhuwd on 2018/4/27.
 *
 * SmartRefreshLayout  C2B下拉刷新头部
 */

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class C2bRefreshHeader extends InternalClassics<ClassicsHeader> implements RefreshHeader {

    public C2bRefreshHeader(Context context) {
        this(context, null);
    }

    public C2bRefreshHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("ResourceType")
    public C2bRefreshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final DensityUtil density = new DensityUtil();

        LayoutParams layoutParams = new LayoutParams(WRAP_CONTENT, density.dip2px(60));
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(layoutParams);
        //加载帧动画
        imageView.setImageResource(R.drawable.bga_refresh_loding_c2b);
//        imageView.setBackgroundColor(0xffc8c8c8);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();
        animationDrawable.start();

        mCenterLayout.addView(imageView);
    }
}
