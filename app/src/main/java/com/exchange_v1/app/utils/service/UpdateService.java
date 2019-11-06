package com.exchange_v1.app.utils.service;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.RemoteViews;

import com.exchange_v1.app.R;
import com.exchange_v1.app.config.FileConfig;
import com.exchange_v1.app.utils.ApkUtil;
import com.exchange_v1.app.utils.SpUtil;
import com.exchange_v1.app.utils.http.impl.ok.C2bOkClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 更新
 *
 */
public class UpdateService extends Service {
	/**
	 * 通知栏服务
	 */
	private NotificationManager notificationManager;
	/**
	 * 通知栏对象
	 */
	private Notification notification;
	/**
	 * 当前下载百分比
	 */
	private double currentPercent = 0;

	/** apk包的大小 */
	// private Float apkSize;
	/**
	 * apk包的路径
	 */
	private String apkPath;
	/**
	 * 本地apk
	 */
	private String apkLocal;
	/**
	 * 缓存apk路径
	 */
	private String tempApkPath;
	private static final int NOTIFYCATION_DOWNLOAD_ID = 20150531;
	private static final int LOAD_APK_UPDATE = 0;
	private static final int LOAD_APK_FINASH = 1;
	private static final int LOAD_APK_ERROR = 2;
	/**
	 * 异步消息处理对象
	 *
	 * @version 1.0
	 * @date 2013-4-26
	 */
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch (msg.what) {
				case LOAD_APK_UPDATE:
					updateNotification();

					break;
				case LOAD_APK_FINASH:
					finish();
					break;
				case LOAD_APK_ERROR:
					loadError();
					break;
			}
		}
	};
	/**
	 * 更新下载进度的线程
	 */
	private Runnable update = new Runnable() {
		@Override
		public void run() {
			if (currentPercent < 100) {
				handler.sendEmptyMessage(LOAD_APK_UPDATE);
				handler.postDelayed(update, 1000);
			}
		}
	};

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		try {
			Bundle bundle = intent.getExtras();
			String path = bundle.getString("path");
			String size = bundle.getString("size");
			if (path == null) {
				stopService(new Intent(this, UpdateService.class));
			} else {
				showNotification();
				// apkSize = Float.parseFloat(size);
				apkPath = path;
				apkLocal = FileConfig.PATH_DOWNLOAD
						+ apkPath.substring(apkPath.lastIndexOf("/") + 1);
				tempApkPath = apkLocal.replace(".apk", ".temp");
				startDownLoad();
			}
		} catch (Exception e) {

		}

	}

	/**
	 * 开始下载文件
	 *
	 * @author 罗文忠
	 * @version 1.0
	 */
	private void startDownLoad() {
		SpUtil.setIsApkDownloadOK(false);
		handler.postDelayed(update, 1000);
		//        new Thread(new Runnable() {
		//            @Override
		//            public void run() {
		//                downloadFile(apkPath, apkLocal);
		//            }
		//        }).start();
		downloadFile(apkPath, apkLocal);
	}

	/**
	 * 更新通知栏进度条
	 *
	 * @author 罗文忠
	 * @version 1.0
	 * @date 2013-4-26
	 */
	private void updateNotification() {
		// currentPercent = new File(tempApkPath).length() / apkSize * 100;
		notification.contentView.setTextViewText(R.id.notice_title, "当前下载" + (int) currentPercent
				+ "%");
		notification.contentView.setProgressBar(R.id.notice_progressbar, 100, (int) currentPercent,
				false);
		notification.contentView.setViewVisibility(R.id.notice_progressbar, View.VISIBLE);
		notification.contentView.setViewVisibility(R.id.notice_content, View.INVISIBLE);
		notificationManager.notify(NOTIFYCATION_DOWNLOAD_ID, notification);
	}

	//    public void downloadFile(String path, final String savePath) {
	//
	//        MyHttpClient.get(path, new IBinaryResponseHandler() {
	//
	//
	//            @Override
	//            public void onSuccess(int i, Header[] headers, byte[] bytes) {
	//                File tempFile = new File(tempApkPath);
	//                if (!new File(FileConfig.PATH_DOWNLOAD).exists()) {
	//                    new File(FileConfig.PATH_DOWNLOAD).mkdirs();
	//                }
	//                try {
	//                    for (File file : new File(FileConfig.PATH_DOWNLOAD).listFiles()) {
	//                        file.delete();
	//                    }
	//
	//                } catch (Exception e) {
	//
	//                    e.printStackTrace();
	//                }
	//
	//                FileOutputStream output = null;
	//                BufferedOutputStream bufferedOutput = null;
	//
	//                try {
	//                    tempFile.createNewFile(); // 创建文件
	//                    output = new FileOutputStream(tempFile);
	//                    bufferedOutput = new BufferedOutputStream(output);
	//                    bufferedOutput.write(bytes);
	//                    tempFile.renameTo(new File(savePath));
	//                    handler.sendEmptyMessage(LOAD_APK_FINASH);
	//                } catch (FileNotFoundException e) {
	//                    handler.sendEmptyMessage(LOAD_APK_ERROR);
	//                    e.printStackTrace();
	//                } catch (IOException e) {
	//                    handler.sendEmptyMessage(LOAD_APK_ERROR);
	//                    e.printStackTrace();
	//                }
	//
	//            }
	//
	//            @Override
	//            public void onFail(int i, Header[] headers, byte[] bytes, Throwable throwable) {
	//                handler.sendEmptyMessage(LOAD_APK_ERROR);
	//            }
	//
	//            @Override
	//            public void onProgress(long bytesWritten, long totalSize) {
	//                super.onProgress(bytesWritten, totalSize);
	//                currentPercent = (int) ((bytesWritten * 1.0 / totalSize) * 100);
	//            }
	//        });
	//    }


	/**
	 * 下载文件.下载文件存储至指定路径.
	 *
	 * @param path     下载路径.
	 * @param savePath 存储路径.
	 * @return 下载成功返回true, 若下载失败则返回false.
	 * @throws IOException 下载过程产生错误抛出IOException.
	 * @author
	 * @version 1.0, 2017-7-11
	 */
	public void downloadFile(String path, final String savePath) {
		final File tempFile = new File(tempApkPath);
		if (!new File(FileConfig.PATH_DOWNLOAD).exists()) {
			new File(FileConfig.PATH_DOWNLOAD).mkdirs();
		}
		// 清理之前下载的旧版APK文件
		try {
			for (File file : new File(FileConfig.PATH_DOWNLOAD).listFiles()) {
				file.delete();
			}
		} catch (Exception e) {
		}
		Request request = new Request.Builder().url(path).build();
		C2bOkClient.getInstance()
				.mOkHttpClient
				.newCall(request)
				.enqueue(new Callback() {
							 @Override
							 public void onFailure(Call call, IOException e) {
								 handler.sendEmptyMessage(LOAD_APK_ERROR);
							 }

							 @Override
							 public void onResponse(Call call, Response response) throws IOException {
								 InputStream input = null;
								 FileOutputStream output = null;
								 byte[] buffer = new byte[1024];
								 int len;
								 try {
									 long total = response.body().contentLength();
									 input = response.body().byteStream();
									 tempFile.createNewFile();
									 output = new FileOutputStream(tempFile);
									 long threadSize = 0;
									 while ((len = input.read(buffer)) != -1) {
										 output.write(buffer, 0, len);
										 threadSize += len;
										 currentPercent = (int) (threadSize * 1.0f / total * 100);
									 }
									 output.flush();
									 // 下载完成
									 currentPercent = 100;
									 tempFile.renameTo(new File(savePath));
									 handler.sendEmptyMessage(LOAD_APK_FINASH);
								 } catch (Exception e) {
									 handler.sendEmptyMessage(LOAD_APK_ERROR);
								 } finally {
									 try {
										 if (input != null)
											 input.close();
									 } catch (IOException e) {
									 }
									 try {
										 if (output != null)
											 output.close();
									 } catch (IOException e) {
									 }
								 }
							 }
						 }

				);
		//
		//        InputStream input = null;
		//        OutputStream output = null;
		//        try {
		//            URL url = new URL(path);
		//            HttpURLConnection conn = null;// (HttpURLConnection)
		//            // url.openConnection();
		//            try {
		//                conn = NetUtil.getUrlConnectionByUrl(url);
		//            } catch (Exception e) {
		//                e.printStackTrace();
		//            }
		//            if (TApplication.iSOUTNET == 0) {//添加HTTP 请求头密文
		//
		//                conn.setRequestProperty("Authorization", "Basic YndvaWw6YndvaWwxMjM=");// 主要是为了添加服务器验证
		//            }
		//            conn.setRequestMethod("GET");
		//            conn.setDoInput(true);
		//            conn.connect();
		//            int code = conn.getResponseCode();
		//            if (code == 200) {
		//                /** 总大小 */
		//                int size = conn.getContentLength();
		//                int threadSize = 0;
		//                input = conn.getInputStream();
		//                tempFile.createNewFile(); // 创建文件
		//                output = new FileOutputStream(tempFile);
		//                byte buffer[] = new byte[1024];
		//                int read = 0;
		//                while ((read = input.read(buffer)) != -1) { // 读取信息循环写入文件
		//                    output.write(buffer, 0, read);
		//                    threadSize = threadSize + read;
		//                    currentPercent = threadSize * 100 / size;
		//                    Log.d("AAAAA", currentPercent + "=====");
		//                }
		//                output.flush();
		//                currentPercent = 100;
		//                tempFile.renameTo(new File(savePath));
		//                handler.sendEmptyMessage(LOAD_APK_FINASH);
		//            } else {
		//                handler.sendEmptyMessage(LOAD_APK_ERROR);
		//            }
		//        } catch (MalformedURLException e) {
		//            handler.sendEmptyMessage(LOAD_APK_ERROR);
		//        } catch (IOException e) {
		//            handler.sendEmptyMessage(LOAD_APK_ERROR);
		//        } finally {
		//            try {
		//                output.close();
		//            } catch (Exception e) {
		//                e.printStackTrace();
		//            }
		//        }
	}

	/**
	 * 显示通知栏
	 *
	 * @author 罗文忠
	 * @version 1.0
	 * @date 2013-4-27
	 */
	private void showNotification() {
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, new Intent(), 0);
		notificationManager = (NotificationManager) getSystemService(Activity.NOTIFICATION_SERVICE);
		notification = new Notification();
		//显示图标，不知道过大会不会出问题？
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notification.icon = R.mipmap.ic_launcher_pf;
        }else {
            notification.icon = R.mipmap.ic_launcher_pf;
        }
		notification.tickerText = "等待下载";
		notification.when = System.currentTimeMillis();
		// 通知栏显示所用到的布局文件
		notification.contentView = new RemoteViews(getPackageName(), R.layout.view_notice_progress);
		notification.contentView.setImageViewResource(R.id.notice_icon, R.mipmap.ic_launcher_pf);
		// notification.contentView.setTextViewText(R.id.notice_title, "当前进度" + currentPercent + "%");
		notification.contentView.setTextViewText(R.id.notice_title, "正在下载中...");
		notification.contentView.setProgressBar(R.id.notice_progressbar, 100, (int) currentPercent,
				true);
		notification.contentIntent = pIntent;
		notification.flags = Notification.FLAG_NO_CLEAR;
		notificationManager.notify(NOTIFYCATION_DOWNLOAD_ID, notification);
	}

	/**
	 * 完成下载
	 *
	 * @author 罗文忠
	 * @version 1.0
	 * @date 2013-4-27
	 */
	private void finish() {
		currentPercent = 100;
		handler.removeCallbacks(update);
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		notification.contentView.setViewVisibility(R.id.notice_content, View.VISIBLE);
		notification.contentView.setViewVisibility(R.id.notice_progressbar, View.GONE);
		notification.contentView.setTextViewText(R.id.notice_title, "c2b_phone新版本下载完成");
		notification.contentView.setTextViewText(R.id.notice_content, "点击安装新版本");
		notificationManager.notify(NOTIFYCATION_DOWNLOAD_ID, notification);
		setInstallApk(apkLocal);
		stopService(new Intent(this, UpdateService.class));
	}

	/**
	 * 下载失败
	 *
	 * @author 罗文忠
	 * @version 1.0
	 * @date 2013-5-12
	 */
	private void loadError() {
		handler.removeCallbacks(update);
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		notification.contentView.setViewVisibility(R.id.notice_content, View.VISIBLE);
		notification.contentView.setViewVisibility(R.id.notice_progressbar, View.GONE);
		notification.contentView.setTextViewText(R.id.notice_title, "c2b_phone新版本下载失败");
		notification.contentView.setTextViewText(R.id.notice_content, "点击重新下载");
		Intent intent = new Intent(this, UpdateService.class);
		intent.putExtra("path", apkPath);
		// intent.putExtra("size", apkSize + "");
		PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);
		notification.contentIntent = pendingIntent;
		notificationManager.notify(NOTIFYCATION_DOWNLOAD_ID, notification);
		stopService(new Intent(this, UpdateService.class));
	}

	/**
	 * 设置点击后安装apk
	 *
	 * @param path
	 * @author 罗文忠
	 * @version 1.0
	 * @date 2013-4-27
	 */
	private void setInstallApk(String path) {
		SpUtil.setIsApkDownloadOK(true);

		Intent intent = ApkUtil.installApk(new File(path));
		if (intent == null) {
			return;
		}
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
		notification.contentIntent = pendingIntent;
		notificationManager.notify(NOTIFYCATION_DOWNLOAD_ID, notification);

	}
}
