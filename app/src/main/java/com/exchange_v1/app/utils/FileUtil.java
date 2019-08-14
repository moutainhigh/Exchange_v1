package com.exchange_v1.app.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.text.TextUtils;

import com.brightoilonline.c2b_phone.config.FileConfig;
import com.brightoilonline.c2b_phone.config.TApplication;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 文件操作工具类
 * 
 * @Description TODO
 * @author CodeApe
 * @version 1.0
 * @date 2013-11-3
 * @Copyright: Copyright (c) 2013 深圳光汇云油电商有限公司.
 * 
 */
public class FileUtil {
	private static File file;
	private static final int EOF = -1;
	private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
	/**
	 * 构建文件夹路径
	 * 
	 * @version 1.0
	 * @createTime 2013-11-3,下午12:08:58
	 * @updateTime 2013-11-3,下午12:08:58
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param path
	 */
	public static void mkdirs(String path) {
		new File(path).mkdirs();
		try {
			new File(path + "/.nomedia").createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 计算文件夹大小（非递归遍历文件夹）
	 * 
	 * @version 1.0
	 * @createTime 2013-7-27,下午3:42:55
	 * @updateTime 2013-7-27,下午3:42:55
	 * @createAuthor 罗文忠
	 * @updateAuthor 罗文忠
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param path 文件夹路径
	 * @return 文件夹大小
	 */
	@SuppressWarnings("rawtypes")
	public static float getDirectorySize(String path) {
		LinkedList list = new LinkedList(); // 保存待遍历文件夹的列表
		File file = new File(path);
		File tmp = null;
		float size = 0;

		size += getDirRootSize(file, list); // 调用遍历文件夹根目录文件的方法
		while (list != null && !list.isEmpty()) {
			tmp = (File) list.removeFirst();
			if (tmp.isDirectory()) {
				size += getDirRootSize(tmp, list);
			} else {
				size += tmp.length();

			}
		}

		return size;
	}

	/**
	 * 遍历指定文件夹根目录下的文件
	 * 
	 * @version 1.0
	 * @createTime 2013-7-27,下午3:27:12
	 * @updateTime 2013-7-27,下午3:27:12
	 * @createAuthor 罗文忠
	 * @updateAuthor 罗文忠
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param file 需要遍历的文件夹
	 * @param list 存放文件链接的列表
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static float getDirRootSize(File file, LinkedList list) {
		// 每个文件夹遍历都会调用该方法
		float size = 0;
		File[] files = file.listFiles();
		if (files == null || files.length == 0) {
			return size;
		}

		for (File f : files) {
			if (f.isDirectory()) {
				list.add(f);
			} else {
				// 这里列出当前文件夹根目录下的所有文件
				size += f.length();
			}
		}
		return size;
	}
	public static File from(Context context, Uri uri) throws IOException {
		InputStream inputStream = context.getContentResolver().openInputStream(uri);
		String fileName = getFileName(context, uri);
		String[] splitName = splitFileName(fileName);
		File tempFile = File.createTempFile(splitName[0], splitName[1]);
		tempFile = rename(tempFile, fileName);
		tempFile.deleteOnExit();
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(tempFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (inputStream != null) {
			copy(inputStream, out);
			inputStream.close();
		}

		if (out != null) {
			out.close();
		}
		return tempFile;
	}
	private static File rename(File file, String newName) {
		File newFile = new File(file.getParent(), newName);
		return newFile;
	}
	private static long copy(InputStream input, OutputStream output) throws IOException {
		long count = 0;
		int n;
		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
		while (EOF != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}
	private static String[] splitFileName(String fileName) {
		String name = fileName;
		String extension = "";
		int i = fileName.lastIndexOf(".");
		if (i != -1) {
			name = fileName.substring(0, i);
			extension = fileName.substring(i);
		}

		return new String[]{name, extension};
	}

	private static String getFileName(Context context, Uri uri) {
		String result = null;
		if (uri.getScheme().equals("content")) {
			Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
			try {
				if (cursor != null && cursor.moveToFirst()) {
					result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (cursor != null) {
					cursor.close();
				}
			}
		}
		if (result == null) {
			result = uri.getPath();
			int cut = result.lastIndexOf(File.separator);
			if (cut != -1) {
				result = result.substring(cut + 1);
			}
		}
		return result;
	}

	/**
	 * 清理文件夹(递归清理)
	 * 
	 * @version 1.0
	 * @createTime 2013-7-31,下午5:10:09
	 * @updateTime 2013-7-31,下午5:10:09
	 * @createAuthor 罗文忠
	 * @updateAuthor 罗文忠
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param file
	 */
	public static void clearDirectory(File file) {
		File[] flist = file.listFiles();
		if (flist == null || flist.length == 0) {
			file.delete();
		} else {
			for (File f : flist) {
				if (f.isDirectory()) {
					clearDirectory(f);
				} else {
					f.delete();
				}
			}
			file.delete();
		}
	}

	/**
	 * 普通权限复制文件
	 * 
	 * @version 1.0
	 * @createTime 2013-11-6,上午11:29:26
	 * @updateTime 2013-11-6,上午11:29:26
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param fromfile 源文件
	 * @param tofile 目标文件
	 */
	public static boolean commoncopyfile(File fromfile, File tofile) {

		FileInputStream inputStream = null;
		FileOutputStream outputStream = null;
		byte[] buffer = new byte[4 * 1024];
		int temp;
		try {
			tofile.createNewFile();
			inputStream = new FileInputStream(fromfile);
			outputStream = new FileOutputStream(tofile);
			while ((temp = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, temp);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			tofile.delete();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			tofile.delete();
			return false;
		} finally {
			try {
				buffer.clone();
				outputStream.close();
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return true;
	}

	/**
	 * 构建本地文件目录
	 * 
	 * @version 1.0
	 * @createTime 2013-11-3,下午3:44:53
	 * @updateTime 2013-11-3,下午3:44:53
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	public static void createAllFile() {

		mkdirs(FileConfig.PATH_BASE);
		mkdirs(FileConfig.PATH_CAMERA);
		mkdirs(FileConfig.PATH_DOWNLOAD);
		mkdirs(FileConfig.PATH_IMAGES);
		mkdirs(FileConfig.PATH_LOG);
		mkdirs(FileConfig.PATH_HTML);

		new File(FileConfig.PATH_PHOTOS).mkdirs();

		if (TApplication.userInfoBean != null) {
			LogUtil.out("++++++++++++++++++++++++++>" + TApplication.userInfoBean.getUserNo());
		}
		// 当用户账户不为空的时候，构建用户私有文件夹
		if (TApplication.userInfoBean != null && !TextUtils.isEmpty(TApplication.userInfoBean.getUserNo())) {
			// 以登录用户的帐号生成Md5串作为用户私有文件夹
			FileConfig.PATH_USER_FILE = FileConfig.PATH_BASE + EncryptUtil.md5Encrypt(TApplication.userInfoBean.getUserNo()) + "/";
			FileConfig.PATH_USER_AUDIO = FileConfig.PATH_USER_FILE + "audio/";
			FileConfig.PATH_USER_IMAGE = FileConfig.PATH_USER_FILE + "image/";
			FileConfig.PATH_USER_THUMBNAIL = FileConfig.PATH_USER_FILE + "thumbnail/";
			FileConfig.PATH_USER_VIDEO = FileConfig.PATH_USER_FILE + "video/";
			FileConfig.PATH_USER_FAVORITES = FileConfig.PATH_USER_FILE + "favorites/";

			LogUtil.out("path is ready!!!    =》 " + FileConfig.PATH_USER_IMAGE);
			mkdirs(FileConfig.PATH_USER_FILE);
			mkdirs(FileConfig.PATH_USER_IMAGE);
			mkdirs(FileConfig.PATH_USER_THUMBNAIL);
			mkdirs(FileConfig.PATH_USER_VIDEO);
			mkdirs(FileConfig.PATH_USER_AUDIO);
			mkdirs(FileConfig.PATH_USER_FAVORITES);
		}
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @version 1.0
	 * @createTime 2014-03-06,早上10:44:53
	 * @updateTime 2014-03-06,早上10:44:53
	 * @createAuthor wujianxing
	 * @updateAuthor wujianxing
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	public static boolean fileIsExists(String filePath) {
		try {
			File f = new File(filePath);
			return f.exists();
		} catch (Exception e) {
		}
		return false;
	}


	/**
	 *  文件解压
	 * @param zipFile   zip文件
	 * @param folderPath    目标路径
	 */
	public static void decompression(String zipFile, String folderPath) {
		ZipFile zfile;
		try {
			// 转码为GBK格式，支持中文
			zfile = new ZipFile(zipFile);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		Enumeration zList = zfile.entries();
		ZipEntry zipEntry;
		byte[] buf = new byte[1024];
		while (zList.hasMoreElements()) {
			zipEntry = (ZipEntry) zList.nextElement();
			// 列举的压缩文件里面的各个文件，判断是否为目录
			if (zipEntry.isDirectory()) {
				String dirstr = folderPath + zipEntry.getName();
				dirstr.trim();
				file = new File(dirstr);
				file.mkdir();
				continue;
			}
			OutputStream os;
			FileOutputStream fos;
			// ze.getName()会返回 script/start.script这样的，是为了返回实体的File
			File realFile = getRealFileName(folderPath, zipEntry.getName());
			try {
				fos = new FileOutputStream(realFile);
			} catch (FileNotFoundException e) {
				return;
			}
			os = new BufferedOutputStream(fos);
			InputStream is;
			try {
				is = new BufferedInputStream(zfile.getInputStream(zipEntry));
			} catch (IOException e) {
				return;
			}
			int readLen = 0;
			// 进行一些内容复制操作
			try {
				while ((readLen = is.read(buf, 0, 1024)) != -1) {
					os.write(buf, 0, readLen);
				}
			} catch (IOException e) {
				return;
			}
			try {
				is.close();
				os.close();
			} catch (IOException e) {
				return;
			}
		}
		try {
			zfile.close();
		} catch (IOException e) {
			return;
		}
	}

	/**
	 * 给定根目录，返回一个相对路径所对应的实际文件名.
	 *
	 * @param baseDir     指定根目录
	 * @param absFileName 相对路径名，来自于ZipEntry中的name
	 * @return java.io.File 实际的文件
	 */
	public static File getRealFileName(String baseDir, String absFileName) {
		absFileName = absFileName.replace("\\", "/");
		String[] dirs = absFileName.split("/");
		File file = new File(baseDir);
		String substr;
		if (dirs.length > 1) {
			for (int i = 0; i < dirs.length - 1; i++) {
				substr = dirs[i];
				file = new File(file, substr);
			}

			if (!file.exists())
				file.mkdirs();
			substr = dirs[dirs.length - 1];
			file = new File(file, substr);
			return file;
		} else {
			file = new File(file, absFileName);
		}
		return file;
	}

}
