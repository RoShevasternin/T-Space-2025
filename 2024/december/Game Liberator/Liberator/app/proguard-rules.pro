# LibGDX
-dontwarn javax.annotation.Nullable

# Вимкнення обфускації для класів, які беруть участь у десеріалізації
-keep class com.liberator.wisoliter.game.utils.** { *; }
-keepnames class com.liberator.wisoliter.game.utils.** { *; }
-keepclassmembers class com.liberator.wisoliter.game.utils.** { *; }

# Збереження імен для @Serializable та @SerialName
-keep @kotlinx.serialization.Serializable class * { *; }
-keepclassmembers class * {
    @kotlinx.serialization.SerialName <fields>;
}


# Appsflyer
-keep class com.appsflyer.** { *; }
-keep class kotlin.jvm.internal.** { *; }
-keep public class com.android.installreferrer.** { *; }