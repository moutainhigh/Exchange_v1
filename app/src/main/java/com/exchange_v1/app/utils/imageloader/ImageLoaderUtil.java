package com.exchange_v1.app.utils.imageloader;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.exchange_v1.app.utils.HandlerUtil;
import com.exchange_v1.app.utils.imageloader.imp.GlideStrategy;


/**
 * Created by chentd on 2017/3/9.
 * 图片框架的管理类,现在默认使用Glide框架.
 */

public class ImageLoaderUtil {
    private volatile static ImageLoaderUtil INSTANCE;
    private ImageStrategy imageLoader;
    private static ImageOptions defaultOptions;

    private ImageLoaderUtil() {
        //默认使用Glide
        imageLoader = new GlideStrategy();
    }

    //双重检查加锁
    private static ImageLoaderUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (ImageLoaderUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ImageLoaderUtil();
                }
            }
        }
        return INSTANCE;
    }

    private static ImageLoaderUtil with() {
        return ImageLoaderUtil.getInstance();
    }

    //初始化一些默认配置
    public static void init() {
        init(defaultOptions);
    }

    public static void init(ImageOptions options) {
        if (options == null) {
            defaultOptions = new ImageOptions.Builder().build();
        }
        defaultOptions = options;
    }

    //TODO 需要补充的方法
    public static void showImage(@NonNull String mUrl, @NonNull View mView, boolean isFromNet) {
        showImage(mView, mUrl);
    }

    public static void showImage(@NonNull String mUrl, @NonNull View mView) {
        showImage(mView, mUrl);
    }

    public static void showImage(@NonNull View mView, @NonNull String mUrl) {

        showImage(mView, mUrl, defaultOptions);
    }

    public static void showImage(@NonNull String mUrl, @NonNull View mView, @Nullable ImageOptions options) {
        showImage(mView, mUrl, options);
    }

    public static void showImage(@NonNull View mView, @NonNull int mDraeable) {
        showImage(mView, mDraeable, defaultOptions);
    }

    public static void showImage(@NonNull View mView, @NonNull String mUrl, @Nullable ImageOptions options) {
        showImage(mView, mUrl, options, null);
    }

    public static void showImage(@NonNull View mView, @NonNull int mDraeable, @Nullable ImageOptions options) {
        showImage(mView, mDraeable, options, null);
    }

    public static void showImage(@NonNull String mUrl, @NonNull View mView
            , @Nullable ImageOptions options, ImageLoadingListener imageLoadingListener) {
        showImage(mView, mUrl, options, imageLoadingListener);
    }

    /**
     * 避免出现Glide加载图片缓存库出现——You cannot start a load for a destroyed activity
     * 所以在主线程加载
     * @param mView
     * @param mUrl
     * @param options
     * @param imageLoadingListener
     */
    public static void showImage(final @NonNull View mView, final @NonNull String mUrl
            , final @Nullable ImageOptions options, final ImageLoadingListener imageLoadingListener) {
        if (mView != null && mUrl != null) {
            HandlerUtil.runOnUI(new Runnable() {
                @Override
                public void run() {
                    with().imageLoader.showImage(mView, mUrl, options, imageLoadingListener);
                }
            });

        }
    }

    /**
     * 避免出现Glide加载图片缓存库出现——You cannot start a load for a destroyed activity
     * 所以在主线程加载
     * @param mView
     * @param mDraeable
     * @param options
     * @param imageLoadingListener
     */
    public static void showImage(final @NonNull View mView, final @NonNull int mDraeable
            , final @Nullable ImageOptions options, final ImageLoadingListener imageLoadingListener) {
        if (mView != null) {
            HandlerUtil.runOnUI(new Runnable() {
                @Override
                public void run() {
                    with().imageLoader.showImage(mView, mDraeable, options, imageLoadingListener);
                }
            });
        }
    }

}
