#####################################################
#            老版混淆规则
#####################################################
#
#---------------------------------基本指令区----------------------------------
#-optimizationpasses 5  #指定代码的压缩级别 0 - 7
#-dontusemixedcaseclassnames  #是否使用大小写混合
#-dontskipnonpubliclibraryclasses  #如果应用程序引入的有jar包，并且想混淆jar包里面的class
#-dontskipnonpubliclibraryclassmembers #不跳过非公共的库的类成员
#-dontpreverify  #混淆时是否做预校验（可去掉加快混淆速度）
#-verbose #混淆时是否记录日志（混淆后生产映射文件 map 类名 -> 转化后类名的映射
#-printmapping proguardMapping.txt
#-optimizations !code/simplification/cast,!field/*,!class/merging/*  #淆采用的算法
#-keepattributes *Annotation*,InnerClasses
#-keepattributes Signature  #保持泛型
#-keepattributes SourceFile,LineNumberTable  #保留行号
#---------------------------------默认保留区---------------------------------
#-keep public class * extends android.app.Activity  #所有activity的子类不要去混淆
#-keep public class * extends android.app.Application
#-keep public class * extends android.app.Service
#-keep public class * extends android.content.BroadcastReceiver
#-keep public class * extends android.content.ContentProvider
#-keep public class * extends android.app.backup.BackupAgentHelper
#-keep public class * extends android.preference.Preference
#-keep public class * extends android.view.View
#-keep public class com.android.vending.licensing.ILicensingService
#-keep class android.support.** {*;}
#
#-keepclasseswithmembernames class * {
#    native <methods>;
#}
#-keepclassmembers class * extends android.app.Activity{
#    public void *(android.view.View);
#}
#-keepclassmembers enum * {
#    public static **[] values();
#    public static ** valueOf(java.lang.String);
#}
##-keep public class * extends android.view.View{
##    *** get*();
##    void set*(***);
##    public <initCompare>(android.content.Context);
##    public <initCompare>(android.content.Context, android.util.AttributeSet);
##    public <initCompare>(android.content.Context, android.util.AttributeSet, int);
##}
##-keepclasseswithmembers class * {
##    public <initCompare>(android.content.Context, android.util.AttributeSet);
##    public <initCompare>(android.content.Context, android.util.AttributeSet, int);
##}
#-keep class * implements android.os.Parcelable {
#  public static final android.os.Parcelable$Creator *;
#}
##保持所有实现 Serializable 接口的类成员
#-keepclassmembers class * implements java.io.Serializable {
#    static final long serialVersionUID;
#    private static final java.io.ObjectStreamField[] serialPersistentFields;
#    private void writeObject(java.io.ObjectOutputStream);
#    private void readObject(java.io.ObjectInputStream);
#    java.lang.Object writeReplace();
#    java.lang.Object readResolve();
#}
#-keep class **.R$* {
# *;
#}
#-keepclassmembers class * {
#    void *(**On*Event);
#}
#-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
#   public *;
#}
#-keepclassmembers class * extends android.webkit.WebViewClient {
#    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
#    public boolean *(android.webkit.WebView, java.lang.String);
#}
#-keepclassmembers class * extends android.webkit.WebViewClient {
#    public void *(android.webkit.WebView, jav.lang.String);
#}

#---------------------------------1.实体类---------------------------------
#-keep class com.ins.common.entity.** { *; }
-keep class **.entity.** { *; }
-keep class **.bean.** { *; }
#-keep class com.ins.aimai.bean.** { *; }
#---------------------------------2.第三方包-------------------------------
#极光推送
-dontwarn cn.jpush.**
-keep class cn.jpush.**{*;}
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
#不晓得是干嘛的第三方
-dontwarn com.beloo.**
-keep class com.beloo.**{*;}
#okhttp
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}
-keep interface okhttp3.**{*;}
#okio
-dontwarn okio.**
-keep class okio.**{*;}
-keep interface okio.**{*;}
#Retrofit2
-dontwarn retrofit2.**
-keep class retrofit2.** {*;}
-keepattributes Signature
-keepattributes Exceptions
#RxJava
-dontwarn rx.**
-keep class rx.**{*;}
#GreenDao
-dontwarn org.greenrobot.greendao.database.**
-keep class org.greenrobot.greendao.**{*;}
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties
#ijkplayer
-keep class tv.danmaku.ijk.media.player.** {*;}
-keep class tv.danmaku.ijk.media.player.IjkMediaPlayer{*;}
-keep class tv.danmaku.ijk.media.player.ffmpeg.FFmpegApi{*;}
#sharesdk
-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class **.R$* {*;}
-keep class **.R{*;}
-keep class com.mob.**{*;}
-dontwarn com.mob.**
-dontwarn cn.sharesdk.**
-dontwarn **.R$*
#eventbus 3.0
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(Java.lang.Throwable);
}
#bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}

#支付宝支付
#-libraryjars libs/alipaySdk-20160825.jar
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}
-dontwarn android.net.**
-keep class android.net.SSLCertificateSocketFactory{*;}
#微信支付
-keep class com.tencent.mm.sdk.** {*;}

#glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-dontwarn com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
-dontwarn com.bumptech.glide.load.resource.bitmap.Downsampler
-dontwarn com.bumptech.glide.load.resource.bitmap.HardwareConfigState

#####################################################
#            新版混淆规则
#####################################################

#指定压缩级别
-optimizationpasses 5
#不跳过非公共的库的类成员
-dontskipnonpubliclibraryclassmembers
#混淆时采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#把混淆类中的方法名也混淆了
-useuniqueclassmembernames
#优化时允许访问并修改有修饰符的类和类的成员
-allowaccessmodification
#将文件来源重命名为“SourceFile”字符串
-renamesourcefileattribute SourceFile
#保留行号
-keepattributes SourceFile,LineNumberTable
#保持泛型
-keepattributes Signature

#保持所有实现 Serializable 接口的类成员
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keep class * implements Android.os.Parcelable { # 保持Parcelable不被混淆
    public static final Android.os.Parcelable$Creator *;
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclassmembers class * {
    void *(**On*Event);
}
-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
   public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}

#Fragment不需要在AndroidManifest.xml中注册，需要额外保护下
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.app.Fragment

# 保持测试相关的代码
-dontnote junit.framework.**
-dontnote junit.runner.**
-dontwarn android.test.**
-dontwarn android.support.test.**
-dontwarn org.junit.**

