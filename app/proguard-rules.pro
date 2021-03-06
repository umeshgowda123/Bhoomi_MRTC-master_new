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

-keepattributes Signature
-keepattributes Annotation

-keep class org.xmlpull.v1.* {*;}
-keepattributes Exceptions
-keepclassmembers class * {
    *** get*();
    void set*(***);
}
-keep public interface com.somepackage.SomeClass$someInterface {*;}
-keep class app.bmc.com.BHOOMI_MRTC.model.** { <fields>; }
-keep public class org.jsoup.** {
    public *;
}

-dontwarn java.lang.invoke.*
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn rx.**
-dontwarn retrofit.**
-dontwarn retrofit.appengine.UrlFetchClient
-dontwarn org.xmlpull.v1.**
-dontwarn org.xmlpull.v1.XmlPullParser
-dontwarn org.xmlpull.v1.XmlSerializer


