package com.exchange_v1.app.utils.imageloader;

import android.animation.Animator;

/**
 * Created by chentd on 2017/3/9.
 * 该类封装了glide需要的额外相关属性(比如:是否跳过内存缓存或者加载图片错误时显示的图片)
 */
public class ImageOptions {


    private int placeHolder = -1; //当没有成功加载的时候显示的图片
    private int errorDrawable = -1;  //加载错误的时候显示的drawable
    private ImageReSize size = null; //重新设定容器宽高
    private boolean isCrossFade = false; //是否渐变平滑的显示图片
    private boolean isSkipMemoryCache = false; //是否跳过内存缓存
    private Animator animator = null;//是否有动画
    private boolean isCenterCrop = false; //是否进行居中剪切

    public boolean isCenterCrop() {
        return isCenterCrop;
    }

    public Animator getAnimator() {
        return animator;
    }

    public int getPlaceHolder() {
        return placeHolder;
    }

    public ImageReSize getSize() {
        return size;
    }

    public int getErrorDrawable() {
        return errorDrawable;
    }

    public boolean isCrossFade() {
        return isCrossFade;
    }

    public boolean isSkipMemoryCache() {
        return isSkipMemoryCache;
    }


    public ImageOptions(ImageOptions.Builder builder) {
        this.placeHolder = builder.placeHolder;
        this.size = builder.size;
        this.errorDrawable = builder.errorDrawable;
        this.isCrossFade = builder.isCrossFade;
        this.isCenterCrop = builder.isCenterCrop;
        this.isSkipMemoryCache = builder.isSkipMemoryCache;
        this.animator = builder.animator;
    }



    public class ImageReSize {
        int reWidth = 0;
        int reHeight = 0;

        public ImageReSize(int reWidth, int reHeight) {
            if (reHeight <= 0) {
                reHeight = 0;
            }
            if (reWidth <= 0) {
                reWidth = 0;
            }
            this.reHeight = reHeight;
            this.reWidth = reWidth;

        }

        public int getReWidth() {
            return reWidth;
        }

        public void setReWidth(int reWidth) {
            this.reWidth = reWidth;
        }

        public int getReHeight() {
            return reHeight;
        }

        public void setReHeight(int reHeight) {
            this.reHeight = reHeight;
        }
    }

    public static final class Builder {
        private int placeHolder = -1;
        private int errorDrawable = -1;
        private ImageReSize size = null;
        private boolean isCrossFade = false;
        private boolean isCenterCrop = false;
        private boolean isSkipMemoryCache = false;
        private Animator animator = null;

        public Builder() {

        }

        public Builder placeHolder(int drawable) {
            this.placeHolder = drawable;
            return this;
        }

        public Builder reSize(ImageReSize size) {
            this.size = size;
            return this;
        }


        public Builder showImageOnFail(int errorDrawable) {
            this.errorDrawable = errorDrawable;
            return this;
        }

        public Builder showImageForEmptyUri(int errorDrawable) {
            this.errorDrawable = errorDrawable;
            return this;
        }

        public Builder errorDrawable(int errorDrawable) {
            this.errorDrawable = errorDrawable;
            return this;
        }

        public Builder isCrossFade(boolean isCrossFade) {
            this.isCrossFade = isCrossFade;
            return this;
        }

        public Builder isCenterCrop(boolean isCenterCrop) {
            this.isCenterCrop = isCenterCrop;
            return this;
        }

        public Builder isSkipMemoryCache(boolean isSkipMemoryCache) {
            this.isSkipMemoryCache = isSkipMemoryCache;
            return this;
        }

        public Builder animator(Animator animator) {
            this.animator = animator;
            return this;
        }

        public Builder showImageOnLoading(int drawable) {
            this.placeHolder = drawable;
            return this;
        }
        //TODO
        public Builder cacheInMemory(boolean b) {
            return this;
        }
        //TODO
        public Builder cacheOnDisk(boolean b) {
            return this;
        }

        public ImageOptions build() {

            return new ImageOptions(this);
        }


    }
}
