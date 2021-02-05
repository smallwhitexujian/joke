# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/xujian/Documents/Android eclipse/android_DEV/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
# Save the obfuscation mapping to a file, so you can de-obfuscate any stack
# traces later on.
#-printmapping bin/classes-processed.map
# You can print out the seeds that are matching the keep options below.
#-printseeds bin/classes-processed.seeds
# Preverification is irrelevant for the dex compiler and the Dalvik VM.
-dontpreverify
# Reduce the size of the output some more.
-repackageclasses ''
-allowaccessmodification
# Switch off some optimizations that trip older versions of the Dalvik VM.
-optimizations !code/simplification/arithmetic
#下面这行代码是 忽略警告，避免打包时某些警告出现
-ignorewarnings
# Keep a fixed source file attribute and all line number tables to get line
# numbers in the stack traces.
# You can comment this out if you're not interested in stack traces.
#-renamesourcefileattribute SourceFile
#-keepattributes SourceFile,LineNumberTable
# RemoteViews might need annotations.
-keepattributes *Annotation*
# Preserve all fundamental application classes.
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
# Preserve all View implementations, their special context constructors, and
# their setters.
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
    *** get*();
}  #保护注解
     -keepattributes *Annotation*
# Preserve all classes that have special context constructors, and the
# constructors themselves.
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
# Preserve all classes that have special context constructors, and the
# constructors themselves.
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
# Preserve the special fields of all Parcelable implementations.
-keepclassmembers class * implements android.os.Parcelable {
    static android.os.Parcelable$Creator CREATOR;
}
# Preserve static fields of inner classes of R classes that might be accessed
# through introspection.
-keepclassmembers class **.R$* {
  public static <fields>;
}
# Preserve the required interface from the License Verification Library
# (but don't nag the developer if the library is not used at all).
-keep public interface com.android.vending.licensing.ILicensingService
-dontnote com.android.vending.licensing.ILicensingService
-dontwarn android.support.**
# Preserve all native method names and the names of their classes.
-keepclasseswithmembernames class * {
    native <methods>;
}

#枚举
-keepclassmembers class * extends java.lang.Enum {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
#忽略自己项目调用
-keep class com.xujian.joke.Model.** { *; }
-keep class com.xj.utils.utils**{*;}

# fresco
-dontwarn com.xj.frescolib.**
-keep class com.xj.frescolib.**{*;}
# Facebook
-dontwarn com.facebook.**
-keep class com.facebook.** {*;}

# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.

# 豌豆荚
-keep class com.wandoujia.ads.sdk.** { *; }
-dontwarn com.wandoujia.ads.sdk.**
-dontwarn android.support.**
-dontwarn com.squareup.**
-dontwarn okio.**
-keep class com.wandoujia.ads.sdk.** { *; }

##okhttp
-dontwarn okhttp3.**
-keep class okhttp3.** { *;}
-dontwarn okio.**
-keep class okio.** {*;}

#gson
-keep class sun.misc.** { *; }
-keep class com.google.gson.examples.android.model.** { *; }


# ProGuard configurations for NetworkBench Lens
-keep class com.networkbench.** { *; }
-dontwarn com.networkbench.**
-keepattributes Exceptions, Signature
# End NetworkBench Lens

-keep class com.qq.e.** {
   public protected *;
   }
   -keep class android.support.v4.app.NotificationCompat**{
   public *;
   }