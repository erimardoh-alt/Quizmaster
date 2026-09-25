package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val QuizMasterColorScheme = darkColorScheme(
  primary = NeonCyan,
  onPrimary = OnNeonCyan,
  primaryContainer = NeonCyanContainer,
  onPrimaryContainer = Color(0xFFA5F3FC),
  secondary = EmeraldChem,
  onSecondary = OnEmeraldChem,
  secondaryContainer = EmeraldChemContainer,
  onSecondaryContainer = Color(0xFFA7F3D0),
  tertiary = VioletIon,
  onTertiary = Color.White,
  tertiaryContainer = VioletIonContainer,
  onTertiaryContainer = Color(0xFFF3E8FF),
  background = ObsidianBg,
  onBackground = TextPrimary,
  surface = ObsidianSurface,
  onSurface = TextPrimary,
  surfaceVariant = ObsidianCard,
  onSurfaceVariant = TextSecondary,
  outline = ObsidianCardBorder,
  error = CrimsonHazard,
  onError = Color.White,
  errorContainer = CrimsonHazardContainer,
  onErrorContainer = Color(0xFFFECACA)
)

@Composable
fun QuizMasterTheme(
  content: @Composable () -> Unit
) {
  // Always intuitive, immersive dark mode as requested for chemistry laboratory aesthetic
  MaterialTheme(
    colorScheme = QuizMasterColorScheme,
    typography = Typography,
    content = content
  )
}
