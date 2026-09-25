package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.ChemicalFormulaBadge
import com.example.ui.components.ChemicalProgressBar
import com.example.ui.components.DetailedExplanationCard
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
import com.example.ui.viewmodel.QuizPlayState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizPlayScreen(
  state: QuizPlayState,
  onSelectOption: (Int) -> Unit,
  onSubmitAnswer: () -> Unit,
  onNextQuestion: () -> Unit,
  onDismissExplanation: () -> Unit,
  onExitQuiz: () -> Unit,
  onRestartQuiz: () -> Unit,
  modifier: Modifier = Modifier
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  if (state.isGameOver) {
    QuizGameOverView(
      state = state,
      onRestart = onRestartQuiz,
      onExit = onExitQuiz,
      modifier = modifier
    )
    return
  }

  if (state.questions.isEmpty()) {
    Box(
      modifier = modifier
        .fillMaxSize()
        .background(ObsidianBg),
      contentAlignment = Alignment.Center
    ) {
      Text(text = "Cargando preguntas...", color = TextSecondary)
    }
    return
  }

  val currentQuestion = state.questions[state.currentIndex]
  val progress = (state.currentIndex + 1).toFloat() / state.questions.size

  Scaffold(
    modifier = modifier
      .fillMaxSize()
      .background(ObsidianBg),
    containerColor = ObsidianBg,
    topBar = {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 8.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          IconButton(onClick = onExitQuiz) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Salir",
              tint = TextSecondary
            )
          }

          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
              text = "Pregunta ${state.currentIndex + 1} de ${state.questions.size}",
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp,
              color = TextPrimary
            )
            Text(
              text = state.quizTitle,
              fontSize = 11.sp,
              color = TextSecondary
            )
          }

          Surface(
            shape = RoundedCornerShape(12.dp),
            color = ObsidianCard,
            border = BorderStroke(1.dp, NeonCyan.copy(alpha = 0.5f))
          ) {
            Text(
              text = "${state.score} pts",
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
              fontWeight = FontWeight.ExtraBold,
              fontSize = 13.sp,
              color = NeonCyan
            )
          }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Progress bar
        ChemicalProgressBar(progress = progress, color = NeonCyan)

        Spacer(modifier = Modifier.height(8.dp))

        // Timer bar
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Timer,
              contentDescription = null,
              tint = if (state.timeRemainingSeconds <= 5) CrimsonHazard else TextSecondary,
              modifier = Modifier.size(16.dp)
            )
            Text(
              text = "${state.timeRemainingSeconds}s",
              fontWeight = FontWeight.Bold,
              fontSize = 13.sp,
              color = if (state.timeRemainingSeconds <= 5) CrimsonHazard else TextSecondary
            )
          }

          if (state.streak > 1) {
            Surface(
              shape = RoundedCornerShape(10.dp),
              color = AmberFlame.copy(alpha = 0.2f),
              border = BorderStroke(1.dp, AmberFlame)
            ) {
              Text(
                text = "🔥 Racha x${state.streak}",
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = AmberFlame
              )
            }
          }
        }
      }
    }
  ) { padding ->
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(padding)
        .padding(horizontal = 16.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp),
      contentPadding = PaddingValues(bottom = 32.dp)
    ) {
      // Chemical Compound Card
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(18.dp),
          colors = CardDefaults.cardColors(containerColor = ObsidianCard),
          border = BorderStroke(1.dp, ObsidianCardBorder)
        ) {
          Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              ChemicalFormulaBadge(
                formula = currentQuestion.formula,
                type = currentQuestion.type
              )
              DifficultyBadge(currentQuestion.difficulty)
            }

            Text(
              text = currentQuestion.questionText,
              fontSize = 17.sp,
              fontWeight = FontWeight.Bold,
              color = TextPrimary,
              lineHeight = 24.sp
            )

            if (!currentQuestion.reactionEquation.isNullOrBlank()) {
              Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFF0F172A),
                border = BorderStroke(1.dp, Color(0xFF1E293B)),
                modifier = Modifier.fillMaxWidth()
              ) {
                Text(
                  text = currentQuestion.reactionEquation,
                  modifier = Modifier.padding(10.dp),
                  fontFamily = FontFamily.Monospace,
                  fontSize = 12.5.sp,
                  fontWeight = FontWeight.Bold,
                  color = VioletIon
                )
              }
            }
          }
        }
      }

      // 4 Answer Option Cards
      items(currentQuestion.options.size) { index ->
        val optionText = currentQuestion.options[index]
        val isSelected = state.selectedAnswerIndex == index
        val isCorrectOption = index == currentQuestion.correctIndex

        val (bgCol, borderCol, iconTint) = when {
          state.isAnswerSubmitted && isCorrectOption -> Triple(
            EmeraldChem.copy(alpha = 0.18f),
            EmeraldChem,
            EmeraldChem
          )
          state.isAnswerSubmitted && isSelected && !isCorrectOption -> Triple(
            CrimsonHazard.copy(alpha = 0.18f),
            CrimsonHazard,
            CrimsonHazard
          )
          isSelected -> Triple(
            NeonCyan.copy(alpha = 0.15f),
            NeonCyan,
            NeonCyan
          )
          else -> Triple(
            ObsidianCard,
            ObsidianCardBorder,
            TextMuted
          )
        }

        Card(
          modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = !state.isAnswerSubmitted) {
              onSelectOption(index)
            }
            .testTag("option_button_$index"),
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = bgCol),
          border = BorderStroke(1.5.dp, borderCol)
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Surface(
              modifier = Modifier.size(32.dp),
              shape = CircleShape,
              color = borderCol.copy(alpha = 0.2f),
              border = BorderStroke(1.dp, borderCol)
            ) {
              Box(contentAlignment = Alignment.Center) {
                val letter = when (index) {
                  0 -> "A"
                  1 -> "B"
                  2 -> "C"
                  else -> "D"
                }
                Text(
                  text = letter,
                  fontWeight = FontWeight.Bold,
                  fontSize = 13.sp,
                  color = if (isSelected || state.isAnswerSubmitted && isCorrectOption) borderCol else TextSecondary
                )
              }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Text(
              text = optionText,
              modifier = Modifier.weight(1f),
              fontSize = 14.5.sp,
              fontWeight = if (isSelected || (state.isAnswerSubmitted && isCorrectOption)) FontWeight.Bold else FontWeight.Normal,
              color = TextPrimary,
              lineHeight = 20.sp
            )

            if (state.isAnswerSubmitted) {
              if (isCorrectOption) {
                Icon(
                  imageVector = Icons.Default.Check,
                  contentDescription = "Correcto",
                  tint = EmeraldChem,
                  modifier = Modifier.size(22.dp)
                )
              } else if (isSelected) {
                Icon(
                  imageVector = Icons.Default.Close,
                  contentDescription = "Incorrecto",
                  tint = CrimsonHazard,
                  modifier = Modifier.size(22.dp)
                )
              }
            }
          }
        }
      }

      // Bottom Action Button
      item {
        Spacer(modifier = Modifier.height(4.dp))

        if (!state.isAnswerSubmitted) {
          Button(
            onClick = onSubmitAnswer,
            enabled = state.selectedAnswerIndex != null,
            modifier = Modifier
              .fillMaxWidth()
              .height(52.dp)
              .testTag("submit_answer_button"),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = NeonCyan,
              disabledContainerColor = ObsidianCard
            )
          ) {
            Text(
              text = "Confirmar Respuesta",
              fontWeight = FontWeight.Bold,
              fontSize = 16.sp,
              color = if (state.selectedAnswerIndex != null) Color.Black else TextMuted
            )
          }
        } else {
          Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            // Detailed explanation card preview directly visible
            DetailedExplanationCard(
              isCorrect = state.isCorrect,
              compoundName = currentQuestion.compoundName,
              formula = currentQuestion.formula,
              explanation = currentQuestion.explanation,
              chemicalFact = currentQuestion.chemicalFact,
              reactionEquation = currentQuestion.reactionEquation
            )

            Button(
              onClick = onNextQuestion,
              modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("next_question_button"),
              shape = RoundedCornerShape(14.dp),
              colors = ButtonDefaults.buttonColors(containerColor = NeonCyan)
            ) {
              Text(
                text = if (state.currentIndex + 1 < state.questions.size) "Siguiente Pregunta" else "Ver Resultados",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black
              )
            }
          }
        }
      }
    }
  }
}

@Composable
fun QuizGameOverView(
  state: QuizPlayState,
  onRestart: () -> Unit,
  onExit: () -> Unit,
  modifier: Modifier = Modifier
) {
  val correctAnswersCount = state.questions.count { true } // All evaluated
  val accuracy = if (state.questions.isNotEmpty()) {
    ((state.score.toFloat() / (state.questions.size * 100).coerceAtLeast(1)) * 100).toInt().coerceAtMost(100)
  } else 0

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(ObsidianBg)
      .padding(24.dp),
    contentAlignment = Alignment.Center
  ) {
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(24.dp),
      colors = CardDefaults.cardColors(containerColor = ObsidianCard),
      border = BorderStroke(1.5.dp, Brush.verticalGradient(listOf(NeonCyan, VioletIon)))
    ) {
      Column(
        modifier = Modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        Surface(
          modifier = Modifier.size(70.dp),
          shape = CircleShape,
          color = AmberFlame.copy(alpha = 0.2f),
          border = BorderStroke(2.dp, AmberFlame)
        ) {
          Box(contentAlignment = Alignment.Center) {
            Text(text = "🏆", fontSize = 34.sp)
          }
        }

        Text(
          text = "¡Cuestionario Completado!",
          fontWeight = FontWeight.ExtraBold,
          fontSize = 22.sp,
          color = TextPrimary
        )

        Text(
          text = state.quizTitle,
          fontSize = 13.sp,
          color = TextSecondary
        )

        // Score summary metrics
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Color(0xFF0F172A))
            .padding(16.dp),
          horizontalArrangement = Arrangement.SpaceAround
        ) {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Puntos", fontSize = 11.sp, color = TextSecondary)
            Text(
              text = "${state.score}",
              fontWeight = FontWeight.Black,
              fontSize = 20.sp,
              color = NeonCyan
            )
          }

          Box(
            modifier = Modifier
              .width(1.dp)
              .height(36.dp)
              .background(ObsidianCardBorder)
          )

          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "XP Ganada", fontSize = 11.sp, color = TextSecondary)
            Text(
              text = "+${state.xpGained}",
              fontWeight = FontWeight.Black,
              fontSize = 20.sp,
              color = VioletIon
            )
          }

          Box(
            modifier = Modifier
              .width(1.dp)
              .height(36.dp)
              .background(ObsidianCardBorder)
          )

          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Racha Máx", fontSize = 11.sp, color = TextSecondary)
            Text(
              text = "${state.maxStreak}",
              fontWeight = FontWeight.Black,
              fontSize = 20.sp,
              color = AmberFlame
            )
          }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Button(
          onClick = onRestart,
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .testTag("restart_quiz_button"),
          shape = RoundedCornerShape(14.dp),
          colors = ButtonDefaults.buttonColors(containerColor = NeonCyan)
        ) {
          Text(
            text = "Jugar Nuevamente",
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontSize = 15.sp
          )
        }

        OutlinedButton(
          onClick = onExit,
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .testTag("exit_to_menu_button"),
          shape = RoundedCornerShape(14.dp),
          border = BorderStroke(1.dp, ObsidianCardBorder)
        ) {
          Text(
            text = "Volver al Menú Principal",
            fontWeight = FontWeight.SemiBold,
            color = TextSecondary,
            fontSize = 15.sp
          )
        }
      }
    }
  }
}
