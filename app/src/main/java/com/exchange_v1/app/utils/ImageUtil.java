package com.exchange_v1.app.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.brightoilonline.c2b_phone.config.FileConfig;
import com.brightoilonline.c2b_phone.config.ServerConfig;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 图片操作工具类
 *
 * @author CodeApe
 * @version 1.0
 * @Description
 * @date 2013-11-5
 * @Copyright: Copyright (c) 2013 深圳光汇云油电商有限公司.
 */
public class ImageUtil {

    /**
     * 图片的最大尺寸，任意边长大于960，都将进行压缩
     */
    private static int maxSize = 960;
    /**
     * 缩略图的图片的最大尺寸，所有图片都会生成缩略图
     */
    private static final int thumMaxSize = 300;

    /**
     * 图片压缩比例
     */
    private int compressScale = 50;
    /**
     * 不是本地
     */
    private String suffix = "";

    /**
     * 处理多张图片
     *
     * @param srcPaths
     * @return
     * @version 1.0
     * @createTime 2014年1月17日, 下午7:54:16
     * @updateTime 2014年1月17日, 下午7:54:16
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public String compressImages(String srcPaths) {
        try {
            LogUtil.out("srcPaths===>" + srcPaths);
            if (TextUtils.isEmpty(srcPaths)) {
                return "";
            }

            String images[] = srcPaths.split(",");
            String compressImges = "";
            String imageName = "";

            for (int i = 0; i < images.length; i++) {
                if (images[i].contains(".jpeg")) {
                    imageName = images[i].substring(images[i].lastIndexOf("/") + 1).replace(".jpeg", ".jpg");
                } else if (images[i].contains(".png")) {
                    imageName = images[i].substring(images[i].lastIndexOf("/") + 1).replace(".png", ".jpg");
                } else {
                    imageName = images[i].substring(images[i].lastIndexOf("/") + 1);
                }
                if (i == 0) {
                    compressImges += compressImage(images[i], imageName);
                } else {
                    compressImges += "," + compressImage(images[i], imageName);
                }
            }

            return compressImges;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 压缩图片
     *
     * @param srcPath       原图路径
     * @param fileName      图片名称
     * @param compressScale 图片压缩比例
     * @return
     * @version 1.0
     * @createTime 2014年6月10日, 下午4:35:01
     * @updateTime 2014年6月10日, 下午4:35:01
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public String compressImage(String srcPath, String fileName, int compressScale) {
        this.compressScale = compressScale;
        return compressImage(srcPath, fileName);
    }

    /**
     * 压缩图片(本地)
     *
     * @param srcPath       原图路径
     * @param fileName      图片名称
     * @param compressScale 图片压缩比例
     * @return
     * @version 1.0
     * @createTime 2014年6月10日, 下午4:35:01
     * @updateTime 2014年6月10日, 下午4:35:01
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public String compressImage(String srcPath, String fileName, int compressScale, String suffix) {
        this.compressScale = compressScale;
        this.suffix = suffix;
        return compressImage(srcPath, fileName);
    }

    // 文件拷贝
    // 要复制的目录下的所有非子目录(文件夹)文件拷贝
    public String CopySdcardFile(String fromFile, String toFile) {
        try {
            File file = new File(toFile);
            if (!file.exists()) {
                InputStream fosfrom = new FileInputStream(fromFile);
                OutputStream fosto = new FileOutputStream(toFile);
                byte bt[] = new byte[102400];
                int c;
                while ((c = fosfrom.read(bt)) > 0) {
                    fosto.write(bt, 0, c);
                }
                fosfrom.close();
                fosto.close();
            }
            return "1";
        } catch (Exception ex) {
            return "";
        }
    }

    /**
     * 压缩图片并保存到相应的文件夹中
     *
     * @param fileName 文件名
     * @param srcPath  源图绝对路径
     * @version 1.0
     * @createTime 2013-11-5,下午3:22:38
     * @updateTime 2013-11-5,下午3:22:38
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public String compressImage(String srcPath, String fileName) {

        try {
            LogUtil.out("srcPath=" + srcPath + "    fileName=" + fileName);

            if (fileName.endsWith(".gif")) {
                String str = CopySdcardFile(srcPath, FileConfig.PATH_USER_IMAGE + fileName);
                if (str != null && str.equals("1")) {
                    return FileConfig.PATH_USER_IMAGE + fileName;
                }
            }
            if (!TextUtils.isEmpty(suffix)) {
                srcPath = srcPath.replace(suffix, ".jpg");
            }

            Bitmap bitmap = getBitmap(srcPath);

            // 如果图片加载失败，则返回空
            if (bitmap == null) {
                return "";
            }

            // 获取原图的显示方向
            int digree = getImageDigree(srcPath);

            // 旋转图片的显示方向
            Matrix matrix = new Matrix();
            matrix.postRotate(digree);

            // 原始位图的宽高参数
            int srcWidth = bitmap.getWidth();
            int srcHeight = bitmap.getHeight();

            // 压缩图片的尺寸属性对象
            Size newSize = getNewSize(srcWidth, srcHeight);
            // 缩略图的尺寸属性对象
            Size thumSize = getThumSize(srcWidth, srcHeight);

            // 压缩图的画布区域
            Rect newDst = new Rect(0, 0, newSize.width, newSize.height);
            // 缩略图的画布区域
            Rect thumDst = new Rect(0, 0, thumSize.width, thumSize.height);
            // 原图的裁剪区域
            Rect src = new Rect(0, 0, srcWidth, srcHeight);

            // ******************缩略图对象******************//
            Bitmap thumBitmap;
            if (digree == 90 || digree == 270) {// 垂直显示
                thumBitmap = Bitmap.createBitmap(thumSize.height, thumSize.width, Bitmap.Config.RGB_565);
            } else {// 横向显示
                thumBitmap = Bitmap.createBitmap(thumSize.width, thumSize.height, Bitmap.Config.RGB_565);
            }
            // 绘制缩略图
            Canvas canvas = new Canvas(thumBitmap);
            canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
            canvas.save();
            // 旋转画布
            rotate(canvas, digree, thumSize);
            canvas.drawBitmap(bitmap, src, thumDst, null);
            canvas.restore();
            // 保存生成的位图
            saveBitmapToFile(FileConfig.PATH_USER_THUMBNAIL, fileName, thumBitmap);
            // 回收位图空间，释放内存
            thumBitmap.recycle();
            System.gc();

            // *****************压缩图对象*******************//
            Bitmap newBitmap;
            if (digree == 90 || digree == 270) {// 垂直显示
                newBitmap = Bitmap.createBitmap(newSize.height, newSize.width, Bitmap.Config.RGB_565);
            } else {// 横向显示
                newBitmap = Bitmap.createBitmap(newSize.width, newSize.height, Bitmap.Config.RGB_565);
            }
            // 绘制压缩图
            canvas = new Canvas(newBitmap);
            canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
            canvas.save();
            // 旋转画布
            rotate(canvas, digree, newSize);
            canvas.drawBitmap(bitmap, src, newDst, null);
            canvas.restore();
            // 保存生成的位图
            saveBitmapToFile(FileConfig.PATH_USER_IMAGE, fileName, newBitmap);
            // 回收位图空间，释放内存
            newBitmap.recycle();
            bitmap.recycle();
            System.gc();

            if (TextUtils.isEmpty(suffix)) {
                return FileConfig.PATH_USER_IMAGE + fileName;
            } else {
                return (FileConfig.PATH_USER_IMAGE + fileName).replace(".jpg", suffix);
            }
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth  参考宽度
     * @param reqHeight 参考高度
     * @return
     * @version 1.0
     * @createTime 2014年1月17日, 下午7:49:52
     * @updateTime 2014年1月17日, 下午7:49:52
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 根据路径获得图片并压缩，返回bitmap用于显示
     *
     * @param filePath 图片路径
     * @return
     * @version 1.0
     * @createTime 2014年1月17日, 下午7:50:21
     * @updateTime 2014年1月17日, 下午7:50:21
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static Bitmap loadBitmap(String filePath) {
        try {

            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, options);
            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, maxSize, maxSize);
            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeFile(filePath, options);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 旋转画布
     *
     * @param canvas 画布
     * @param digree 旋转角度
     * @param size   画布尺寸
     * @Ps: 画布旋转之后，整个坐标系也会跟随旋转，所以旋转之后的平移要考虑坐标系的变动，不然平移必然是错位的
     * @version 1.0
     * @createTime 2013-12-9,下午3:56:05
     * @updateTime 2013-12-9,下午3:56:05
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    private void rotate(Canvas canvas, int digree, Size size) {

        switch (digree) {
            case 0: // 正横拍
                break;
            case 90:// 正竖拍
                canvas.rotate(90, canvas.getWidth() / 2, canvas.getHeight() / 2);
                canvas.translate(-Math.abs(size.height - size.width) / 2, Math.abs(size.height - size.width) / 2);
                break;
            case 180:// 反横拍
                canvas.rotate(180, canvas.getWidth() / 2, canvas.getHeight() / 2);
                break;
            case 270:// 反竖拍
                canvas.rotate(-90, canvas.getWidth() / 2, canvas.getHeight() / 2);
                canvas.translate(-Math.abs(size.height - size.width) / 2, Math.abs(size.height - size.width) / 2);
                break;

            default:
                break;
        }

    }

    /**
     * 获取压缩图片的尺寸大小属性
     *
     * @param srcWidth  原始图片的宽度
     * @param srcHeight 原始图片的高度
     * @return
     * @version 1.0
     * @createTime 2013-11-5,下午3:05:20
     * @updateTime 2013-11-5,下午3:05:20
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    private Size getNewSize(int srcWidth, int srcHeight) {
        Size newSize = new Size();
        // 判断图片形状
        if (srcWidth >= srcHeight) {
            if (srcWidth >= maxSize) {
                newSize.width = maxSize;
            } else {
                newSize.width = srcWidth;
            }
            newSize.height = newSize.width * srcHeight / srcWidth;
        } else {
            if (srcHeight >= maxSize) {
                newSize.height = maxSize;
            } else {
                newSize.height = srcWidth;
            }
            newSize.width = newSize.height * srcWidth / srcHeight;
        }
        return newSize;
    }

    /**
     * 获取缩略图的尺寸大小属性
     *
     * @param srcWidth  原图的宽度
     * @param srcHeight 原图的高度
     * @return
     * @version 1.0
     * @createTime 2013-11-5,下午3:09:23
     * @updateTime 2013-11-5,下午3:09:23
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    private Size getThumSize(int srcWidth, int srcHeight) {
        Size thumSize = new Size();
        // 判断图片形状
        if (srcWidth >= srcHeight) {
            if (srcWidth >= thumMaxSize) {
                thumSize.width = thumMaxSize;
            } else {
                thumSize.width = srcWidth;
            }
            thumSize.height = thumSize.width * srcHeight / srcWidth;
        } else {
            if (srcHeight >= thumMaxSize) {
                thumSize.height = thumMaxSize;
            } else {
                thumSize.height = srcWidth;
            }
            thumSize.width = thumSize.height * srcWidth / srcHeight;
        }
        return thumSize;
    }

    /**
     * 保存位图到本地文件
     *
     * @param saveParentPath // 文件保存目录
     * @param fileName       文件名称
     * @param bitmap         位图对象
     * @version 1.0
     * @createTime 2013-11-5,下午2:33:35
     * @updateTime 2013-11-5,下午2:33:35
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public void saveBitmapToFile(String saveParentPath, String fileName, Bitmap bitmap) {

        try {
            File saveimg = new File(saveParentPath + fileName);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(saveimg));
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressScale, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取位图
     *
     * @param path 图片路径
     * @return
     * @version 1.0
     * @createTime 2013-11-6,下午9:01:29
     * @updateTime 2013-11-6,下午9:01:29
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    private Bitmap getBitmap(String path) {

        if (TextUtils.isEmpty(path)) {// 文件路径为空
            return null;
        }

        return loadBitmap(path);

    }

    // /**
    // * 从给定路径加载图片
    // *
    // * @version 1.0
    // * @createTime 2013-11-6,下午9:00:23
    // * @updateTime 2013-11-6,下午9:00:23
    // * @createAuthor CodeApe
    // * @updateAuthor CodeApe
    // * @updateInfo (此处输入修改内容,若无修改可不写.)
    // *
    // * @param imgpath
    // * 图片路径
    // * @return
    // */
    // public Bitmap loadBitmap(String imgpath) {
    //
    // int be = 0;
    // BitmapFactory.Options options = new BitmapFactory.Options();
    // options.inJustDecodeBounds = true;
    // Bitmap bitmap = BitmapFactory.decodeFile(imgpath, options);
    // options.inJustDecodeBounds = false;
    // if (options.outHeight > options.outWidth) {
    // be = (int) (options.outHeight / (float) 200);
    // } else {
    // be = (int) (options.outWidth / (float) 200);
    // }
    // if (be <= 0) {
    // be = 1;
    // }
    // be = 2;
    // options.inSampleSize = be;
    // bitmap = BitmapFactory.decodeFile(imgpath, options);
    //
    // return bitmap;
    //
    // }

    /**
     * 获取图片的显示方向
     *
     * @param imagePath
     * @return
     * @version 1.0
     * @createTime 2013-11-6,下午9:23:10
     * @updateTime 2013-11-6,下午9:23:10
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    private int getImageDigree(String imagePath) {
        int digree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
            exif = null;
        }
        if (exif != null) {
            // 读取图片中相机方向信息
            int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            // 计算旋转角度
            switch (ori) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    digree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    digree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    digree = 270;
                    break;
                default:
                    digree = 0;
                    break;
            }
        }

        return digree;

    }

    /**
     * 尺寸类
     *
     * @author CodeApe
     * @version 1.0
     * @Description TODO
     * @date 2013-11-5
     * @Copyright: Copyright (c) 2013 深圳光汇云油电商有限公司.
     */
    private class Size {
        /**
         * 宽度
         */
        private int width;
        /**
         * 高度
         */
        private int height;

        @Override
        public String toString() {
            return "Size [width=" + width + ", height=" + height + "]";
        }
    }

    /**
     * 水平方向模糊度
     */
    private static float hRadius = 10;
    /**
     * 竖直方向模糊度
     */
    private static float vRadius = 10;
    /**
     * 模糊迭代度
     */
    private static int iterations = 6;

    /**
     * 图片高斯模糊处理
     *
     * @param bmp     位图对象
     * @param context 上下文对象
     * @return
     * @version 1.0
     * @createTime 2014年8月16日, 上午9:34:49
     * @updateTime 2014年8月16日, 上午9:34:49
     * @createAuthor 王治粮
     * @updateAuthor 王治粮
     * @updateInfo
     */
    public static Drawable BlurImages(Bitmap bmp, Context context) {

        int width = bmp.getWidth();
        int height = bmp.getHeight();
        int[] inPixels = new int[width * height];
        int[] outPixels = new int[width * height];
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bmp.getPixels(inPixels, 0, width, 0, 0, width, height);
        for (int i = 0; i < iterations; i++) {
            blur(inPixels, outPixels, width, height, hRadius);
            blur(outPixels, inPixels, height, width, vRadius);
        }
        blurFractional(inPixels, outPixels, width, height, hRadius);
        blurFractional(outPixels, inPixels, height, width, vRadius);
        bitmap.setPixels(inPixels, 0, width, 0, 0, width, height);
        Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);
        return drawable;
    }

    /**
     * 图片高斯模糊算法
     *
     * @param in
     * @param out
     * @param width
     * @param height
     * @param radius
     * @version 1.0
     * @createTime 2014年8月16日, 上午9:27:47
     * @updateTime 2014年8月16日, 上午9:27:47
     * @createAuthor 王治粮
     * @updateAuthor 王治粮
     * @updateInfo
     */
    public static void blur(int[] in, int[] out, int width, int height, float radius) {
        int widthMinus1 = width - 1;
        int r = (int) radius;
        int tableSize = 2 * r + 1;
        int divide[] = new int[256 * tableSize];

        for (int i = 0; i < 256 * tableSize; i++)
            divide[i] = i / tableSize;

        int inIndex = 0;

        for (int y = 0; y < height; y++) {
            int outIndex = y;
            int ta = 0, tr = 0, tg = 0, tb = 0;

            for (int i = -r; i <= r; i++) {
                int rgb = in[inIndex + clamp(i, 0, width - 1)];
                ta += (rgb >> 24) & 0xff;
                tr += (rgb >> 16) & 0xff;
                tg += (rgb >> 8) & 0xff;
                tb += rgb & 0xff;
            }

            for (int x = 0; x < width; x++) {
                out[outIndex] = (divide[ta] << 24) | (divide[tr] << 16) | (divide[tg] << 8) | divide[tb];

                int i1 = x + r + 1;
                if (i1 > widthMinus1)
                    i1 = widthMinus1;
                int i2 = x - r;
                if (i2 < 0)
                    i2 = 0;
                int rgb1 = in[inIndex + i1];
                int rgb2 = in[inIndex + i2];

                ta += ((rgb1 >> 24) & 0xff) - ((rgb2 >> 24) & 0xff);
                tr += ((rgb1 & 0xff0000) - (rgb2 & 0xff0000)) >> 16;
                tg += ((rgb1 & 0xff00) - (rgb2 & 0xff00)) >> 8;
                tb += (rgb1 & 0xff) - (rgb2 & 0xff);
                outIndex += height;
            }
            inIndex += width;
        }
    }

    /**
     * 图片高斯模糊算法
     *
     * @param in
     * @param out
     * @param width
     * @param height
     * @param radius
     * @version 1.0
     * @createTime 2014年8月16日, 上午9:37:35
     * @updateTime 2014年8月16日, 上午9:37:35
     * @createAuthor 王治粮
     * @updateAuthor 王治粮
     * @updateInfo
     */
    public static void blurFractional(int[] in, int[] out, int width, int height, float radius) {
        radius -= (int) radius;
        float f = 1.0f / (1 + 2 * radius);
        int inIndex = 0;

        for (int y = 0; y < height; y++) {
            int outIndex = y;

            out[outIndex] = in[0];
            outIndex += height;
            for (int x = 1; x < width - 1; x++) {
                int i = inIndex + x;
                int rgb1 = in[i - 1];
                int rgb2 = in[i];
                int rgb3 = in[i + 1];

                int a1 = (rgb1 >> 24) & 0xff;
                int r1 = (rgb1 >> 16) & 0xff;
                int g1 = (rgb1 >> 8) & 0xff;
                int b1 = rgb1 & 0xff;
                int a2 = (rgb2 >> 24) & 0xff;
                int r2 = (rgb2 >> 16) & 0xff;
                int g2 = (rgb2 >> 8) & 0xff;
                int b2 = rgb2 & 0xff;
                int a3 = (rgb3 >> 24) & 0xff;
                int r3 = (rgb3 >> 16) & 0xff;
                int g3 = (rgb3 >> 8) & 0xff;
                int b3 = rgb3 & 0xff;
                a1 = a2 + (int) ((a1 + a3) * radius);
                r1 = r2 + (int) ((r1 + r3) * radius);
                g1 = g2 + (int) ((g1 + g3) * radius);
                b1 = b2 + (int) ((b1 + b3) * radius);
                a1 *= f;
                r1 *= f;
                g1 *= f;
                b1 *= f;
                out[outIndex] = (a1 << 24) | (r1 << 16) | (g1 << 8) | b1;
                outIndex += height;
            }
            out[outIndex] = in[width - 1];
            inIndex += width;
        }
    }

    public static int clamp(int x, int a, int b) {
        return (x < a) ? a : (x > b) ? b : x;
    }

    /**
     * 获取图片下载路径
     *
     * @param url
     * @return
     * @version 1.0
     * @createTime 2015年7月17日, 上午9:51:53
     * @updateTime 2015年7月17日, 上午9:51:53
     * @createAuthor gushiyong
     * @updateAuthor gushiyong
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static String getDownURL(String url) {
        if (url.startsWith(ServerConfig.SERVER_IMAGE_URL)) {
            return url;
        } else {
            return ServerConfig.SERVER_IMAGE_URL + url;
        }
    }

    /**
     * 刷新相册
     */
    public static void refreshTheAlbum(File file, Context context) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("_data", file.toString());
        contentValues.put("description", "save image ---");
        contentValues.put("mime_type", "image/jpeg");
        ContentResolver contentResolver = context.getContentResolver();
        Uri externalContentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        contentResolver.insert(externalContentUri, contentValues);
    }

    /**
     * 获取相册图片地址
     */
    public static String getPhoto(Uri uri, Activity activity) {
        if (uri == null) {
            return null;
        }
        Cursor cursor = activity.getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) {
            return null;
        }
        cursor.moveToFirst();
        String picturePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
        cursor.close();
        return picturePath;
    }

    /**
     * @param filePath 文件路径
     * @param limit    压缩宽度
     * @param max      规则图片比例
     * @return
     */
    public static BitmapFactory.Options commpressImage(String filePath, float limit, float max, float min) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//获取图片大小
        BitmapFactory.decodeFile(filePath, options);
        int height = options.outHeight;
        int width = options.outWidth;
        if ((float) height / width < min || (float) height / width > max) {
            return null;
        }
        int inSampleSize = 1;//原图
        if (width > limit) {
            inSampleSize = (int) (width / limit);
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;
        return options;
    }

    /**
     * 根据bitmap保存文件
     */
    public static String getImagePath(@NonNull Bitmap bitmap) {
        return savePhoto(bitmap, Environment.getExternalStorageDirectory().getAbsolutePath(), String.valueOf(System.currentTimeMillis()) + ".jpg");
    }

    /**
     * Save image to the SD card
     *
     * @param photoBitmap
     * @param photoName
     * @param path
     */
    public static String savePhoto(Bitmap photoBitmap, String path,
                                   String photoName) {
        String localPath = null;
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File photoFile = new File(path, photoName);
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(photoFile);
                if (photoBitmap != null) {
                    if (photoBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                            fileOutputStream)) {
                        localPath = photoFile.getPath();
                        fileOutputStream.flush();
                    }
                }
            } catch (FileNotFoundException e) {
                photoFile.delete();
                localPath = null;
                e.printStackTrace();
            } catch (IOException e) {
                photoFile.delete();
                localPath = null;
                e.printStackTrace();
            } finally {
                try {
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                        fileOutputStream = null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return localPath;
    }

}
