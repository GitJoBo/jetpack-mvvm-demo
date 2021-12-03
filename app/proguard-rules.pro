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
-keepattributes Signature

# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**

# JSR 305 annotations are for embedding nullability information.
-dontwarn javax.annotation.**

# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*

# OkHttp platform used only on JVM and when Conscrypt dependency is available.
-dontwarn okhttp3.internal.platform.ConscryptPlatform

#fastjson混淆
-keepattributes Signature
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.**{*;}
-keep class com.alibaba.fastjson.**{*; }
-keep public class com.ninstarscf.ld.model.entity.**{*;}

##Gson混淆
# 在处理字段时，Gson使用存储在类文件中的泛型类型信息。 Proguard默认会删除此类信息，因此请对其进行配置以保留所有信息。
-keepattributes Signature
# 用于使用GSON @Expose批注
-keepattributes *Annotation*
# Gson specific classes
-dontwarn sun.misc.**
-keep class com.google.gson.stream.** { *; }
# 将下面com.google.gson.examples.android.model 换成你自己项目的实体类的包名
-keep class com.google.gson.examples.android.model.** { <fields>; }
-keep class com.bizeng.lh.**{*;}
#防止proguard从TypeAdapterFactory，JsonSerializer，JsonDeserializer实例中剥离接口信息（因此可以在@JsonAdapter中使用它们）
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
#防止R8将数据对象好友始终保留为null
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

# 使用了以下两个 EventBus 之一的话，加入对应规则
-keep class org.greenrobot.eventbus.EventBus {*;}
-keep class org.simple.eventbus.EventBus {*;}

# 友盟
-keep class com.umeng.** { *; }
-keep class com.uc.** { *; }
-keep class com.efs.** { *; }

-keepclassmembers class*{
     public<init>(org.json.JSONObject);
}
-keepclassmembers enum*{
      publicstatic**[] values();
      publicstatic** valueOf(java.lang.String);
}
-keep public class com.bizeng.lh.R$*{
public static final int *;
}

# 使用了 uix 的时候加入以下规则
-keep class me.shouheng.uix.common.UIX {*;}

# 如果使用了 ViewBinding 则需要添加以下规则，并将包名替换为自己应用的包名
-keep class com.bizeng.bz.databinding.** {
    public static * inflate(android.view.LayoutInflater);
}

#liveeventbus
-dontwarn com.jeremyliao.liveeventbus.**
-keep class com.jeremyliao.liveeventbus.** { *; }
-keep class androidx.lifecycle.** { *; }
-keep class androidx.arch.core.** { *; }

#AndroidPicker
-keepattributes InnerClasses,Signature
-keepattributes *Annotation*
-keep class cn.qqtheme.framework.entity.** { *;}

#EventBus3.0
-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
# And if you use AsyncExecutor:
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

#bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
-keep class android.support.**{*;}

#腾讯云
-keep class com.tencent.** { *; }

#JZPlayer 列表视频播放控件
-keep public class cn.jzvd.JZMediaSystem {*; }
-keep public class cn.jzvd.demo.CustomMedia.CustomMedia {*; }
-keep public class cn.jzvd.demo.CustomMedia.JZMediaIjk {*; }
-keep public class cn.jzvd.demo.CustomMedia.JZMediaSystemAssertFolder {*; }
-keep class tv.danmaku.ijk.media.player.** {*; }
-dontwarn tv.danmaku.ijk.media.player.*
-keep interface tv.danmaku.ijk.media.player.** { *; }

#SVGAPlayer-Android
#-keep class com.squareup.wire.** { *; }
#-keep class com.opensource.svgaplayer.proto.** { *; }

#支付宝
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}
-keep class com.alipay.sdk.app.H5PayCallback {
    <fields>;
    <methods>;
}
-keep class com.alipay.android.phone.mrpc.core.** { *; }
-keep class com.alipay.apmobilesecuritysdk.** { *; }
-keep class com.alipay.mobile.framework.service.annotation.** { *; }
-keep class com.alipay.mobilesecuritysdk.face.** { *; }
-keep class com.alipay.tscenter.biz.rpc.** { *; }
-keep class org.json.alipay.** { *; }
-keep class com.alipay.tscenter.** { *; }
-keep class com.ta.utdid2.** { *;}
-keep class com.ut.device.** { *;}
-keep class com.hjq.bar.** {*;}