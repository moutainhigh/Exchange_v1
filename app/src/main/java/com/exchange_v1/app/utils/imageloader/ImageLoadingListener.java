package com.exchange_v1.app.utils.imageloader;

import android.graphics.Bitmap;
import android.view.View;

/**
 * Created by huanghh on 2017/3/22.
 */

public interface ImageLoadingListener {

    void onLoadingStarted(String imageUri, View view);
    void onLoadingFailed(final String imageUri, final View view, FailReason failReason);

    void onLoadingComplete(String imageUri, View view, Bitmap loadedImage);

    void onLoadingCancelled(String imageUri, View view);

}
