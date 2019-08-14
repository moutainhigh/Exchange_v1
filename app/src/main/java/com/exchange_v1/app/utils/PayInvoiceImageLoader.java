package com.exchange_v1.app.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.ByteArrayOutputStream;


/**
 * 图片设置与下载
 */
public class PayInvoiceImageLoader {
	private static int icon_max_size = 100 * 1024;
	private static BitmapFactory.Options options = new BitmapFactory.Options();
	static{
//		options.inSampleSize = 8;
		options.inSampleSize = 2;
	}
	public static Bitmap compressBitmap(Bitmap image) {
		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, bos);
		int size = bos.toByteArray().length;
		if (size > icon_max_size) {
			float scale = (float) icon_max_size / (float) size;
			// 长*宽，反求，计算大概比例。
			scale = (float) Math.sqrt(scale);
			Matrix matrix = new Matrix();
			matrix.reset();
			matrix.setScale(scale, scale);
			Bitmap bitmap = Bitmap.createBitmap(image, 0, 0, imageWidth,
					imageHeight, matrix, true);
			image.recycle();
			image = null;
			return bitmap;
		} else {
			return image;
		}
	}
	
}
