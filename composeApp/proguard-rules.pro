# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Keep Kotlin Metadata (required for reflection)
-keepattributes *Annotation*, InnerClasses, Signature, Exception
-keep class kotlin.Metadata { *; }

# Kotlinx Serialization - Keep only serializers and annotated classes
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt

# Keep serializer classes for @Serializable annotated classes
-keep,includedescriptorclasses class **$$serializer { *; }

# Keep Companion objects only for serializable classes in our packages
-keepclassmembers @kotlinx.serialization.Serializable class es.jgm1997.** {
    *** Companion;
    kotlinx.serialization.KSerializer serializer(...);
}

# Keep serialization infrastructure
-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Keep specific serialization classes needed at runtime
-keep class kotlinx.serialization.KSerializer { *; }
-keep class kotlinx.serialization.Serializable { *; }
-keep class kotlinx.serialization.descriptors.** { *; }
# Only keep the necessary internal serialization classes for primitive types
-keep class kotlinx.serialization.internal.*Serializer { *; }
-dontwarn kotlinx.serialization.**

# Ktor Client - Keep only necessary runtime classes
-keepclassmembers class io.ktor.client.** { *; }
-keep class io.ktor.client.engine.** { *; }
-keep class io.ktor.client.plugins.** { *; }
-keep interface io.ktor.** { *; }
-dontwarn io.ktor.**
-dontwarn kotlinx.atomicfu.**
-dontwarn io.netty.**
-dontwarn com.typesafe.**
-dontwarn org.slf4j.**

# Kotlinx Coroutines - Keep only public API
-keepnames class kotlinx.coroutines.** { *; }
-keep class kotlinx.coroutines.CoroutineScope { *; }
-keep class kotlinx.coroutines.CoroutineDispatcher { *; }
-dontwarn kotlinx.coroutines.**

# Koin Dependency Injection - Keep DSL and core classes
-keep class org.koin.core.** { *; }
-keep class org.koin.dsl.** { *; }
-keep class org.koin.android.** { *; }

# Voyager Navigation - Keep navigator and screen classes
-keep class cafe.adriel.voyager.** { *; }
-keep interface cafe.adriel.voyager.** { *; }

# Jetpack Compose - Keep only @Composable functions and runtime
-keepclassmembers class androidx.compose.runtime.** {
    public <methods>;
}
-keep class androidx.compose.runtime.Composer { *; }
-keep class androidx.compose.runtime.ComposerKt { *; }
# Keep only specific platform classes needed
-keep class androidx.compose.ui.platform.AndroidCompositionLocals_androidKt { *; }
-keep class androidx.compose.ui.platform.DisposableSaveableStateRegistry_androidKt { *; }
-dontwarn androidx.compose.**

# AndroidX - Keep only essential classes
-keep class androidx.lifecycle.ViewModel { *; }
-keep class androidx.lifecycle.ViewModelProvider { *; }
-keep class androidx.activity.ComponentActivity { *; }
-dontwarn androidx.**

# Keep application models and data classes (specific packages)
-keep class es.jgm1997.mgfisiobook.core.models.** { *; }
-keep class es.jgm1997.shared.core.models.** { *; }

# Keep application repositories and use cases
-keepclassmembers class es.jgm1997.**.repository.** { *; }
-keepclassmembers class es.jgm1997.**.usecase.** { *; }

# Keep ViewModel classes
-keep class * extends androidx.lifecycle.ViewModel {
    <init>(...);
}

# Keep Koin module classes
-keep class es.jgm1997.**.di.** { *; }

# JWT - Keep only public API
-keep class com.appstractive.jwt.** { *; }
-dontwarn com.appstractive.**

# Keep enum classes
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Keep native methods
-keepclasseswithmembernames class * {
    native <methods>;
}

# Keep custom exceptions
-keep public class * extends java.lang.Exception

# Keep Parcelables
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

