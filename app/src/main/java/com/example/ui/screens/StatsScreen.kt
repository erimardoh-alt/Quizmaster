package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CompoundType
import com.example.ui.components.ChemicalFormulaBadge
import com.example.ui.components.ChemicalProgressBar
import com.example.ui.theme.AmberFlame
import com.example.ui.theme.CrimsonHazard
import com.example.ui.theme.EmeraldChem
import com.example.ui.theme.InorganicTag
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.ObsidianBg
import com.example.ui.theme.ObsidianCard
import com.example.ui.theme.ObsidianCardBorder
import com.example.ui.theme.OrganicTag
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.VioletIon
import com.example.ui.viewmodel.StatsDashboardData

@Composable
fun StatsScreen(
  data: StatsDashboardData,
  modifier: Modifier = Modifier
) {
  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(ObsidianBg)
      .padding(horizontal = 16.dp),
    contentPadding = PaddingValues(top = 16.dp, bottom = 90.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // 1. Profile & Chemistry Rank Header Card
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("stats_profile_header"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianCard),
        border = BorderStroke(1.5.dp, Brush.horizontalGradient(listOf(NeonCyan, VioletIon)))
      ) {
        Column(
          modifier = Modifier.padding(18.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
              Surface(
                modifier = Modifier.size(54.dp),
                shape = CircleShape,
                color = NeonCyan.copy(alpha = 0.2f),
                border = BorderStroke(2.dp, NeonCyan)
              ) {
                Box(contentAlignment = Alignment.Center) {
                  Text(text = "🧑‍🔬", fontSize = 28.sp)
                }
              }

              Column {
                Text(
                  text = data.currentRankTitle,
                  fontWeight = FontWeight.ExtraBold,
                  fontSize = 17.sp,
                  color = TextPrimary
                )
                Text(
                  text = "Nivel de Alquimista: ${data.currentRankLevel}",
                  fontSize = 12.sp,
                  color = TextSecondary
                )
              }
            }

            Surface(
              shape = RoundedCornerShape(12.dp),
              color = VioletIon.copy(alpha = 0.2f),
              border = BorderStroke(1.dp, VioletIon)
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
              ) {
                Text(text = "⚡", fontSize = 12.sp)
                Text(
                  text = "${data.totalXp} XP",
                  fontWeight = FontWeight.Black,
                  fontSize = 13.sp,
                  color = VioletIon
                )
              }
            }
          }

          // XP progress to next level
          Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(
                text = "Progreso de Rango Científico",
                fontSize = 11.5.sp,
                color = TextSecondary
              )
              Text(
                text = "${data.totalXp % 1000}/1000 XP",
                fontSize = 11.5.sp,
                fontWeight = FontWeight.Bold,
                color = NeonCyan
              )
            }
            ChemicalProgressBar(
              progress = ((data.totalXp % 1000).toFloat() / 1000f).coerceIn(0.1f, 1f),
              color = NeonCyan
            )
          }
        }
      }
    }

    // 2. Metrics Grid (KPIs)
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        // Precisión
        KpiCard(
          title = "Precisión Global",
          value = if (data.totalQuestionsAnswered > 0) "${data.overallAccuracyPercent}%" else "--",
          subtext = "${data.totalCorrectAnswers} aciertos",
          accentColor = EmeraldChem,
          icon = "🎯",
          modifier = Modifier.weight(1f)
        )

        // Racha
        KpiCard(
          title = "Racha de Días",
          value = "${data.currentStreakDays} d",
          subtext = "Fuego continuo",
          accentColor = AmberFlame,
          icon = "🔥",
          modifier = Modifier.weight(1f)
        )
      }
    }

    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        // Duelos 1v1
        KpiCard(
          title = "Duelos 1v1",
          value = "${data.duelWins}V / ${data.duelLosses}D",
          subtext = "En tiempo real",
          accentColor = VioletIon,
          icon = "⚔️",
          modifier = Modifier.weight(1f)
        )

        // Partidas
        KpiCard(
          title = "Partidas Totales",
          value = "${data.totalGames}",
          subtext = "${data.totalQuestionsAnswered} preguntas",
          accentColor = NeonCyan,
          icon = "🧪",
          modifier = Modifier.weight(1f)
        )
      }
    }

    // 3. Category Mastery (Organic vs Inorganic)
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianCard),
        border = BorderStroke(1.dp, ObsidianCardBorder)
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
          Text(
            text = "Dominio por Especialidad Química",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = TextPrimary
          )

          // Organic
          Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
              ) {
                Text(text = "🌿", fontSize = 14.sp)
                Text(
                  text = "Química Orgánica (Enlaces C-C, C-H)",
                  fontWeight = FontWeight.SemiBold,
                  fontSize = 13.sp,
                  color = TextPrimary
                )
              }
              Text(
                text = "${data.organicAccuracyPercent}%",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = OrganicTag
              )
            }
            ChemicalProgressBar(
              progress = data.organicAccuracyPercent / 100f,
              color = OrganicTag
            )
          }

          // Inorganic
          Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
              ) {
                Text(text = "💎", fontSize = 14.sp)
                Text(
                  text = "Química Inorgánica (Minerales, Iones, Redox)",
                  fontWeight = FontWeight.SemiBold,
                  fontSize = 13.sp,
                  color = TextPrimary
                )
              }
              Text(
                text = "${data.inorganicAccuracyPercent}%",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = InorganicTag
              )
            }
            ChemicalProgressBar(
              progress = data.inorganicAccuracyPercent / 100f,
              color = InorganicTag
            )
          }
        }
      }
    }

    // 4. Compuestos Químicos Dominados
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = EmeraldChem,
            modifier = Modifier.size(18.dp)
          )
          Text(
            text = "Compuestos Dominados (${data.masteredCompounds.size})",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = TextPrimary
          )
        }
      }
    }

    if (data.masteredCompounds.isEmpty()) {
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = ObsidianCard)
        ) {
          Text(
            text = "Acierta varias veces las preguntas de un compuesto (como CH₄ o H₂SO₄) para graduarlo a 'Dominado'.",
            modifier = Modifier.padding(14.dp),
            fontSize = 12.5.sp,
            color = TextSecondary
          )
        }
      }
    } else {
      item {
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          items(data.masteredCompounds) { compound ->
            Surface(
              shape = RoundedCornerShape(12.dp),
              color = EmeraldChem.copy(alpha = 0.12f),
              border = BorderStroke(1.dp, EmeraldChem.copy(alpha = 0.5f))
            ) {
              Column(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
              ) {
                Text(
                  text = compound.formula,
                  fontFamily = FontFamily.Monospace,
                  fontWeight = FontWeight.Bold,
                  fontSize = 14.sp,
                  color = EmeraldChem
                )
                Text(
                  text = compound.commonName,
                  fontSize = 11.sp,
                  color = TextPrimary
                )
                Text(
                  text = "✓ ${compound.correctCount} aciertos",
                  fontSize = 10.sp,
                  color = EmeraldChem
                )
              }
            }
          }
        }
      }
    }

    // 5. Compuestos por Repasar (Spaced Repetition)
    if (data.reviewCompounds.isNotEmpty()) {
      item {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = null,
            tint = AmberFlame,
            modifier = Modifier.size(18.dp)
          )
          Text(
            text = "Compuestos para Repasar (${data.reviewCompounds.size})",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = TextPrimary
          )
        }
      }

      items(data.reviewCompounds) { compound ->
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = ObsidianCard),
          border = BorderStroke(1.dp, AmberFlame.copy(alpha = 0.3f))
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = "${compound.formula} • ${compound.commonName}",
                fontWeight = FontWeight.Bold,
                fontSize = 13.5.sp,
                color = TextPrimary
              )
              Text(
                text = "${compound.incorrectCount} fallos recientes",
                fontSize = 11.5.sp,
                color = AmberFlame
              )
            }
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = AmberFlame.copy(alpha = 0.15f)
            ) {
              Text(
                text = "Repasar",
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = AmberFlame
              )
            }
          }
        }
      }
    }
  }
}

@Composable
fun KpiCard(
  title: String,
  value: String,
  subtext: String,
  accentColor: Color,
  icon: String,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier,
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianCard),
    border = BorderStroke(1.dp, accentColor.copy(alpha = 0.3f))
  ) {
    Column(
      modifier = Modifier.padding(14.dp),
      verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = title,
          fontSize = 11.5.sp,
          color = TextSecondary,
          fontWeight = FontWeight.SemiBold
        )
        Text(text = icon, fontSize = 14.sp)
      }
      Text(
        text = value,
        fontWeight = FontWeight.Black,
        fontSize = 20.sp,
        color = accentColor
      )
      Text(
        text = subtext,
        fontSize = 10.5.sp,
        color = TextMuted
      )
    }
  }
}
