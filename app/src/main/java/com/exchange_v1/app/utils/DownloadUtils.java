package com.exchange_v1.app.utils;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import com.brightoilonline.c2b_phone.utils.http.impl.ok.C2bOkClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @创建者 李贞高
 * @创建时间 2017/12/13 10:15
 * @描述 下载工具类  DownloadManager只支持HTTP。而且支持HTTP是由于ICS，不支持FTP
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述
 */
public class DownloadUtils {
    //zip文件路径
    String ZIP_PATH = Environment.getExternalStorageDirectory().toString() + File.separator + "Download/";
    int bundleVer;
    String ZIP_NAME = "";
    private Context mContext;
    //下载器
    private DownloadManager downloadManager;
    //下载的ID
    private long downloadId;
    private File mFile;

    public DownloadUtils(Context context) {
        this.mContext = context;
    }

    public void downloadZipFile(String url, String name, String title, String desc, int jsBundleVer) {
        downloadZipFile(url, name, title, desc, true, jsBundleVer);
    }

    public void downloadZipFile(String url, String name, String title, String desc, boolean isShow, int jsBundleVer) {
        downloadZipFile(url, name, title, desc, "Download", isShow, jsBundleVer);
    }

    public void downloadZipFile(String url, String name, String title, String desc, String path, boolean isShow, int jsBundleVer) {
        bundleVer = jsBundleVer;
        //检查Zip文件，每次解压后应该删除
        mFile = new File(ZIP_PATH, name);
        if (mFile != null && mFile.exists()) {
            mFile.delete();
        }
        if (TextUtils.isEmpty(url)) {
            return;
        }
        ZIP_NAME = name;
        //创建下载任务
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        //移动网络是否允许漫游
        request.setAllowedOverRoaming(false);
        //下载完在通知栏显示
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION);
        request.setTitle(title);
        request.setDescription(desc);
        request.setVisibleInDownloadsUi(isShow);

        Environment.getExternalStoragePublicDirectory(path).mkdir();
        //设置下载路径
        request.setDestinationInExternalPublicDir(path, name);
        //获取DownloadManager
        downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        //下载请求加入下载队列，队列返回任务id
        downloadId = downloadManager.enqueue(request);
        //注册广播监听下载状态
        mContext.registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    //广播监听下载的各个状态
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkStatus();
        }
    };

    private void checkStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);
        Cursor cursor = downloadManager.query(query);
        if (cursor.moveToFirst()) {
            int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                //暂停
                case DownloadManager.STATUS_PAUSED:
                    break;
                //延迟
                case DownloadManager.STATUS_PENDING:
                    break;
                //下载中
                case DownloadManager.STATUS_RUNNING:
                    break;
                //完成
                case DownloadManager.STATUS_SUCCESSFUL:
                    //解压处理
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            FileUtil.decompression(ZIP_PATH + ZIP_NAME, mContext.getFilesDir().toString());
                            mFile.delete();
                            //修改本地版本号
                            SpUtil.getSpUtil().putSPValue("jsBundleVersion", bundleVer);
                            mContext.unregisterReceiver(receiver);
                        }
                    }).start();
                    break;
                //失败
                case DownloadManager.STATUS_FAILED:
                    mContext.unregisterReceiver(receiver);
                    break;
                default:
                    break;
            }

        }
        cursor.close();
    }

    /**
     * 下载zip文件https
     *
     * @param url
     * @param fileName
     */
    public void downloadJSBundleFile(String url, final String fileName, final int bundleVersion) {
        //zip文件
        final File file = new File(ZIP_PATH, fileName);
        Request request = new Request.Builder().url(url).build();
        C2bOkClient.getInstance().mOkHttpClient.newCall(request)
                .enqueue(new Callback() {
                             @Override
                             public void onFailure(Call call, IOException e) {
                                 if (file.exists()) {
                                     file.delete();
                                 }
                             }

                             @Override
                             public void onResponse(Call call, Response response) throws IOException {
                                 InputStream input = null;
                                 FileOutputStream output = null;
                                 byte[] buffer = new byte[1024];
                                 int len;
                                 try {
                                     input = response.body().byteStream();
                                     file.createNewFile();
                                     output = new FileOutputStream(file);
                                     while ((len = input.read(buffer)) != -1) {
                                         output.write(buffer, 0, len);
                                     }
                                     output.flush();

                                     new Thread(new Runnable() {
                                         @Override
                                         public void run() {
                                             //解压
                                             FileUtil.decompression(ZIP_PATH + fileName, mContext.getFilesDir().toString());
//                                             FileUtil.decompression(ZIP_PATH + fileName, ZIP_PATH);
                                             file.delete();
                                             //修改本地版本号
                                             SpUtil.getSpUtil().putSPValue("jsBundleVersion", bundleVersion);
                                             // 获取当前进程的id
//                                             int pid = android.os.Process.myPid();
                                             // 这个方法只能用于自杀操作
//                                             android.os.Process.killProcess(pid);
                                         }
                                     }).start();
                                 } catch (Exception e) {
                                     if (file.exists()) {
                                         file.delete();
                                     }
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
    }

}
