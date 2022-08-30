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


# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
-renamesourcefileattribute SourceFile


# - - - - - - - SQLITE CIPHER - START - - - - - - - - - - - -
-keep class net.sqlcipher.** { *; }
-keep class net.sqlcipher.database.* { *; }
-keep,includedescriptorclasses class net.sqlcipher.** { *; }
-keep,includedescriptorclasses interface net.sqlcipher.** { *; }
# - - - - - - - SQLITE CIPHER - END - - - - - - - - - - - - -


# - - - - - - - RETROFIT - START - - - - - - - - - - - - - - -
# Retrofit does reflection on generic parameters. InnerClasses is required to use Signature and
# EnclosingMethod is required to use InnerClasses.
-keepattributes Signature, InnerClasses, EnclosingMethod
# Retrofit does reflection on method and parameter annotations.
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**
# Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit
# Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*
# With R8 full mode, it sees no subtypes of Retrofit interfaces since they are created with a Proxy
# and replaces all potential values with null. Explicitly keeping the interfaces prevents this.
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>
# - - - - - - - RETROFIT - END - - - - - - - - - - - - - - -

# - - - - - - - OK HTTP - START - - - - - - - - - - - - - - -
# JSR 305 annotations are for embedding nullability information.
-dontwarn javax.annotation.**

# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*

# OkHttp platform used only on JVM and when Conscrypt dependency is available.
-dontwarn okhttp3.internal.platform.ConscryptPlatform
-dontwarn org.conscrypt.ConscryptHostnameVerifier
# - - - - - - - OK HTTP - END - - - - - - - - - - - - - - - -

# - - - - - - - KOTLIN COROUTIN - START - - - - - - - - - - -
-keep class kotlinx.coroutines.android.** {*;}
# - - - - - - - KOTLIN COROUTIN - END - - - - - - - - - - - -

# - - - - - - - GSON - START - - - - - - - - - - - - - - - - -
-keepattributes Signature
# For using GSON @Expose annotation
-keepattributes *Annotation*
# Gson specific classes
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
#-keep class com.rma.myspeed.data.remote.response** { *; }
# Prevent proguard from stripping interface information from TypeAdapter, TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Prevent R8 from leaving Data object members always null
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}
# - - - - - - - GSON - END - - - - - - - - - - - - - - - - - -

# - - - - - - - FIREBASE CRASHLYTICS - START - - - - - - - - -
# If you're using custom Exception
-keep public class * extends java.lang.Exception
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**
-keep class com.google.firebase.crashlytics.** { *; }
-dontwarn com.google.firebase.crashlytics.**
# - - - - - - - FIREBASE CRASHLYTICS - END - - - - - - - - - - -

# - - - - - - - GLIDE - START - - - - - - - - - - - - - - - - - -
### Glide, Glide Okttp Module, Glide Transformations
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
# -keepresourcexmlelements manifest/application/meta-data@value=GlideModule 3 For dexguard
-dontwarn jp.co.cyberagent.android.gpuimage.**
# - - - - - - - GLIDE - END - - - - - - - - - - - - - - - - - - - -

# - - - - - - - ANDROID ARCHITECHTURE COMPONENT - START - - - - - -
### Android Architecture Components
# Ref: https://issuetracker.google.com/issues/62113696
# LifecycleObserver's empty constructor is considered to be unused by proguard
#-keepclassmembers class * implements android.arch.lifecycle.LifecycleObserver {
#    <init>(...);
#}
-keep class * implements android.arch.lifecycle.LifecycleObserver {
    <init>(...);
}

# ViewModel's empty constructor is considered to be unused by proguard
-keepclassmembers class * extends android.arch.lifecycle.ViewModel {
    <init>(...);
}

# keep Lifecycle State and Event enums values
-keepclassmembers class android.arch.lifecycle.Lifecycle$State { *; }
-keepclassmembers class android.arch.lifecycle.Lifecycle$Event { *; }
# keep methods annotated with @OnLifecycleEvent even if they seem to be unused
# (Mostly for LiveData.LifecycleBoundObserver.onStateChange(), but who knows)
-keepclassmembers class * {
    @android.arch.lifecycle.OnLifecycleEvent *;
}
# - - - - - - - ANDROID ARCHITECHTURE COMPONENT - END - - - - - -


# - - - - - - - DAGGER HILT - START - - - - - -
# Keep class names of Hilt injected ViewModels since their name are used as a multibinding map key.
-keepnames @dagger.hilt.android.lifecycle.HiltViewModel class * extends androidx.lifecycle.ViewModel
# - - - - - - - DAGGER HILT - END - - - - - -


# - - - - - - - EVENT BUS - START - - - - - -
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
# - - - - - - - EVENT BUS - END - - - - - -

# - - - - - - - androidx.lifecycle - START - - - - - -
-keepclassmembers enum androidx.lifecycle.Lifecycle$Event {
    <fields>;
}
-keep class * implements androidx.lifecycle.LifecycleObserver {
}
-keep class * implements androidx.lifecycle.GeneratedAdapter {
    <init>(...);
}
-keepclassmembers class ** {
    @androidx.lifecycle.OnLifecycleEvent *;
}
# - - - - - - - androidx.lifecycle - END - - - - - -

# - - - - - - - GOOGLE MAPS - START - - - - - -
#-keep class com.google.android.libraries.maps.lv.zzk { <init>();}
-keep,allowoptimization class com.google.android.libraries.maps.** { *; }
# - - - - - - - GOOGLE MAPS - END - - - - - -