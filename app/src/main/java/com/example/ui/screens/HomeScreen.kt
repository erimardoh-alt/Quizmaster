package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.data.local.CustomQuizEntity
import com.example.data.model.CompoundType
import com.example.data.model.DifficultyLevel
import com.example.ui.components.DifficultyBadge
import com.example.ui.theme.AmberFlame
import com.example.ui.theme.CrimsonHazard
import com.example.ui.theme.EmeraldChem
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.ObsidianBg
import com.example.ui.theme.ObsidianCard
import com.example.ui.theme.ObsidianCardBorder
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.VioletIon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
  userXp: Int,
  userRank: String,
  streakDays: Int,
  customQuizzes: List<CustomQuizEntity>,
  onStartPresetQuiz: (CompoundType, DifficultyLevel) -> Unit,
  onStartCustomQuiz: (CustomQuizEntity) -> Unit,
  onNavigateToDuel: () -> Unit,
  onNavigateToCreateQuiz: () -> Unit,
  onDeleteCustomQuiz: (Long) -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedCategory by remember { mutableStateOf(CompoundType.ALL) }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(ObsidianBg)
      .padding(horizontal = 16.dp),
    contentPadding = PaddingValues(top = 16.dp, bottom = 90.dp),
    verticalArrangement = Arrangement.spacedBy(18.dp)
  ) {
    // 1. Top Header & User Status
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = NeonCyan.copy(alpha = 0.15f),
              border = BorderStroke(1.dp, NeonCyan.copy(alpha = 0.5f))
            ) {
              Text(
                text = "QM",
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                color = NeonCyan,
                fontWeight = FontWeight.Black,
                fontFamily = FontFamily.Monospace,
                fontSize = 14.sp
              )
            }
            Text(
              text = "QuizMaster",
              fontSize = 24.sp,
              fontWeight = FontWeight.ExtraBold,
              color = TextPrimary
            )
          }
          Text(
            text = "Trivia de Química Orgánica & Inorgánica",
            fontSize = 13.sp,
            color = TextSecondary
          )
        }

        // Streak & XP pill
        Row(
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Surface(
            shape = RoundedCornerShape(20.dp),
            color = AmberFlame.copy(alpha = 0.15f),
            border = BorderStroke(1.dp, AmberFlame.copy(alpha = 0.5f))
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
              Text(text = "🔥", fontSize = 13.sp)
              Text(
                text = "$streakDays d",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = AmberFlame
              )
            }
          }

          Surface(
            shape = RoundedCornerShape(20.dp),
            color = VioletIon.copy(alpha = 0.15f),
            border = BorderStroke(1.dp, VioletIon.copy(alpha = 0.5f))
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
              Text(text = "⚡", fontSize = 13.sp)
              Text(
                text = "$userXp XP",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = VioletIon
              )
            }
          }
        }
      }
    }

    // 2. Banner: Duelo Rápido en Tiempo Real
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("duel_quick_banner")
          .clickable { onNavigateToDuel() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianCard),
        border = BorderStroke(1.5.dp, Brush.horizontalGradient(listOf(NeonCyan, VioletIon)))
      ) {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .background(
              Brush.horizontalGradient(
                listOf(
                  Color(0xFF0F172A),
                  Color(0xFF1E1B4B)
                )
              )
            )
            .padding(18.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
              ) {
                Surface(
                  shape = RoundedCornerShape(6.dp),
                  color = NeonCyan
                ) {
                  Text(
                    text = "EN VIVO",
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.Black
                  )
                }
                Text(
                  text = "Duelo Rápido 1v1",
                  fontSize = 18.sp,
                  fontWeight = FontWeight.Bold,
                  color = TextPrimary
                )
              }
              Spacer(modifier = Modifier.height(6.dp))
              Text(
                text = "Desafía a otros estudiantes en tiempo real a rondas químicas de 12 segundos con comodines.",
                fontSize = 13.sp,
                color = TextSecondary,
                lineHeight = 18.sp
              )
              Spacer(modifier = Modifier.height(12.dp))
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
              ) {
                Text(
                  text = "¡Batallar Ahora!",
                  color = NeonCyan,
                  fontWeight = FontWeight.Bold,
                  fontSize = 14.sp
                )
                Icon(
                  imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                  contentDescription = null,
                  tint = NeonCyan,
                  modifier = Modifier.size(16.dp)
                )
              }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Surface(
              modifier = Modifier.size(54.dp),
              shape = CircleShape,
              color = NeonCyan.copy(alpha = 0.2f),
              border = BorderStroke(2.dp, NeonCyan)
            ) {
              Box(contentAlignment = Alignment.Center) {
                Text(text = "⚔️", fontSize = 26.sp)
              }
            }
          }
        }
      }
    }

    // 3. Category Selector (Organic vs Inorganic Filter)
    item {
      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
          text = "Selecciona Tipo de Compuestos:",
          fontWeight = FontWeight.Bold,
          fontSize = 15.sp,
          color = TextPrimary
        )

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          listOf(
            CompoundType.ALL to "🧪 Todos",
            CompoundType.ORGANIC to "🌿 Orgánicos (C-H)",
            CompoundType.INORGANIC to "💎 Inorgánicos"
          ).forEach { (type, label) ->
            val isSelected = selectedCategory == type
            Surface(
              modifier = Modifier
                .weight(1f)
                .clickable { selectedCategory = type }
                .testTag("filter_${type.name.lowercase()}"),
              shape = RoundedCornerShape(12.dp),
              color = if (isSelected) {
                when (type) {
                  CompoundType.ORGANIC -> EmeraldChem.copy(alpha = 0.2f)
                  CompoundType.INORGANIC -> NeonCyan.copy(alpha = 0.2f)
                  else -> VioletIon.copy(alpha = 0.2f)
                }
              } else ObsidianCard,
              border = BorderStroke(
                1.5.dp,
                if (isSelected) {
                  when (type) {
                    CompoundType.ORGANIC -> EmeraldChem
                    CompoundType.INORGANIC -> NeonCyan
                    else -> VioletIon
                  }
                } else ObsidianCardBorder
              )
            ) {
              Box(
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 4.dp),
                contentAlignment = Alignment.Center
              ) {
                Text(
                  text = label,
                  fontSize = 12.sp,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                  color = if (isSelected) TextPrimary else TextSecondary,
                  maxLines = 1
                )
              }
            }
          }
        }
      }
    }

    // 4. Progressive Difficulty Levels
    item {
      Text(
        text = "Niveles de Dificultad Progresivos",
        fontWeight = FontWeight.Bold,
        fontSize = 17.sp,
        color = TextPrimary
      )
    }

    val levels = listOf(
      DifficultyLevel.BEGINNER to Triple(
        "Principiante",
        "Fórmulas esenciales, nombres comunes, alcanos simples y sales neutras.",
        EmeraldChem
      ),
      DifficultyLevel.INTERMEDIATE to Triple(
        "Intermedio",
        "Grupos funcionales (alcoholes, cetonas), reacciones redox y síntesis.",
        NeonCyan
      ),
      DifficultyLevel.ADVANCED to Triple(
        "Avanzado",
        "Mecanismos de reacción, hibridación sp/sp²/sp³, isomería y acidez pKa.",
        AmberFlame
      ),
      DifficultyLevel.EXPERT to Triple(
        "Experto / Maestro",
        "Compuestos organometálicos, coordinación d-d, mutarrotación y dismutación.",
        CrimsonHazard
      )
    )

    items(levels) { (diff, meta) ->
      val (title, desc, color) = meta

      Card(
        modifier = Modifier
          .fillMaxWidth()
          .clickable { onStartPresetQuiz(selectedCategory, diff) }
          .testTag("level_card_${diff.name.lowercase()}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianCard),
        border = BorderStroke(1.dp, color.copy(alpha = 0.35f))
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          // Level number circle
          Surface(
            modifier = Modifier.size(46.dp),
            shape = CircleShape,
            color = color.copy(alpha = 0.15f),
            border = BorderStroke(1.5.dp, color)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Text(
                text = "${diff.levelNumber}",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                color = color
              )
            }
          }

          Spacer(modifier = Modifier.width(14.dp))

          Column(modifier = Modifier.weight(1f)) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = TextPrimary
              )
              DifficultyBadge(diff)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = desc,
              fontSize = 12.5.sp,
              color = TextSecondary,
              lineHeight = 17.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = "+${diff.xpReward} XP por acierto",
              fontSize = 11.5.sp,
              fontWeight = FontWeight.SemiBold,
              color = color
            )
          }

          Spacer(modifier = Modifier.width(8.dp))

          Surface(
            shape = CircleShape,
            color = color.copy(alpha = 0.2f),
            modifier = Modifier.size(36.dp)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Jugar Nivel",
                tint = color,
                modifier = Modifier.size(20.dp)
              )
            }
          }
        }
      }
    }

    // 5. Creador de Cuestionarios Personalizados Section
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "Cuestionarios Personalizados",
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            color = TextPrimary
          )
          Text(
            text = "Crea y juega tus propios exámenes químicos",
            fontSize = 12.sp,
            color = TextSecondary
          )
        }

        Button(
          onClick = onNavigateToCreateQuiz,
          colors = ButtonDefaults.buttonColors(containerColor = NeonCyan),
          shape = RoundedCornerShape(12.dp),
          contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
          modifier = Modifier.testTag("create_quiz_button")
        ) {
          Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "Crear",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
          )
        }
      }
    }

    if (customQuizzes.isEmpty()) {
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = ObsidianCard)
        ) {
          Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Text(text = "📝", fontSize = 28.sp)
            Text(
              text = "Sin cuestionarios personalizados aún",
              fontWeight = FontWeight.SemiBold,
              color = TextPrimary
            )
            Text(
              text = "Diseña tus propias preguntas químicas con compuestos, fórmulas y explicaciones para desafiar a tus compañeros.",
              fontSize = 12.sp,
              color = TextSecondary,
              textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
          }
        }
      }
    } else {
      items(customQuizzes) { quiz ->
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .testTag("custom_quiz_item_${quiz.id}"),
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = ObsidianCard),
          border = BorderStroke(1.dp, ObsidianCardBorder)
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Surface(
              modifier = Modifier.size(42.dp),
              shape = RoundedCornerShape(10.dp),
              color = VioletIon.copy(alpha = 0.2f),
              border = BorderStroke(1.dp, VioletIon)
            ) {
              Box(contentAlignment = Alignment.Center) {
                Text(text = "🧪", fontSize = 20.sp)
              }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = quiz.title,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = TextPrimary
              )
              Text(
                text = "${quiz.questionCount} preguntas • ${quiz.category}",
                fontSize = 12.sp,
                color = TextSecondary
              )
            }

            IconButton(onClick = { onStartCustomQuiz(quiz) }) {
              Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Jugar Cuestionario",
                tint = NeonCyan
              )
            }

            IconButton(onClick = { onDeleteCustomQuiz(quiz.id) }) {
              Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Eliminar",
                tint = TextMuted,
                modifier = Modifier.size(18.dp)
              )
            }
          }
        }
      }
    }
  }
}
