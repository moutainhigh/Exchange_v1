package com.exchange_v1.app.utils.imageloader.imp;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.exchange_v1.app.utils.imageloader.ImageLoadingListener;
import com.exchange_v1.app.utils.imageloader.ImageOptions;
import com.exchange_v1.app.utils.imageloader.ImageStrategy;

/**
 * Created by chentd on 2017/3/9.
 */

public class GlideStrategy implements ImageStrategy {

    @Override
    public void showImage(View v, String url, ImageOptions options, ImageLoadingListener imageLoadingListener) {
        if (ActivityUtils.isDestroyed(v)) {
            return;
        }

        if (v instanceof ImageView) {
            //将类型转换为ImageView
            ImageView imageView= (ImageView) v;
            //装配基本的参数
            DrawableTypeRequest dtr = Glide.with(imageView.getContext()).load(url);
            intoView(options, imageView, dtr);
        }
    }

    private void intoView(ImageOptions options, ImageView iv, DrawableTypeRequest dtr) {
        //装配附加参数
        loadOptions(dtr, options).into(iv);
    }

    @Override
    public void showImage(View v, int drawable, ImageOptions options, ImageLoadingListener imageLoadingListener) {
        if (ActivityUtils.isDestroyed(v)) {
            return;
        }

        if (v instanceof ImageView) {
            ImageView imageView= (ImageView) v;
            DrawableTypeRequest dtr = Glide.with(imageView.getContext()).load(drawable);
            intoView(options, imageView, dtr);
        }
    }

    //这个方法用来装载由外部设置的参数
    private DrawableTypeRequest loadOptions(DrawableTypeRequest dtr, ImageOptions options){
        if (options==null) {
            return dtr;
        }
        if (options.getPlaceHolder()!=-1) {
            dtr.placeholder(options.getPlaceHolder());
        }
        if (options.getErrorDrawable()!=-1){
            dtr.error(options.getErrorDrawable());
        }
        if (options.isCrossFade()) {
            dtr.crossFade();
        }
        if (options.isCenterCrop()) {
            dtr.centerCrop();
        }
        if (options.isSkipMemoryCache()){
            dtr.skipMemoryCache(options.isSkipMemoryCache());
        }
        if (options.getSize()!=null) {
            dtr.override(options.getSize().getReWidth(),options.getSize().getReWidth());
        }
        return dtr;
    }

}
