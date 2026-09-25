package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CompoundType
import com.example.data.model.DifficultyLevel
import com.example.ui.theme.AmberFlame
import com.example.ui.theme.CrimsonHazard
import com.example.ui.theme.EmeraldChem
import com.example.ui.theme.InorganicTag
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.ObsidianCard
import com.example.ui.theme.ObsidianCardBorder
import com.example.ui.theme.OrganicTag
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.VioletIon

@Composable
fun ChemicalFormulaBadge(
  formula: String,
  type: CompoundType,
  modifier: Modifier = Modifier
) {
  val (bgColor, textColor, borderCol) = when (type) {
    CompoundType.ORGANIC -> Triple(Color(0xFF063327), OrganicTag, Color(0xFF0F766E))
    CompoundType.INORGANIC -> Triple(Color(0xFF082F49), InorganicTag, Color(0xFF0284C7))
    CompoundType.ALL -> Triple(Color(0xFF2E1065), VioletIon, Color(0xFF7E22CE))
  }

  Surface(
    modifier = modifier,
    color = bgColor,
    shape = RoundedCornerShape(8.dp),
    border = BorderStroke(1.dp, borderCol)
  ) {
    Row(
      modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      Text(
        text = formula,
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp,
        color = textColor,
        fontFamily = FontFamily.Monospace
      )
      Box(
        modifier = Modifier
          .size(4.dp)
          .clip(CircleShape)
          .background(textColor)
      )
      Text(
        text = type.displayName,
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium,
        color = textColor.copy(alpha = 0.9f)
      )
    }
  }
}

@Composable
fun DifficultyBadge(
  difficulty: DifficultyLevel,
  modifier: Modifier = Modifier
) {
  val (color, stars) = when (difficulty) {
    DifficultyLevel.BEGINNER -> EmeraldChem to "★☆☆☆"
    DifficultyLevel.INTERMEDIATE -> NeonCyan to "★★☆☆"
    DifficultyLevel.ADVANCED -> AmberFlame to "★★★☆"
    DifficultyLevel.EXPERT -> CrimsonHazard to "★★★★"
  }

  Surface(
    modifier = modifier,
    color = color.copy(alpha = 0.12f),
    shape = RoundedCornerShape(16.dp),
    border = BorderStroke(1.dp, color.copy(alpha = 0.4f))
  ) {
    Row(
      modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
      Text(
        text = stars,
        fontSize = 11.sp,
        color = color,
        fontWeight = FontWeight.Bold
      )
      Text(
        text = difficulty.title,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        color = color
      )
    }
  }
}

@Composable
fun DetailedExplanationCard(
  isCorrect: Boolean,
  compoundName: String,
  formula: String,
  explanation: String,
  chemicalFact: String,
  reactionEquation: String? = null,
  modifier: Modifier = Modifier
) {
  val statusColor = if (isCorrect) EmeraldChem else CrimsonHazard

  Card(
    modifier = modifier.fillMaxWidth(),
    colors = CardDefaults.cardColors(containerColor = ObsidianCard),
    shape = RoundedCornerShape(16.dp),
    border = BorderStroke(1.5.dp, statusColor.copy(alpha = 0.6f))
  ) {
    Column(
      modifier = Modifier.padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      // Header
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Icon(
          imageVector = if (isCorrect) Icons.Default.CheckCircle else Icons.Default.Warning,
          contentDescription = null,
          tint = statusColor,
          modifier = Modifier.size(24.dp)
        )
        Text(
          text = if (isCorrect) "¡Respuesta Correcta!" else "Respuesta Incorrecta",
          fontWeight = FontWeight.Bold,
          fontSize = 17.sp,
          color = statusColor
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
          text = formula,
          fontFamily = FontFamily.Monospace,
          fontWeight = FontWeight.ExtraBold,
          fontSize = 15.sp,
          color = NeonCyan
        )
      }

      // Compound identity
      Text(
        text = "Compuesto: $compoundName",
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        color = TextPrimary
      )

      // Detailed scientific reason
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(10.dp))
          .background(Color(0xFF0F172A))
          .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Science,
            contentDescription = null,
            tint = NeonCyan,
            modifier = Modifier.size(16.dp)
          )
          Text(
            text = "Explicación Científica Detallada:",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = NeonCyan
          )
        }
        Text(
          text = explanation,
          fontSize = 13.5.sp,
          lineHeight = 19.sp,
          color = TextSecondary
        )
      }

      // Reaction equation if present
      if (!reactionEquation.isNullOrBlank()) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF0B1120))
            .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(8.dp))
            .padding(10.dp)
        ) {
          Text(
            text = "Ecuación / Reacción Química:",
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = VioletIon
          )
          Spacer(modifier = Modifier.height(3.dp))
          Text(
            text = reactionEquation,
            fontFamily = FontFamily.Monospace,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFE2E8F0)
          )
        }
      }

      // Chemical Fact / Real-world Application
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(10.dp))
          .background(AmberFlame.copy(alpha = 0.1f))
          .padding(10.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Icon(
          imageVector = Icons.Default.Lightbulb,
          contentDescription = null,
          tint = AmberFlame,
          modifier = Modifier.size(18.dp)
        )
        Column {
          Text(
            text = "Dato del Laboratorio / Aplicación:",
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            color = AmberFlame
          )
          Text(
            text = chemicalFact,
            fontSize = 12.5.sp,
            color = TextSecondary,
            lineHeight = 17.sp
          )
        }
      }
    }
  }
}

@Composable
fun PowerUpChip(
  powerUp: com.example.data.model.PowerUpType,
  isAvailable: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val borderCol = if (isAvailable) NeonCyan.copy(alpha = 0.6f) else Color(0xFF334155)
  val bgCol = if (isAvailable) ObsidianCard else Color(0xFF0F172A)

  Surface(
    modifier = modifier
      .clip(RoundedCornerShape(12.dp))
      .clickable(enabled = isAvailable, onClick = onClick)
      .testTag("power_up_${powerUp.name.lowercase()}"),
    shape = RoundedCornerShape(12.dp),
    color = bgCol,
    border = BorderStroke(1.dp, borderCol)
  ) {
    Row(
      modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      Text(
        text = powerUp.iconLabel,
        fontSize = 16.sp
      )
      Column {
        Text(
          text = powerUp.title,
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = if (isAvailable) TextPrimary else TextMuted
        )
        Text(
          text = if (isAvailable) "Disponible" else "Usado",
          fontSize = 10.sp,
          color = if (isAvailable) NeonCyan else TextMuted
        )
      }
    }
  }
}

@Composable
fun ChemicalProgressBar(
  progress: Float,
  color: Color = NeonCyan,
  modifier: Modifier = Modifier
) {
  val animatedProgress by animateFloatAsState(
    targetValue = progress.coerceIn(0f, 1f),
    animationSpec = tween(400),
    label = "progress"
  )

  LinearProgressIndicator(
    progress = { animatedProgress },
    modifier = modifier
      .fillMaxWidth()
      .height(8.dp)
      .clip(RoundedCornerShape(4.dp)),
    color = color,
    trackColor = Color(0xFF1E293B),
    strokeCap = StrokeCap.Round
  )
}
