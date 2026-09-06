package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme =
  lightColorScheme(
    primary = PrimaryBronze,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,
    secondary = SecondaryCharcoal,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = OnSecondaryContainer,
    tertiary = TertiaryBronze,
    onTertiary = OnTertiary,
    tertiaryContainer = TertiaryContainer,
    background = SurfaceCanvas,
    onBackground = OnSurfaceText,
    surface = SurfaceCanvas,
    onSurface = OnSurfaceText,
    surfaceVariant = SurfaceHighest,
    onSurfaceVariant = OnSurfaceVariantText,
    surfaceContainerLowest = SurfaceLowest,
    surfaceContainerLow = SurfaceLow,
    surfaceContainer = SurfaceDefault,
    surfaceContainerHigh = SurfaceHigh,
    surfaceContainerHighest = SurfaceHighest,
    outline = OutlineColor,
    outlineVariant = OutlineVariantColor,
    error = ErrorRed,
    onError = OnPrimary,
    errorContainer = ErrorContainer,
    onErrorContainer = OnErrorContainer,
    inverseSurface = InverseSurfaceColor,
    inverseOnSurface = InverseOnSurfaceColor,
  )

private val DarkColorScheme =
  darkColorScheme(
    primary = PrimaryFixedDim,
    onPrimary = OnPrimaryFixed,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,
    secondary = SecondaryFixed,
    onSecondary = OnSecondary,
    tertiary = TertiaryFixed,
    background = InverseSurfaceColor,
    surface = InverseSurfaceColor,
    onSurface = InverseOnSurfaceColor,
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }

      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
