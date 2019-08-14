package com.exchange_v1.app.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.Html;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.brightoilonline.c2b_phone.R;
import com.brightoilonline.c2b_phone.config.TApplication;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import java.lang.ref.WeakReference;

/**
 * Created by huanghh on 2017/5/4.
 */

public class UMUtils {
    //分享的回调
    public static class MyUMShareListener implements UMShareListener{

        private WeakReference<Activity> activity;

        public MyUMShareListener(Activity activity) {
            this.activity = new WeakReference(activity);
        }

        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            LogUtil.i( "UM Share platform" + platform);
            ToastUtil.showToast(activity.get()," 分享成功啦");
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtil.showToast(activity.get(), " 分享失败啦");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtil.showToast(activity.get()," 分享取消了");
        }
    };

    public static void shareWeiXin(Activity activity
            ,String title
            ,String text
            ,String targetUrl
            ,int umImageRes
    ){
        UMImage umImage = getUmImage(activity, umImageRes);
        UMWeb web = getUmWeb(activity, title, text, targetUrl, umImage);

        shareByPlatform(activity, web,SHARE_MEDIA.WEIXIN);
    }

    public static void shareWeiXin(Activity activity
            ,String title
            ,String text
            ,String targetUrl
            ,String umImageUrl
    ){
        UMImage umImage = getUmImage(activity, umImageUrl);
        UMWeb web = getUmWeb(activity, title, text, targetUrl, umImage);

        shareByPlatform(activity, web,SHARE_MEDIA.WEIXIN);
    }

    public static void shareWeiXinCircle(Activity activity
            ,String title
            ,String text
            ,String targetUrl
            ,int umImageRes
    ){
        UMImage umImage = getUmImage(activity, umImageRes);
        UMWeb web = getUmWeb(activity, title, text, targetUrl, umImage);

        shareByPlatform(activity, web,SHARE_MEDIA.WEIXIN_CIRCLE);
    }

    public static void shareWeiXinCircle(Activity activity
            ,String title
            ,String text
            ,String targetUrl
            ,String umImageUrl
    ){
        UMImage umImage = getUmImage(activity, umImageUrl);
        UMWeb web = getUmWeb(activity, title, text, targetUrl, umImage);

        shareByPlatform(activity, web,SHARE_MEDIA.WEIXIN_CIRCLE);
    }

//    /**
//     * qq分享
//     *
//     */
//    public static void shareQQ(Activity activity
//            ,String title
//            ,String text
//            ,String targetUrl
//            ,String umImageUrl
//    ){
//        UMImage umImage = getUmImage(activity, umImageUrl);
//        UMWeb web = getUmWeb(activity, title, text, targetUrl, umImage);
//
//        shareByPlatform(activity, web,SHARE_MEDIA.QQ);
//    }

    @NonNull
    private static UMImage getUmImage(Activity activity, int umImageRes) {
        UMImage umImage =new UMImage(activity, umImageRes);
        return umImage;
    }

    private static UMImage getUmImage(Activity activity, String umImageUrl) {
        UMImage umImage ;
        //分享显示的图片
        if(StringUtil.isEmpty(umImageUrl)){
            umImage =new UMImage(activity, R.drawable.ic_share);
        }else{
            umImage =new UMImage(activity, umImageUrl);
        }
        return umImage;
    }

    @NonNull
    private static UMWeb getUmWeb(Activity activity, String title, String text, String targetUrl, UMImage umImage) {


        UMWeb web = new UMWeb(targetUrl);
        web.setTitle(title);//标题
        web.setThumb(umImage);  //缩略图
        web.setDescription(text);//描述
        return web;
    }



    private static void shareByPlatform(final Activity activity, final  UMWeb web, final SHARE_MEDIA platform) {

        final Runnable callBack = new Runnable() {
            @Override
            public void run(){
                HandlerUtil.runOnUI(new Runnable() {
                    @Override
                    public void run() {
                        new ShareAction(activity).setPlatform(platform)
                                .setCallback(new MyUMShareListener(activity))
//                .withText(text)
//                .withTitle(title)
//                .withTargetUrl(targetUrl)
                                .withMedia(web)
                                .share();
                    }
                });
            }
        };
        //友盟的sdk有问题，暂时不使用权限
//        requestPermissions(activity,callBack);
        callBack.run();
    }

    private static void requestPermissions(Activity activity, final Runnable callBack) {
        if(Build.VERSION.SDK_INT>=23){
            String[] mPermissionList = new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.GET_ACCOUNTS,
                    //以下的权限是没有提示框的。所以不用提示
//                    Manifest.permission.READ_LOGS,
//                    Manifest.permission.SET_DEBUG_APP,
//                    Manifest.permission.SYSTEM_ALERT_WINDOW,
//                    Manifest.permission.WRITE_APN_SETTINGS
            };
            shareHandlerCall = new Handler(Looper.getMainLooper()){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if (msg.what == UM_PERMISSIONS_CALLBACK_CODE) {
                        callBack.run();
                    }
                }
            };
            ActivityCompat.requestPermissions(activity,mPermissionList, UM_REQUEST_CODE);

        }else{
            callBack.run();
            shareHandlerCall = null;
        }
    }

    /**
     * 用于请求分享回调
     */
    private static Handler shareHandlerCall = null;

    private static final int UM_REQUEST_CODE = 123;
    private static final int UM_PERMISSIONS_CALLBACK_CODE = 456;

    public static void onRequestPermissionsResult(Activity activity, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == UM_REQUEST_CODE) {
            boolean isPassPermissions = true;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    isPassPermissions = false;
                };
            }
            if (isPassPermissions) {
                Message msg = Message.obtain();
                msg.what = UM_PERMISSIONS_CALLBACK_CODE;
                if (shareHandlerCall == null) {
                    return;
                }
                shareHandlerCall.sendMessage(msg);
            }else{
                shareHandlerCall = null;
                ToastUtil.showToast(activity, "分享功能受限无法使用");
            }

        }         
    }

    public static void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if (activity==null) {
            return;
        }
        UMShareAPI.get(activity).onActivityResult(requestCode, resultCode, data);
    }

    public static ClipData myClip;
    public static ClipboardManager myClipboard;
    /**
     * 标题栏的分享操作
     * SHARE_MEDIA.QQ
     *
     */
    public static void SpeciaH5Share(final Activity thisAc, final String title, final String text, final String targetUrl, final String umImageUrl){
        new ShareAction(thisAc).setDisplayList(SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE)
                .addButton("sharereward_copy","sharereward_copy","link_icon","link_icon")
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                        if (snsPlatform.mShowWord.equals("sharereward_copy")){//复制连接
                            myClipboard = (ClipboardManager)thisAc.getSystemService(Context.CLIPBOARD_SERVICE);
                            myClip = ClipData.newPlainText("text",targetUrl);
                            myClipboard.setPrimaryClip(myClip);
                            Toast.makeText(thisAc.getApplicationContext(), "复制分享链接成功",
                                    Toast.LENGTH_SHORT).show();
                        }else if (SHARE_MEDIA.WEIXIN_CIRCLE.equals(share_media)){
                            shareWeiXinCircle(thisAc
                                    ,""+ Html.fromHtml(title)
                                    ,""+ Html.fromHtml(text)
                                    ,targetUrl
                                    ,umImageUrl
                            );
                        }
//                        else if (SHARE_MEDIA.QQ.equals(share_media)){//增加一个QQ分享
//                            shareQQ(thisAc
//                                    ,""+Html.fromHtml(title)
//                                    ,""+Html.fromHtml(text)
//                                    ,targetUrl
//                                    ,umImageUrl
//                            );
//                        }
                        else {

                            shareWeiXin(thisAc
                                    ,""+ Html.fromHtml(title)
                                    ,""+ Html.fromHtml(text)
                                    ,targetUrl
                                    ,umImageUrl
                            );
                        }

                    }
                }).open();
    }

    /**
     * 分享http图片
     * 到微信好友或者朋友圈
     */
    public static void ShareHttpPicture(final Activity thisAc, final String umImageUrl) {

        //设置分享面板的样式
        ShareBoardConfig config = new ShareBoardConfig();
        config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_CENTER);
        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR);

        new ShareAction(thisAc).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                        if (!StringUtil.isEmpty(umImageUrl)) {
                            UMImage imageurl = new UMImage(thisAc, umImageUrl);//分享网络图片url
                            imageurl.setThumb(new UMImage(thisAc, umImageUrl));//发送分享前显示的图片
                            new ShareAction(thisAc).withMedia(imageurl)
                                    .setPlatform(share_media)//设置分享平台
                                    .setCallback(new MyUMShareListener(thisAc)).share();
                        } else {
                            Toast.makeText(thisAc, "url参数不完整", Toast.LENGTH_LONG).show();
                        }
                    }
                }).open(config);
    }

    /**
     * 是否需要收集Crash崩溃日志
     */
    public static void CatchUncaughtExceptions(Boolean canCatch) {
        MobclickAgent.setCatchUncaughtExceptions(canCatch);
        if (canCatch){
            StatService.setOn(TApplication.context,StatService.EXCEPTION_LOG);//开启百度错误日志统计
        }
        LogUtil.out("UMUtils",""+canCatch);
    }

}
