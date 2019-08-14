package com.exchange_v1.app.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.widget.TextView;

/**textview 显示富文本
 * @Description:
 * @author: zhuwd
 * @date: 2017/9/15
 * @Copyright: Copyright (c) 2017 深圳光汇云油电商有限公司.
 */
public class TVHtmlUtil implements Html.ImageGetter{

    TextView mTextView;

    public TVHtmlUtil(TextView textView) {
        this.mTextView = textView;
    }

    @Override
    public Drawable getDrawable(String source) {
        final URLDrawable urlDrawable = new URLDrawable();
//        ImageLoaderUtil.showImage();
//        ImageLoader.getInstance().loadImage( source, new SimpleImageLoadingListener() {
//            @Override
//            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                urlDrawable.bitmap = loadedImage;
//                urlDrawable.setBounds(0, 0, loadedImage.getWidth(), loadedImage.getHeight());
//                mTextView.invalidate();
//                mTextView.setText(mTextView.getText()); // 解决图文重叠
//            }
//        });


        return urlDrawable;
    }
    public class URLDrawable extends BitmapDrawable {
        protected Bitmap bitmap;

        @Override
        public void draw(Canvas canvas) {
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, 0, 0, getPaint());
            }
        }
    }

}
