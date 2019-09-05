# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-dontshrink
-dontpreverify
-dontoptimize
-dontusemixedcaseclassnames

-flattenpackagehierarchy
-allowaccessmodification
-printmapping map.txt

-optimizationpasses 7
-verbose
-keepattributes Exceptions,InnerClasses
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-ignorewarnings

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends java.lang.Throwable {*;}
-keep public class * extends java.lang.Exception {*;}
-keep class com.exchange_v1.app.interf.WebInterface{*;}
-keep class com.exchange_v1.app.interf.SpecialH5WebInterface{*;}
-keep class com.exchange_v1.app.interf.RedeemCodeWebInterface{*;}
-keep class com.exchange_v1.app.utils.ui.webview.** { *;}
-keep class com.exchange_v1.app.activity.home.webpage.** { *;}
-keep class com.exchange_v1.app.bean.** { *;}

#混淆摩窗代码.
-keep class com.tencent.mm.sdk.** {*;}
-keep class cn.magicwindow.** {*;}
-dontwarn cn.magicwindow.**


-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
#----------------------------------------------------------------------------
-keep class **.R$* {*;}
-keep class **.R{*;}
-keep public class com.exchange_v1.app.R$*{
	public static final int *;
}

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keep public class * extends java.net.URLDecoder
-keep public class com.android.vending.licensing.ILicensingService
-keep public class * extends android.preference.Preference
-keep public class * extends android.app.backup.BackupAgentHelper

-dontwarn
-dontskipnonpubliclibraryclassmembers

-keepattributes Signature

-keepattributes Exceptions,InnerClasses,Signature,SourceFile,LineNumberTable
-keepattributes *Annotation*
-keepattributes *JavascriptInterface*

-keep class android.support.v4.** { *; }
-dontwarn android.support.v4.*
-dontwarn android.support.**
#v4继承的Fragment都不要混淆
-keep public class * extends android.support.v4.app.Fragment

#---------------------------------------------------eventbus3.1.1 begin ---------------------------------------------------
-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
#---------------------------------------------------eventbus3.1.1 end ---------------------------------------------------

#---------------------------------------------------imageloader glide begin ---------------------------------------------------
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# for DexGuard only
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule
#---------------------------------------------------imageloader glide end ---------------------------------------------------



#-libraryjars libs/armeabi/libentryexstd.so
#-libraryjars libs/armeabi/liblocSDK6a.so

#-libraryjars libs/alipaySDK-20150818.jar
#-libraryjars libs/BaiduLBS_Android.jar
#-libraryjars libs/achartengine-1.1.0.jar
#-libraryjars libs/fastjson-1.1.38.jar
#-libraryjars libs/httpmime-4.2.2.jar
#-libraryjars libs/libammsdk.jar
#-libraryjars libs/UPPayAssistEx.jar
#-libraryjars libs/UPPayPluginExStd.jar
#-libraryjars libs/umeng-analytics-v5.6.3.jar
#-libraryjars libs/UPPayAssistEx.jar
#-libraryjars libs/UPPayPluginExStd.jar
#-libraryjars libs/wftsdk2.0.jar
-keep public interface com.tencent.**
-keep public class com.tencent.** {*;}
-keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
-keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}

-keep class com.tencent.** {*;}
-dontwarn com.tencent.**

-keep class com.tencent.open.TDialog$*
-keep class com.tencent.open.TDialog$* {*;}
-keep class com.tencent.open.PKDialog
-keep class com.tencent.open.PKDialog {*;}
-keep class com.tencent.open.PKDialog$*
-keep class com.tencent.open.PKDialog$* {*;}

-keepattributes Exceptions,InnerClasses,Signature
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable



-keep class com.umeng.socialize.sensor.**
-keep class com.umeng.socialize.handler.**
-keep class com.umeng.socialize.handler.*

-keep class com.umeng.scrshot.**

-keep public interface com.umeng.socialize.**
-keep public interface com.umeng.socialize.sensor.**
-keep public interface com.umeng.scrshot.**

-keep public class com.umeng.socialize.* {*;}

-dontwarn com.umeng.**




-dontwarn org.apache.commons.net.**
-dontwarn com.tencent.**
-dontwarn com.alibaba.fastjson.**
-dontwarn com.alipay.**
-dontwarn org.apache.http.entity.mime.**
-dontwarn com.baidu.**
-dontwarn android.support.**


-keep class com.alipay.** { *;}

-keep class org.apache.http.entity.mime.**{*;}
-keep class org.apache.http.entity.mime.content.**{*;}


#-keep class com.fuiou.pay.** {*;}
#-keep class com.fuiou.pay.activity.** {*;}
#-keep class com.fuiou.pay.plugin.** {*;}
#-keep class com.fuiou.pay.view.** { *; }

-keep class android.webkit.JavascriptInterface {*;}

-keep class com.fuiou.pay.plugin.JsPlugin {*;}
#-keep class com.fuiou.pay.activity.FyWebActivity {*;}

-keepclassmembers class com.fuiou.pay.activity.FyWebActivity {
   public *;
 }

 -keep public class com.fuiou.pay.plugin.JsPlugin$JSCallBack {
     public *;
 }

#-keepnames class com.fuiou.pay.plugin.JsPlugin$* {
#    public <fields>;
#    public <methods>;
#}


-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }

-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }

#-keepattributes *JavascriptInterface*


-keep class com.tencent.mm.sdk.openapi.** {*;}
-keep class com.tencent.mm.sdk.openapi.WXMediaMessage {*;}
-keep class com.tencent.mm.sdk.openapi.** implements com.tencent.mm.sdk.openapi.WXMediaMessage$IMediaObject {*;}


#------------------------百度--------------------------
#-keep class com.baidu.mapapi.** {*; }
#-keep class assets.** {*; }
#-keep class com.baidu.** {*; }
#-keep class vi.com.gdi.bgl.** {*; }
#-keep class com.baidu.location.h.c.** {*; }
-keep class com.baidu.** {*;}
-keep class vi.com.** {*;}
-dontwarn com.baidu.**

#-------------------------------------------------------------银联支付start-------------------------------------------------------------
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose
-dontoptimize
-dontwarn com.unionpay.**
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService

-keep class org.simalliance.openmobileapi.** {*;}
-keep class org.simalliance.openmobileapi.service.** {*;}

-keep class com.unionpay.** {*;}

-keep  public class com.unionpay.uppay.net.HttpConnection {
	public <methods>;
}
-keep  public class com.unionpay.uppay.net.HttpParameters {
	public <methods>;
}
-keep  public class com.unionpay.uppay.model.BankCardInfo {
	public <methods>;
}
-keep  public class com.unionpay.uppay.model.PAAInfo {
	public <methods>;
}
-keep  public class com.unionpay.uppay.model.ResponseInfo {
	public <methods>;
}
-keep  public class com.unionpay.uppay.model.PurchaseInfo {
	public <methods>;
}
-keep  public class com.unionpay.uppay.util.DeviceInfo {
	public <methods>;
}
-keep  public class java.util.HashMap {
	public <methods>;
}
-keep  public class java.lang.String {
	public <methods>;
}
-keep  public class java.util.List {
	public <methods>;
}
-keep  public class com.unionpay.uppay.util.PayEngine {
	public <methods>;
	native <methods>;
}

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keep  public class com.unionpay.utils.UPUtils {
	native <methods>;
}
#-------------------------------------------------------------银联支付end-------------------------------------------------------------
#-------------------------------------------------------------友盟分享start-------------------------------------------------------------
-dontusemixedcaseclassnames
    -dontshrink
    -dontoptimize
    -dontwarn com.google.android.maps.**
    -dontwarn android.webkit.WebView
    -dontwarn com.umeng.**
    -dontwarn com.tencent.weibo.sdk.**
    -dontwarn com.facebook.**
    -keep public class javax.**
    -keep public class android.webkit.**
    -dontwarn android.support.v4.**
    -keep enum com.facebook.**
    -keepattributes Exceptions,InnerClasses,Signature
    -keepattributes *Annotation*
    -keepattributes SourceFile,LineNumberTable

    -keep public interface com.facebook.**
    -keep public interface com.tencent.**
    -keep public interface com.umeng.socialize.**
    -keep public interface com.umeng.socialize.sensor.**
    -keep public interface com.umeng.scrshot.**
    -keep class com.android.dingtalk.share.ddsharemodule.** { *; }
    -keep public class com.umeng.socialize.* {*;}


    -keep class com.facebook.**
    -keep class com.facebook.** { *; }
    -keep class com.umeng.scrshot.**
    -keep public class com.tencent.** {*;}
    -keep class com.umeng.socialize.sensor.**
    -keep class com.umeng.socialize.handler.**
    -keep class com.umeng.socialize.handler.*
    -keep class com.umeng.weixin.handler.**
    -keep class com.umeng.weixin.handler.*
    -keep class com.umeng.qq.handler.**
    -keep class com.umeng.qq.handler.*
    -keep class UMMoreHandler{*;}
    -keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
    -keep class com.tencent.mm.sdk.modelmsg.** implements   com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
    -keep class im.yixin.sdk.api.YXMessage {*;}
    -keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}
    -keep class com.tencent.mm.sdk.** {
     *;
    }
    -keep class com.tencent.mm.opensdk.** {
   *;
    }
    -dontwarn twitter4j.**
    -keep class twitter4j.** { *; }

    -keep class com.tencent.** {*;}
    -dontwarn com.tencent.**
    -keep public class com.umeng.com.umeng.soexample.R$*{
    public static final int *;
    }
    -keep public class com.linkedin.android.mobilesdk.R$*{
    public static final int *;
        }
    -keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
    }

    -keep class com.tencent.open.TDialog$*
    -keep class com.tencent.open.TDialog$* {*;}
    -keep class com.tencent.open.PKDialog
    -keep class com.tencent.open.PKDialog {*;}
    -keep class com.tencent.open.PKDialog$*
    -keep class com.tencent.open.PKDialog$* {*;}

    -keep class com.sina.** {*;}
    -dontwarn com.sina.**
    -keep class  com.alipay.share.sdk.** {
       *;
    }
    -keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
    }

    -keep class com.linkedin.** { *; }
    -keepattributes Signature
#-------------------------------------------------------------友盟分享end-------------------------------------------------------------
#-------------------------------------------------------------友盟统计begin-------------------------------------------------------------
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keep public class com.exchange_v1.app.R$*{
public static final int *;
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
#-------------------------------------------------------------友盟统计end-------------------------------------------------------------

#-injars      bin/classes

# 不加入，防止报错
#-injars      libs

#-outjars     bin/classes-processed.jar



-keep class com.github.mikephil.charting.** { *; }

#-keep class com.baidu.** { *; }
#-keep class vi.com.gdi.bgl.android.**{*;}

-keep class com.alibaba.fastjson.** {*;}



#----------------------------------------------------------------------------
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

# adding this in to preserve line numbers so that the stack traces
# can be remapped
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable
#百度统计混淆
-keep class com.baidu.bottom.** { *; }
-keep class com.baidu.kirin.** { *; }
-keep class com.baidu.mobstat.** { *; }

#网页七鱼
-dontwarn com.qiyukf.**
-keep class com.qiyukf.** {*;}
#TabLayout
-keep class android.support.design.widget.TabLayout** {*;}

-keep class com.huawei.hms.**{*;}
-dontwarn com.huawei.**
-keep interface com.huawei.android.hms.agent.common.INoProguard {*;}
-keep class * extends com.huawei.android.hms.agent.common.INoProguard {*;}
-dontwarn com.coloros.mcsdk.**
-keep class com.coloros.mcsdk.** { *; }
-dontwarn com.vivo.push.**
-keep class com.vivo.push.**{*; }
-keep class com.vivo.vms.**{*; }
-dontwarn com.xiaomi.push.**
-keep class com.xiaomi.push.** { *; }
