package com.exchange_v1.app.utils.imageloader;

import android.view.View;

/**
 * Created by chentd on 2017/3/9.
 */

public interface ImageStrategy {
    void showImage(View v, String url, ImageOptions options, ImageLoadingListener imageLoadingListener);
    void showImage(View v, int drawable, ImageOptions options, ImageLoadingListener imageLoadingListener);
}
