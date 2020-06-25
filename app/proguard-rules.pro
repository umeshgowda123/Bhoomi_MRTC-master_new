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
-renamesourcefileattribute SourceFile
-flattenpackagehierarchy

-optimizationpasses 5
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-dump class_files.txt
-printseeds seeds.txt
-printusage unused.txt
-printmapping mapping.txt

-keep class com.example.datamodel.** { *; }
-keepattributes Signature
-keepattributes Annotation
-keep class retrofit.** { *; }
-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-keep class org.xmlpull.v1.* {*;}

-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn rx.**
-dontwarn retrofit.**
-dontwarn retrofit.appengine.UrlFetchClient
-dontwarn org.xmlpull.v1.**
-dontwarn org.xmlpull.v1.XmlPullParser
-dontwarn org.xmlpull.v1.XmlSerializer


