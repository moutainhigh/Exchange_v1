package com.exchange_v1.app.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * 用于将图片下载框架切换
 * 
 * @Description
 * @author 綦巍
 * @date 2015-4-23 18:02:00
 * @Copyright: Copyright (c) 2015 深圳光汇云油电商有限公司.
 */
public interface ImgLoader {
	/**
	 * 设置头像默认的图片
	 * 
	 * @createTime 2015-4-23,下午6:03:17
	 * @updateTime 2015-4-23,下午6:03:17
	 * @createAuthor 綦巍
	 * @updateAuthor 綦巍
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param image
	 */
	public void setPortraitDefaul(ImageView image);

	/**
	 * 设置默认的图片
	 * 
	 * @createTime 2015-4-23,下午6:03:17
	 * @updateTime 2015-4-23,下午6:03:17
	 * @createAuthor 綦巍
	 * @updateAuthor 綦巍
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param image
	 */
	public void setDefault(ImageView image);

	/**
	 * 设置圆角,像素将去除
	 * 
	 * @createTime 2015-4-23,下午6:05:19
	 * @updateTime 2015-4-23,下午6:05:19
	 * @createAuthor 綦巍
	 * @updateAuthor 綦巍
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param percentX 圆角弧度百分比
	 */
	public void setPercentX(float percentX);

	/**
	 * 设置圆角像素,百分比将去除
	 * 
	 * @createTime 2015-4-23,下午6:05:19
	 * @updateTime 2015-4-23,下午6:05:19
	 * @createAuthor 綦巍
	 * @updateAuthor 綦巍
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param pxRadius 圆角弧度像素
	 */
	public void setPxRadius(int pxRadius);

	/**
	 * 是否获取中间正方形图片
	 * 
	 * @createTime 2015-4-23,下午6:05:19
	 * @updateTime 2015-4-23,下午6:05:19
	 * @createAuthor 綦巍
	 * @updateAuthor 綦巍
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param isMiddle 默认false
	 */
	public void setMiddle(boolean isMiddle);

	/**
	 * 设置错误图片
	 * 
	 * @createTime 2015-4-23,下午6:05:36
	 * @updateTime 2015-4-23,下午6:05:36
	 * @createAuthor 綦巍
	 * @updateAuthor 綦巍
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param image
	 */
	public void setError(ImageView image);

	public void setNeedAdd(boolean isNeedAdd);

	/**
	 * 下载图片
	 * 
	 * @createTime 2015-4-23,下午6:05:53
	 * @updateTime 2015-4-23,下午6:05:53
	 * @createAuthor 綦巍
	 * @updateAuthor 綦巍
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param context 上下文
	 * @param position 位置,用于图片错位处理
	 * @param imagePath 图片路径
	 * @param callback 图片下载完成后回调
	 * @return bitmap
	 */
	public Bitmap loadImageBitmap(final Context context, final int position, final String imagePath, final ImageCallback callback);

	// * @deprecated use loadImageBitmap(final ImageView imageView, final int position, final String imagePath);
	// @Deprecated

	/**
	 * 下载图片
	 * 
	 * @createTime 2015-4-23,下午6:05:53
	 * @updateTime 2015-4-23,下午6:05:53
	 * @createAuthor 綦巍
	 * @updateAuthor 綦巍
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param context 上下文
	 * @param position 位置,用于图片错位处理
	 * @param imagePath 图片路径
	 * @param localPath 保存本地路径
	 * @param callback 图片下载完成后回调
	 * @return bitmap
	 */
	public Bitmap loadImageBitmap(final Context context, final int position, final String imagePath, final String localPath,
                                  final ImageCallback callback);

	/**
	 * 下载图片
	 *
	 * @createTime 2015-4-23,下午6:05:53
	 * @updateTime 2015-4-23,下午6:05:53
	 * @createAuthor 綦巍
	 * @updateAuthor 綦巍
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param context 上下文
	 * @param position 位置,用于图片错位处理
	 * @param imagePath 图片路径
	 * @param localPath 保存本地路径
	 * @param callback 图片下载完成后回调
	 * @param isVideo 是否是视频
	 * @return bitmap
	 */
	public Bitmap loadImageBitmapByVideo(final Context context, final ImageView image, final int position, final String imagePath,
                                         final String localPath, final ImageCallback callback, boolean isVideo);

	/**
	 * 下载图片
	 *
	 * @createTime 2015-4-23,下午6:05:53
	 * @updateTime 2015-4-23,下午6:05:53
	 * @createAuthor 綦巍
	 * @updateAuthor 綦巍
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param context 上下文
	 * @param position 位置,用于图片错位处理
	 * @param imagePath 图片路径
	 * @param localPath 保存本地路径
	 * @param callback 图片下载完成后回调
	 * @param isMediaPhoto 是否媒体图片
	 * @return bitmap
	 */
	public Bitmap loadImageBitmap(final Context context, final ImageView image, final int position, final int photoId, final String localPath,
                                  final ImageCallback callback, boolean isMediaPhoto);

	/**
	 * 下载图片,这里的str参数没有作用,目测是前人来避免方法参数重名
	 *
	 * @createTime 2015-4-23,下午6:11:52
	 * @updateTime 2015-4-23,下午6:11:52
	 * @createAuthor 綦巍
	 * @updateAuthor 綦巍
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param context 上下文
	 * @param image 设置图片的控件
	 * @param position 位置,用于图片错位处理
	 * @param imagePath 图片路径
	 * @param localPath 保存本地路径
	 * @param callback 图片下载完成后回调
	 * @param isPortrait 是否是头像，true是，false不是
	 * @return bitmap
	 */
	public Bitmap loadImageBitmap(final Context context, final ImageView image, final int position, final String imagePath, final String localPath,
                                  final ImageCallback callback, final boolean isPortrait, final String str);

	/**
	 * 下载图片(本地下载)
	 *
	 * @createTime 2015-4-23,下午6:11:52
	 * @updateTime 2015-4-23,下午6:11:52
	 * @createAuthor 綦巍
	 * @updateAuthor 綦巍
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param context 上下文
	 * @param image 设置图片的控件
	 * @param position 位置
	 * @param imagePath 图片路径
	 * @param callback 图片下载完成后回调
	 * @param isPortrait 是否是头像，true是，false不是
	 * @param callback 图片下载完成后回调
	 * @return bitmap
	 */
	public Bitmap loadImageBitmapBenDi(final Context context, final ImageView image, final int position, final String imagePath,
                                       final String localPath, final ImageCallback callback, final boolean isPortrait, final String str);

	/**
	 * 下载图片
	 *
	 * @createTime 2015-4-23,下午6:11:52
	 * @updateTime 2015-4-23,下午6:11:52
	 * @createAuthor 綦巍
	 * @updateAuthor 綦巍
	 * @param context 上下文
	 * @param position 位置
	 * @param imagePath 图片路径
	 * @param isCutRound 是否裁剪圆角
	 * @param callback 图片下载完成后回调
	 * @return bitmap
	 */
	public Bitmap loadImageBitmap(final Context context, final int position, final String imagePath, final boolean isCutRound,
                                  final ImageCallback callback);

	/**
	 * 设置图片加载开始位置
	 * 
	 * @author 罗文忠
	 * @version 1.0
	 * @date 2013-4-16
	 * @param startLoadLimit 开始加载图片范围
	 * @param stopLoadLimit 结束图片加载范围
	 */
	public void setLoadLimit(int startLoadLimit, int stopLoadLimit);

	/**
	 * 改变状态
	 * 
	 * @version 1.0
	 * @createTime 2015-4-23,下午6:25:28
	 * @updateTime 2015-4-23,下午6:25:28
	 * @createAuthor 綦巍
	 * @updateAuthor 綦巍
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 */
	public void restore();

	/**
	 * 锁定下载图片线程
	 * 
	 * @author 罗文忠
	 * @version 1.0
	 * @date 2013-4-16
	 */
	public void lock();

	/**
	 * 解锁下载图片的线程
	 * 
	 * @author 罗文忠
	 * @version 1.0
	 * @date 2013-4-16
	 */
	public void unlock();

	/**
	 * 加载图片回调接口
	 * 
	 * @Description
	 * @author CodeApe
	 * @version 1.0
	 * @date 2013-10-24
	 * @Copyright: Copyright (c) 2013 深圳光汇云油电商有限公司.
	 * 
	 */
	public interface ImageCallback {
		/**
		 * 图片加载完成之后回调该接口
		 * 
		 * @version 1.0
		 * @createTime 2013-10-24,下午2:02:10
		 * @updateTime 2013-10-24,下午2:02:10
		 * @createAuthor CodeApe
		 * @updateAuthor CodeApe
		 * @updateInfo (此处输入修改内容,若无修改可不写.)
		 * 
		 * @param position 图片在list的位置
		 * @param bitmap 图片位图对象 ，null加载失败，否则返回得到的位图对象
		 * @param imagePath 图片路径
		 */
		public void imageLoaded(int position, Bitmap bitmap, String imagePath);
	}

	/**
	 * 设置ImageView图片,首先取缓存,然后取本地,最后取网络,自动设置默认图片和正确图片.(使用MyImage将缓存小图片,安全机制未知)
	 * 
	 * @version 1.0
	 * @createTime 2015-4-26,下午7:24:18
	 * @updateTime 2015-4-26,下午7:24:18
	 * @createAuthor 綦巍
	 * @updateAuthor 綦巍
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param imageView 被设置图片的ImageView
	 * @param position ListView的position,用于滑动时不加载
	 * @param imagePath 网络图片地址
	 */
	public void loadImageBitmap(final ImageView imageView, final int position, final String imagePath);

	/**
	 * 设置ImageView图片,首先取缓存,然后取本地,最后取网络,自动设置默认图片和正确图片.(使用MyImage将缓存小图片,安全机制未知)
	 * 
	 * @version 1.0
	 * @createTime 2015-4-26,下午7:24:18
	 * @updateTime 2015-4-26,下午7:24:18
	 * @createAuthor 綦巍
	 * @updateAuthor 綦巍
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param imageView 被设置图片的ImageView
	 * @param imagePath 网络图片地址
	 */
	public void loadImageBitmap(final ImageView imageView, final String imagePath);

	/**
	 * 手动设置ImageView的宽高
	 * 
	 * @version 1.0
	 * @createTime 2015-5-4,上午11:38:51
	 * @updateTime 2015-5-4,上午11:38:51
	 * @createAuthor 綦巍
	 * @updateAuthor 綦巍
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 */
	public void setImageWH(int width, int height);

	/**
	 * 加载资源并缓存
	 * 
	 * @version 1.0
	 * @createTime 2015-5-6,上午10:47:20
	 * @updateTime 2015-5-6,上午10:47:20
	 * @createAuthor 綦巍
	 * @updateAuthor 綦巍
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param id
	 * @return
	 */
	public Bitmap getResourceBitmap(int id);

	/**
	 * 根据图片id加载图片,本地相册的id
	 * 
	 * @version 1.0
	 * @createTime 2015-5-6,上午10:47:32
	 * @updateTime 2015-5-6,上午10:47:32
	 * @createAuthor 綦巍
	 * @updateAuthor 綦巍
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param imageView
	 * @param photoId 本地相册的id
	 * @return
	 */
	public void loadImageFromPath(final ImageView imageView, int photoId, final int position);

}
