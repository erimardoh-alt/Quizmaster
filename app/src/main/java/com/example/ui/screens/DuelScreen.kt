package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DuelStatus
import com.example.data.model.PowerUpType
import com.example.ui.components.ChemicalFormulaBadge
import com.example.ui.components.ChemicalProgressBar
import com.example.ui.components.DetailedExplanationCard
import com.example.ui.components.PowerUpChip
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
import com.example.ui.viewmodel.DuelUiState

@Composable
fun DuelScreen(
  state: DuelUiState,
  onStartMatchmaking: (String?) -> Unit,
  onSubmitAnswer: (Int) -> Unit,
  onUseFiftyFifty: () -> Unit,
  onUseCatalyst: () -> Unit,
  onUseSpectrometry: () -> Unit,
  onShowReviewExplanation: (Int) -> Unit,
  onDismissReviewExplanation: () -> Unit,
  onExit: () -> Unit,
  modifier: Modifier = Modifier
) {
  var friendCodeInput by remember { mutableStateOf("") }
  var showFriendCodeDialog by remember { mutableStateOf(false) }

  // Review Dialog for Detailed Explanations
  if (state.showExplanationSheet && state.roundHistory.isNotEmpty()) {
    val reviewRound = state.roundHistory.getOrNull(state.explanationReviewIndex)
    if (reviewRound != null) {
      AlertDialog(
        onDismissRequest = onDismissReviewExplanation,
        containerColor = ObsidianCard,
        title = {
          Text(
            text = "Explicación Quimica (Ronda ${state.explanationReviewIndex + 1})",
            color = NeonCyan,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp
          )
        },
        text = {
          DetailedExplanationCard(
            isCorrect = reviewRound.userChoice == reviewRound.question.correctIndex,
            compoundName = reviewRound.question.compoundName,
            formula = reviewRound.question.formula,
            explanation = reviewRound.question.explanation,
            chemicalFact = reviewRound.question.chemicalFact,
            reactionEquation = reviewRound.question.reactionEquation
          )
        },
        confirmButton = {
          TextButton(onClick = onDismissReviewExplanation) {
            Text("Entendido", color = NeonCyan, fontWeight = FontWeight.Bold)
          }
        }
      )
    }
  }

  // Spectrometry Clue Dialog
  if (state.hintRevealed != null) {
    AlertDialog(
      onDismissRequest = { /* Dismissed via button */ },
      containerColor = ObsidianCard,
      icon = {
        Icon(
          imageVector = Icons.Default.Science,
          contentDescription = null,
          tint = NeonCyan,
          modifier = Modifier.size(28.dp)
        )
      },
      title = {
        Text(
          text = "🔬 Espectrometría Revelada",
          color = NeonCyan,
          fontWeight = FontWeight.Bold
        )
      },
      text = {
        Text(
          text = state.hintRevealed,
          color = TextPrimary,
          fontSize = 14.5.sp,
          lineHeight = 20.sp
        )
      },
      confirmButton = {
        TextButton(onClick = { /* state handles closure or keeps active */ }) {
          Text("Aceptar", color = NeonCyan, fontWeight = FontWeight.Bold)
        }
      }
    )
  }

  // Friend Code Input Dialog
  if (showFriendCodeDialog) {
    AlertDialog(
      onDismissRequest = { showFriendCodeDialog = false },
      containerColor = ObsidianCard,
      title = {
        Text(
          text = "Desafío con Amigos",
          color = TextPrimary,
          fontWeight = FontWeight.Bold
        )
      },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
          Text(
            text = "Introduce el código de sala o comparte tu ID para competir sincronizados en tiempo real:",
            fontSize = 13.sp,
            color = TextSecondary
          )
          OutlinedTextField(
            value = friendCodeInput,
            onValueChange = { friendCodeInput = it },
            placeholder = { Text("Ej: CHEM-8842") },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = NeonCyan,
              unfocusedBorderColor = ObsidianCardBorder,
              focusedTextColor = TextPrimary,
              unfocusedTextColor = TextPrimary
            ),
            modifier = Modifier.fillMaxWidth()
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            showFriendCodeDialog = false
            onStartMatchmaking(friendCodeInput)
          },
          colors = ButtonDefaults.buttonColors(containerColor = NeonCyan)
        ) {
          Text("Iniciar Duelo", color = Color.Black, fontWeight = FontWeight.Bold)
        }
      },
      dismissButton = {
        TextButton(onClick = { showFriendCodeDialog = false }) {
          Text("Cancelar", color = TextSecondary)
        }
      }
    )
  }

  when (state.status) {
    DuelStatus.MATCHMAKING -> {
      MatchmakingLobbyView(
        onQuickMatch = { onStartMatchmaking(null) },
        onChallengeFriend = { showFriendCodeDialog = true },
        onBack = onExit,
        modifier = modifier
      )
    }

    DuelStatus.COUNTDOWN -> {
      CountdownView(
        opponent = state.opponent,
        modifier = modifier
      )
    }

    DuelStatus.ROUND_ACTIVE, DuelStatus.ROUND_RESULT -> {
      ActiveDuelView(
        state = state,
        onSubmitAnswer = onSubmitAnswer,
        onUseFiftyFifty = onUseFiftyFifty,
        onUseCatalyst = onUseCatalyst,
        onUseSpectrometry = onUseSpectrometry,
        onExit = onExit,
        modifier = modifier
      )
    }

    DuelStatus.MATCH_FINISHED -> {
      DuelResultsView(
        state = state,
        onShowExplanation = onShowReviewExplanation,
        onRematch = { onStartMatchmaking(null) },
        onExit = onExit,
        modifier = modifier
      )
    }
  }
}

@Composable
fun MatchmakingLobbyView(
  onQuickMatch: () -> Unit,
  onChallengeFriend: () -> Unit,
  onBack: () -> Unit,
  modifier: Modifier = Modifier
) {
  val transition = rememberInfiniteTransition(label = "pulse")
  val pulseScale by transition.animateFloat(
    initialValue = 0.95f,
    targetValue = 1.08f,
    animationSpec = infiniteRepeatable(
      animation = tween(1200),
      repeatMode = RepeatMode.Reverse
    ),
    label = "scale"
  )

  Scaffold(
    modifier = modifier
      .fillMaxSize()
      .background(ObsidianBg),
    containerColor = ObsidianBg,
    topBar = {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        IconButton(onClick = onBack) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Volver",
            tint = TextSecondary
          )
        }
        Text(
          text = "Arena de Duelo Rápido",
          fontWeight = FontWeight.Bold,
          fontSize = 18.sp,
          color = TextPrimary
        )
      }
    }
  ) { padding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(padding)
        .padding(24.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(top = 20.dp)
      ) {
        // Glowing Radar Atom
        Box(
          modifier = Modifier
            .size(140.dp)
            .scale(pulseScale),
          contentAlignment = Alignment.Center
        ) {
          Surface(
            modifier = Modifier.fillMaxSize(),
            shape = CircleShape,
            color = NeonCyan.copy(alpha = 0.1f),
            border = BorderStroke(2.dp, NeonCyan.copy(alpha = 0.4f))
          ) {}
          Surface(
            modifier = Modifier.size(90.dp),
            shape = CircleShape,
            color = VioletIon.copy(alpha = 0.2f),
            border = BorderStroke(2.dp, VioletIon)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Text(text = "⚛️", fontSize = 42.sp)
            }
          }
        }

        Text(
          text = "Duelo Químico en Tiempo Real",
          fontWeight = FontWeight.Black,
          fontSize = 22.sp,
          color = TextPrimary,
          textAlign = TextAlign.Center
        )

        Text(
          text = "Compite contra otros estudiantes en un combate rápido de 5 rondas a 12 segundos. Usa comodines moleculares y suma puntos con tu velocidad de reacción.",
          fontSize = 14.sp,
          color = TextSecondary,
          textAlign = TextAlign.Center,
          lineHeight = 20.sp
        )

        // Features pills
        Row(
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          modifier = Modifier.padding(top = 8.dp)
        ) {
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = ObsidianCard,
            border = BorderStroke(1.dp, EmeraldChem.copy(alpha = 0.4f))
          ) {
            Text(
              text = "⚡ 12s por ronda",
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
              fontSize = 12.sp,
              color = EmeraldChem,
              fontWeight = FontWeight.Bold
            )
          }

          Surface(
            shape = RoundedCornerShape(12.dp),
            color = ObsidianCard,
            border = BorderStroke(1.dp, NeonCyan.copy(alpha = 0.4f))
          ) {
            Text(
              text = "⚗️ Comodines 50/50",
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
              fontSize = 12.sp,
              color = NeonCyan,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }

      Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        Button(
          onClick = onQuickMatch,
          modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .testTag("quick_match_start_button"),
          shape = RoundedCornerShape(16.dp),
          colors = ButtonDefaults.buttonColors(containerColor = NeonCyan)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Bolt,
              contentDescription = null,
              tint = Color.Black
            )
            Text(
              text = "Buscar Duelo Rápido (1v1)",
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = Color.Black
            )
          }
        }

        OutlinedButton(
          onClick = onChallengeFriend,
          modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .testTag("challenge_friend_button"),
          shape = RoundedCornerShape(16.dp),
          border = BorderStroke(1.5.dp, ObsidianCardBorder)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Group,
              contentDescription = null,
              tint = VioletIon
            )
            Text(
              text = "Desafiar Amigo con Código",
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
          }
        }
      }
    }
  }
}

@Composable
fun CountdownView(
  opponent: com.example.ui.viewmodel.OpponentProfile,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .fillMaxSize()
      .background(ObsidianBg),
    contentAlignment = Alignment.Center
  ) {
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(24.dp),
      shape = RoundedCornerShape(24.dp),
      colors = CardDefaults.cardColors(containerColor = ObsidianCard),
      border = BorderStroke(2.dp, NeonCyan)
    ) {
      Column(
        modifier = Modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        Surface(
          shape = RoundedCornerShape(8.dp),
          color = NeonCyan
        ) {
          Text(
            text = "¡OPONENTE ENCONTRADO!",
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            fontWeight = FontWeight.Black,
            fontSize = 12.sp,
            color = Color.Black
          )
        }

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceAround,
          verticalAlignment = Alignment.CenterVertically
        ) {
          // You
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(
              modifier = Modifier.size(60.dp),
              shape = CircleShape,
              color = NeonCyan.copy(alpha = 0.2f),
              border = BorderStroke(2.dp, NeonCyan)
            ) {
              Box(contentAlignment = Alignment.Center) {
                Text(text = "🧑‍🔬", fontSize = 28.sp)
              }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = "Tú", fontWeight = FontWeight.Bold, color = TextPrimary, fontSize = 14.sp)
            Text(text = "Estudiante", fontSize = 11.sp, color = TextSecondary)
          }

          Text(
            text = "VS",
            fontWeight = FontWeight.Black,
            fontSize = 24.sp,
            color = CrimsonHazard
          )

          // Opponent
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(
              modifier = Modifier.size(60.dp),
              shape = CircleShape,
              color = VioletIon.copy(alpha = 0.2f),
              border = BorderStroke(2.dp, VioletIon)
            ) {
              Box(contentAlignment = Alignment.Center) {
                Text(text = opponent.avatar, fontSize = 28.sp)
              }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = opponent.name, fontWeight = FontWeight.Bold, color = TextPrimary, fontSize = 14.sp)
            Text(text = opponent.institution, fontSize = 11.sp, color = TextSecondary)
          }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = "¡Iniciando en 3... 2... 1!",
          fontWeight = FontWeight.Bold,
          fontSize = 16.sp,
          color = AmberFlame
        )
      }
    }
  }
}

@Composable
fun ActiveDuelView(
  state: DuelUiState,
  onSubmitAnswer: (Int) -> Unit,
  onUseFiftyFifty: () -> Unit,
  onUseCatalyst: () -> Unit,
  onUseSpectrometry: () -> Unit,
  onExit: () -> Unit,
  modifier: Modifier = Modifier
) {
  val q = state.questions[state.currentRoundIndex]
  val roundProgress = (state.currentRoundIndex + 1).toFloat() / state.questions.size

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
        // Match Header with split scores
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          // User Score
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Surface(
              modifier = Modifier.size(34.dp),
              shape = CircleShape,
              color = NeonCyan.copy(alpha = 0.2f),
              border = BorderStroke(1.dp, NeonCyan)
            ) {
              Box(contentAlignment = Alignment.Center) {
                Text(text = "Tú", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = NeonCyan)
              }
            }
            Column {
              Text(text = "Puntos", fontSize = 10.sp, color = TextSecondary)
              Text(
                text = "${state.userTotalScore}",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 16.sp,
                color = NeonCyan
              )
            }
          }

          // Round & Timer
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
              text = "Ronda ${state.currentRoundIndex + 1}/${state.questions.size}",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
            Surface(
              shape = RoundedCornerShape(12.dp),
              color = if (state.roundTimeLeftSeconds <= 4) CrimsonHazard.copy(alpha = 0.2f) else ObsidianCard,
              border = BorderStroke(
                1.dp,
                if (state.roundTimeLeftSeconds <= 4) CrimsonHazard else NeonCyan
              )
            ) {
              Text(
                text = "${state.roundTimeLeftSeconds}s",
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 13.sp,
                color = if (state.roundTimeLeftSeconds <= 4) CrimsonHazard else NeonCyan
              )
            }
          }

          // Opponent Score
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Column(horizontalAlignment = Alignment.End) {
              Text(text = state.opponent.name.split(" ").first(), fontSize = 11.sp, color = TextSecondary)
              Text(
                text = "${state.opponentTotalScore}",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 16.sp,
                color = VioletIon
              )
            }
            Surface(
              modifier = Modifier.size(34.dp),
              shape = CircleShape,
              color = VioletIon.copy(alpha = 0.2f),
              border = BorderStroke(1.dp, VioletIon)
            ) {
              Box(contentAlignment = Alignment.Center) {
                Text(text = state.opponent.avatar, fontSize = 16.sp)
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Opponent live action status indicator
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF0F172A))
            .padding(horizontal = 10.dp, vertical = 5.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(
            text = "Oponente: ${state.opponentAnsweredText}",
            fontSize = 11.5.sp,
            color = if (state.isOpponentAnswering) TextSecondary else EmeraldChem,
            fontWeight = FontWeight.SemiBold
          )

          if (state.userStreak > 1) {
            Text(
              text = "🔥 Racha x${state.userStreak}",
              fontSize = 11.5.sp,
              fontWeight = FontWeight.Bold,
              color = AmberFlame
            )
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
      verticalArrangement = Arrangement.spacedBy(12.dp),
      contentPadding = PaddingValues(bottom = 24.dp)
    ) {
      // Question Card
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = ObsidianCard),
          border = BorderStroke(1.dp, ObsidianCardBorder)
        ) {
          Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              ChemicalFormulaBadge(formula = q.formula, type = q.type)
              Text(
                text = q.compoundName,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextSecondary
              )
            }

            Text(
              text = q.questionText,
              fontWeight = FontWeight.Bold,
              fontSize = 16.sp,
              color = TextPrimary,
              lineHeight = 22.sp
            )
          }
        }
      }

      // Power-up Bar (Comodines)
      item {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          PowerUpChip(
            powerUp = PowerUpType.FIFTY_FIFTY,
            isAvailable = !state.isFiftyFiftyUsed && state.userSelectedChoice == null,
            onClick = onUseFiftyFifty,
            modifier = Modifier.weight(1f)
          )
          PowerUpChip(
            powerUp = PowerUpType.TIME_BOOST,
            isAvailable = !state.isCatalystUsed && state.userSelectedChoice == null,
            onClick = onUseCatalyst,
            modifier = Modifier.weight(1f)
          )
          PowerUpChip(
            powerUp = PowerUpType.SPECTROMETRY,
            isAvailable = !state.isSpectrometryUsed && state.userSelectedChoice == null,
            onClick = onUseSpectrometry,
            modifier = Modifier.weight(1f)
          )
        }
      }

      // 4 Choice Options
      items(q.options.size) { index ->
        val isEliminated = index in state.eliminatedOptions
        val isUserChoice = state.userSelectedChoice == index
        val isOpponentChoice = state.opponentSelectedChoice == index
        val isCorrect = index == q.correctIndex
        val isResultState = state.status == DuelStatus.ROUND_RESULT

        val (bgCol, borderCol) = when {
          isResultState && isCorrect -> EmeraldChem.copy(alpha = 0.2f) to EmeraldChem
          isResultState && isUserChoice && !isCorrect -> CrimsonHazard.copy(alpha = 0.2f) to CrimsonHazard
          isUserChoice -> NeonCyan.copy(alpha = 0.2f) to NeonCyan
          isEliminated -> Color(0xFF0F172A) to Color(0xFF1E293B)
          else -> ObsidianCard to ObsidianCardBorder
        }

        Card(
          modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = !isEliminated && state.userSelectedChoice == null) {
              onSubmitAnswer(index)
            }
            .testTag("duel_option_$index"),
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = bgCol),
          border = BorderStroke(1.5.dp, borderCol)
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            val letter = when (index) {
              0 -> "A"
              1 -> "B"
              2 -> "C"
              else -> "D"
            }
            Surface(
              modifier = Modifier.size(30.dp),
              shape = CircleShape,
              color = borderCol.copy(alpha = 0.2f),
              border = BorderStroke(1.dp, borderCol)
            ) {
              Box(contentAlignment = Alignment.Center) {
                Text(
                  text = letter,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold,
                  color = if (isEliminated) TextMuted else borderCol
                )
              }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
              text = if (isEliminated) "— Descartada por 50/50 —" else q.options[index],
              modifier = Modifier.weight(1f),
              fontSize = 14.sp,
              fontWeight = if (isUserChoice) FontWeight.Bold else FontWeight.Normal,
              color = if (isEliminated) TextMuted else TextPrimary
            )

            // Player tags indicator
            if (isUserChoice) {
              Surface(
                shape = RoundedCornerShape(6.dp),
                color = NeonCyan,
                modifier = Modifier.padding(end = 4.dp)
              ) {
                Text(
                  text = "Tú",
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Black,
                  color = Color.Black
                )
              }
            }

            if (isResultState && isOpponentChoice) {
              Surface(
                shape = RoundedCornerShape(6.dp),
                color = VioletIon
              ) {
                Text(
                  text = state.opponent.avatar,
                  modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                  fontSize = 11.sp
                )
              }
            }
          }
        }
      }
    }
  }
}

@Composable
fun DuelResultsView(
  state: DuelUiState,
  onShowExplanation: (Int) -> Unit,
  onRematch: () -> Unit,
  onExit: () -> Unit,
  modifier: Modifier = Modifier
) {
  val userWon = state.userTotalScore > state.opponentTotalScore
  val isDraw = state.userTotalScore == state.opponentTotalScore
  val statusTitle = when {
    userWon -> "¡Victoria Alquímica!"
    isDraw -> "¡Empate Molecular!"
    else -> "Derrota en Laboratorio"
  }
  val statusColor = when {
    userWon -> EmeraldChem
    isDraw -> AmberFlame
    else -> CrimsonHazard
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(ObsidianBg)
      .padding(16.dp),
    contentPadding = PaddingValues(top = 16.dp, bottom = 32.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianCard),
        border = BorderStroke(2.dp, statusColor)
      ) {
        Column(
          modifier = Modifier.padding(24.dp),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
          Surface(
            modifier = Modifier.size(70.dp),
            shape = CircleShape,
            color = statusColor.copy(alpha = 0.2f),
            border = BorderStroke(2.dp, statusColor)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Text(
                text = if (userWon) "🏆" else if (isDraw) "🤝" else "⚗️",
                fontSize = 32.sp
              )
            }
          }

          Text(
            text = statusTitle,
            fontWeight = FontWeight.Black,
            fontSize = 22.sp,
            color = TextPrimary
          )

          // Split Scoreboard
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(14.dp))
              .background(Color(0xFF0F172A))
              .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text(text = "Tú", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = NeonCyan)
              Text(
                text = "${state.userTotalScore}",
                fontWeight = FontWeight.Black,
                fontSize = 26.sp,
                color = NeonCyan
              )
            }

            Text(text = "VS", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextMuted)

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text(text = state.opponent.name, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = VioletIon)
              Text(
                text = "${state.opponentTotalScore}",
                fontWeight = FontWeight.Black,
                fontSize = 26.sp,
                color = VioletIon
              )
            }
          }

          Text(
            text = if (userWon) "+150 XP de Alquimista ganados" else "+60 XP por participación",
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            color = AmberFlame
          )
        }
      }
    }

    // Rounds Review list with Detailed Explanations
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Revisión y Explicaciones de la Batalla",
          fontWeight = FontWeight.Bold,
          fontSize = 16.sp,
          color = TextPrimary
        )
      }
    }

    items(state.roundHistory.size) { idx ->
      val round = state.roundHistory[idx]
      val userRight = round.userChoice == round.question.correctIndex

      Card(
        modifier = Modifier
          .fillMaxWidth()
          .clickable { onShowExplanation(idx) },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianCard),
        border = BorderStroke(1.dp, if (userRight) EmeraldChem.copy(alpha = 0.5f) else CrimsonHazard.copy(alpha = 0.5f))
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Surface(
            modifier = Modifier.size(36.dp),
            shape = CircleShape,
            color = (if (userRight) EmeraldChem else CrimsonHazard).copy(alpha = 0.15f)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Text(
                text = if (userRight) "✓" else "✕",
                fontWeight = FontWeight.Black,
                color = if (userRight) EmeraldChem else CrimsonHazard
              )
            }
          }

          Spacer(modifier = Modifier.width(12.dp))

          Column(modifier = Modifier.weight(1f)) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
              Text(
                text = round.question.formula,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = NeonCyan
              )
              Text(
                text = "• ${round.question.compoundName}",
                fontSize = 13.sp,
                color = TextSecondary
              )
            }
            Text(
              text = "Toca para ver la explicación científica detallada",
              fontSize = 11.5.sp,
              color = VioletIon
            )
          }

          Icon(
            imageVector = Icons.Default.Science,
            contentDescription = "Ver Explicación",
            tint = NeonCyan,
            modifier = Modifier.size(20.dp)
          )
        }
      }
    }

    item {
      Spacer(modifier = Modifier.height(6.dp))
      Button(
        onClick = onRematch,
        modifier = Modifier
          .fillMaxWidth()
          .height(52.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(containerColor = NeonCyan)
      ) {
        Text(
          text = "Buscar Otro Oponente",
          color = Color.Black,
          fontWeight = FontWeight.Bold,
          fontSize = 15.sp
        )
      }

      Spacer(modifier = Modifier.height(8.dp))

      OutlinedButton(
        onClick = onExit,
        modifier = Modifier
          .fillMaxWidth()
          .height(52.dp),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.dp, ObsidianCardBorder)
      ) {
        Text(
          text = "Volver al Menú Principal",
          color = TextSecondary,
          fontWeight = FontWeight.SemiBold,
          fontSize = 15.sp
        )
      }
    }
  }
}
