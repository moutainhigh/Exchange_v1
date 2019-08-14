package com.exchange_v1.app.utils;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.brightoilonline.marine.NativeAction;
import com.umeng.analytics.MobclickAgent;

import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FinishPrograme {
    private Context context;

    public FinishPrograme(Context mcontext) {
        context = mcontext;
    }

    public void exitPrograme() {
        try {
            ZipFile zipFile = new ZipFile(context.getPackageCodePath());
            ZipEntry zipEntry = zipFile.getEntry("classes.dex");
            String crc32 = String.valueOf(zipEntry.getCrc());
//            System.out.println("[socket]" + DES3.encode(crc32 + "54683"));
            try {
                if (NativeAction.isDexComplete(crc32) == 1) {
                    finishApp(context);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                finishApp(context);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
    }

    private void finishApp(final Context context) {
        Toast.makeText(context, "系统发现您安装的APP被篡改过，请重新下载。", Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //友盟统计，主动杀死应用前，保存统计数据
                MobclickAgent.onKillProcess(context);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
        }, 2000);
    }
}
