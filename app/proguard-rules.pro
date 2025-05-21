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
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Gson
-keepattributes Signature
-keepattributes EnclosingMethod
-keep class com.google.gson.reflect.TypeToken { *; }
-keep class * extends com.google.gson.reflect.TypeToken
# For keeping field names for GSON serialization/deserialization,
# if you are not using @SerializedName annotation on your models.
# If you are using @SerializedName, this might not be strictly necessary
# but can be a safeguard.
# -keepclassmembers,allowobfuscation class * {
#    @com.google.gson.annotations.SerializedName <fields>;
# }
# If you use @Expose, keep members annotated with it
# -keepclassmembers class * {
#   @com.google.gson.annotations.Expose <fields>;
# }

# OkHttp / Retrofit
-dontwarn okhttp3.**
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Exceptions
-keepclasseswithmembers interface * {
    @retrofit2.http.GET <methods>;
    @retrofit2.http.POST <methods>;
    @retrofit2.http.PUT <methods>;
    @retrofit2.http.DELETE <methods>;
    @retrofit2.http.PATCH <methods>;
    @retrofit2.http.OPTIONS <methods>;
    @retrofit2.http.HEAD <methods>;
}
# Keep OkHttp internal classes for Kotlin Coroutines support
-keep class okhttp3.internal.platform.* { *; }

# Kotlin Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.android.AndroidDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembernames class kotlinx.coroutines.flow.internal.AbstractSharedFlow {
  kotlinx.coroutines.flow.StateFlow getSubscriptionCount();
}

# Keep data classes (DTOs)
-keep class com.sample.cryptocurrencyapp.data.remote.dto.** { *; }