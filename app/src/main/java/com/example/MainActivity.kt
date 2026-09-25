package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.Science
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.local.QuizMasterDatabase
import com.example.data.model.CompoundType
import com.example.data.model.DifficultyLevel
import com.example.data.repository.QuizMasterRepository
import com.example.ui.screens.CustomQuizCreatorScreen
import com.example.ui.screens.DuelScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LeaderboardScreen
import com.example.ui.screens.QuizPlayScreen
import com.example.ui.screens.StatsScreen
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.ObsidianCard
import com.example.ui.theme.ObsidianCardBorder
import com.example.ui.theme.QuizMasterTheme
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.viewmodel.CustomQuizViewModel
import com.example.ui.viewmodel.DuelViewModel
import com.example.ui.viewmodel.QuizViewModel
import com.example.ui.viewmodel.StatsViewModel
import kotlinx.coroutines.launch

enum class AppDestination(
  val label: String,
  val selectedIcon: ImageVector,
  val unselectedIcon: ImageVector
) {
  HOME("Jugar", Icons.Filled.Science, Icons.Outlined.Science),
  DUEL("Duelo", Icons.Filled.Bolt, Icons.Outlined.Bolt),
  CREATE("Crear", Icons.Filled.AddCircle, Icons.Outlined.AddCircleOutline),
  STATS("Progreso", Icons.Filled.BarChart, Icons.Outlined.BarChart),
  RANKING("Ranking", Icons.Filled.EmojiEvents, Icons.Outlined.EmojiEvents)
}

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      QuizMasterTheme {
        QuizMasterRoot()
      }
    }
  }
}

@Composable
fun QuizMasterRoot() {
  val context = LocalContext.current.applicationContext
  val database = remember { QuizMasterDatabase.getInstance(context) }
  val repository = remember { QuizMasterRepository(database.quizDao()) }
  val coroutineScope = rememberCoroutineScope()

  val quizViewModel: QuizViewModel = viewModel { QuizViewModel(repository) }
  val duelViewModel: DuelViewModel = viewModel { DuelViewModel(repository) }
  val customQuizViewModel: CustomQuizViewModel = viewModel { CustomQuizViewModel(repository) }
  val statsViewModel: StatsViewModel = viewModel { StatsViewModel(repository) }

  var currentDestination by remember { mutableStateOf(AppDestination.HOME) }
  var isPlayingQuiz by remember { mutableStateOf(false) }

  val quizPlayState by quizViewModel.state.collectAsStateWithLifecycle()
  val duelState by duelViewModel.state.collectAsStateWithLifecycle()
  val customQuizzes by customQuizViewModel.customQuizzes.collectAsStateWithLifecycle()
  val createQuizState by customQuizViewModel.createState.collectAsStateWithLifecycle()
  val statsData by statsViewModel.dashboardData.collectAsStateWithLifecycle()

  // Handle hardware back press
  BackHandler(enabled = isPlayingQuiz || currentDestination != AppDestination.HOME) {
    if (isPlayingQuiz) {
      quizViewModel.resetQuiz()
      isPlayingQuiz = false
    } else {
      currentDestination = AppDestination.HOME
    }
  }

  Scaffold(
    modifier = Modifier.fillMaxSize(),
    bottomBar = {
      if (!isPlayingQuiz) {
        NavigationBar(
          containerColor = ObsidianCard,
          tonalElevation = 8.dp
        ) {
          AppDestination.entries.forEach { destination ->
            val isSelected = currentDestination == destination
            NavigationBarItem(
              selected = isSelected,
              onClick = {
                currentDestination = destination
              },
              icon = {
                Icon(
                  imageVector = if (isSelected) destination.selectedIcon else destination.unselectedIcon,
                  contentDescription = destination.label,
                  modifier = Modifier.size(24.dp)
                )
              },
              label = {
                Text(
                  text = destination.label,
                  fontSize = 11.sp,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
              },
              colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Black,
                selectedTextColor = NeonCyan,
                indicatorColor = NeonCyan,
                unselectedIconColor = TextMuted,
                unselectedTextColor = TextMuted
              ),
              modifier = Modifier.testTag("nav_${destination.name.lowercase()}")
            )
          }
        }
      }
    }
  ) { innerPadding ->
    if (isPlayingQuiz) {
      QuizPlayScreen(
        state = quizPlayState,
        onSelectOption = { quizViewModel.selectOption(it) },
        onSubmitAnswer = { quizViewModel.submitAnswer() },
        onNextQuestion = { quizViewModel.nextQuestion() },
        onDismissExplanation = { quizViewModel.dismissExplanation() },
        onExitQuiz = {
          quizViewModel.resetQuiz()
          isPlayingQuiz = false
        },
        onRestartQuiz = {
          quizViewModel.startPresetQuiz(
            category = quizPlayState.currentCategory,
            difficulty = quizPlayState.currentDifficulty
          )
        },
        modifier = Modifier.padding(innerPadding)
      )
    } else {
      when (currentDestination) {
        AppDestination.HOME -> {
          HomeScreen(
            userXp = statsData.totalXp,
            userRank = statsData.currentRankTitle,
            streakDays = statsData.currentStreakDays,
            customQuizzes = customQuizzes,
            onStartPresetQuiz = { cat, diff ->
              quizViewModel.startPresetQuiz(cat, diff)
              isPlayingQuiz = true
            },
            onStartCustomQuiz = { customQuiz ->
              coroutineScope.launch {
                val questions = customQuizViewModel.loadQuizQuestions(customQuiz.id)
                val cat = try { CompoundType.valueOf(customQuiz.category) } catch (e: Exception) { CompoundType.ALL }
                val diff = try { DifficultyLevel.valueOf(customQuiz.difficulty) } catch (e: Exception) { DifficultyLevel.INTERMEDIATE }
                quizViewModel.startCustomQuiz(customQuiz.title, cat, diff, questions)
                isPlayingQuiz = true
              }
            },
            onNavigateToDuel = {
              currentDestination = AppDestination.DUEL
            },
            onNavigateToCreateQuiz = {
              currentDestination = AppDestination.CREATE
            },
            onDeleteCustomQuiz = { quizId ->
              customQuizViewModel.deleteQuiz(quizId)
            },
            modifier = Modifier.padding(innerPadding)
          )
        }

        AppDestination.DUEL -> {
          DuelScreen(
            state = duelState,
            onStartMatchmaking = { friendCode ->
              duelViewModel.startMatchmaking(friendCode)
            },
            onSubmitAnswer = { choiceIdx ->
              duelViewModel.submitUserAnswer(choiceIdx)
            },
            onUseFiftyFifty = { duelViewModel.useFiftyFifty() },
            onUseCatalyst = { duelViewModel.useCatalyst() },
            onUseSpectrometry = { duelViewModel.useSpectrometry() },
            onShowReviewExplanation = { idx -> duelViewModel.showReviewExplanation(idx) },
            onDismissReviewExplanation = { duelViewModel.dismissReviewExplanation() },
            onExit = {
              currentDestination = AppDestination.HOME
            },
            modifier = Modifier.padding(innerPadding)
          )
        }

        AppDestination.CREATE -> {
          CustomQuizCreatorScreen(
            state = createQuizState,
            onUpdateTitle = { customQuizViewModel.updateTitle(it) },
            onUpdateDescription = { customQuizViewModel.updateDescription(it) },
            onUpdateCategory = { customQuizViewModel.updateCategory(it) },
            onUpdateDifficulty = { customQuizViewModel.updateDifficulty(it) },
            onOpenQuestionDialog = { customQuizViewModel.openNewQuestionDialog() },
            onDismissQuestionDialog = { customQuizViewModel.dismissQuestionDialog() },
            onUpdateDraft = { updater -> customQuizViewModel.updateDraft(updater) },
            onAddQuestion = { customQuizViewModel.addDraftQuestion() },
            onRemoveQuestion = { customQuizViewModel.removeDraftQuestion(it) },
            onSaveQuiz = {
              customQuizViewModel.saveCustomQuiz {
                currentDestination = AppDestination.HOME
              }
            },
            onBack = { currentDestination = AppDestination.HOME },
            modifier = Modifier.padding(innerPadding)
          )
        }

        AppDestination.STATS -> {
          StatsScreen(
            data = statsData,
            modifier = Modifier.padding(innerPadding)
          )
        }

        AppDestination.RANKING -> {
          LeaderboardScreen(
            leaderboard = statsData.leaderboard,
            modifier = Modifier.padding(innerPadding)
          )
        }
      }
    }
  }
}
