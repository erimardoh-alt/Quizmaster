package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.CustomQuizEntity
import com.example.data.model.CompoundType
import com.example.data.model.DifficultyLevel
import com.example.data.model.TriviaQuestion
import com.example.data.repository.QuizMasterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class NewQuestionDraft(
  val compoundName: String = "",
  val formula: String = "",
  val type: CompoundType = CompoundType.ORGANIC,
  val difficulty: DifficultyLevel = DifficultyLevel.INTERMEDIATE,
  val questionText: String = "",
  val optionA: String = "",
  val optionB: String = "",
  val optionC: String = "",
  val optionD: String = "",
  val correctIndex: Int = 0,
  val explanation: String = "",
  val chemicalFact: String = ""
)

data class CreateQuizUiState(
  val title: String = "",
  val description: String = "",
  val category: CompoundType = CompoundType.ORGANIC,
  val difficulty: DifficultyLevel = DifficultyLevel.INTERMEDIATE,
  val draftQuestions: List<TriviaQuestion> = emptyList(),
  val isCreatingQuestion: Boolean = false,
  val currentDraft: NewQuestionDraft = NewQuestionDraft(),
  val saveSuccessMessage: String? = null,
  val errorMessage: String? = null
)

class CustomQuizViewModel(private val repository: QuizMasterRepository) : ViewModel() {

  val customQuizzes: StateFlow<List<CustomQuizEntity>> = repository.customQuizzes
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5000),
      initialValue = emptyList()
    )

  private val _createState = MutableStateFlow(CreateQuizUiState())
  val createState: StateFlow<CreateQuizUiState> = _createState.asStateFlow()

  init {
    seedDefaultQuizIfNeeded()
  }

  private fun seedDefaultQuizIfNeeded() {
    viewModelScope.launch {
      // Check if we should insert a starter custom quiz for users to explore
      repository.customQuizzes.collect { list ->
        if (list.isEmpty()) {
          val starterQuestions = listOf(
            TriviaQuestion(
              id = "draft_1",
              compoundName = "Éter Dietílico",
              formula = "(C₂H₅)₂O",
              type = CompoundType.ORGANIC,
              difficulty = DifficultyLevel.INTERMEDIATE,
              questionText = "¿Cuál es el método industrial clásico de síntesis del éter dietílico a partir de etanol?",
              options = listOf(
                "Deshidratación intermolecular con ácido sulfúrico a 140 °C",
                "Halogenación por radicales libres a alta presión",
                "Hidrólisis básica de acetonitrilo",
                "Oxidación catalítica con permanganato"
              ),
              correctIndex = 0,
              explanation = "A 140 °C en presencia de H₂SO₄, dos moléculas de etanol reaccionan mediante sustitución nucleofílica bimolecular (Sn2) intermolecular para formar éter dietílico. A temperaturas superiores (180 °C), compite la eliminación intramolecular rindiendo etileno.",
              chemicalFact = "Fue uno de los primeros anestésicos quirúrgicos inhalados utilizados en 1846 en el Hospital General de Massachusetts.",
              reactionEquation = "2 C₂H₅OH (con H₂SO₄ conc. a 140°C) → C₂H₅-O-C₂H₅ + H₂O"
            ),
            TriviaQuestion(
              id = "draft_2",
              compoundName = "Nitrato de Plata",
              formula = "AgNO₃",
              type = CompoundType.INORGANIC,
              difficulty = DifficultyLevel.INTERMEDIATE,
              questionText = "¿Qué color de precipitado se produce al añadir nitrato de plata a una muestra que contiene iones yoduro (I⁻)?",
              options = listOf(
                "Precipitado amarillo pálido de AgI",
                "Precipitado blanco lechoso de AgCl",
                "Precipitado azul brillante de Ag₂O",
                "No hay precipitado porque es completamente soluble"
              ),
              correctIndex = 0,
              explanation = "El catión Ag⁺ reacciona selectivamente con aniones haluro. Con cloruro forma AgCl blanco, con bromuro AgBr crema, y con yoduro produce yoduro de plata (AgI) de un característico color amarillo vivo e insoluble en amoniaco.",
              chemicalFact = "El yoduro de plata (AgI) se utiliza en la 'siembra de nubes' atmosférica para inducir lluvia artificial debido a que su estructura cristalina es casi idéntica a la del hielo.",
              reactionEquation = "Ag⁺(aq) + I⁻(aq) → AgI(s)↓ (Kps = 8.5 × 10⁻¹⁷)"
            )
          )

          repository.createCustomQuiz(
            title = "Laboratorio Químico: Síntesis y Reactivos",
            description = "Cuestionario creado con reactivos de síntesis orgánica y sales inorgánicas de ensayo analítico.",
            category = CompoundType.ALL,
            difficulty = DifficultyLevel.INTERMEDIATE,
            questions = starterQuestions
          )
        }
      }
    }
  }

  fun updateTitle(newTitle: String) {
    _createState.update { it.copy(title = newTitle, errorMessage = null) }
  }

  fun updateDescription(newDesc: String) {
    _createState.update { it.copy(description = newDesc) }
  }

  fun updateCategory(newCat: CompoundType) {
    _createState.update { it.copy(category = newCat) }
  }

  fun updateDifficulty(newDiff: DifficultyLevel) {
    _createState.update { it.copy(difficulty = newDiff) }
  }

  fun openNewQuestionDialog() {
    _createState.update {
      it.copy(
        isCreatingQuestion = true,
        currentDraft = NewQuestionDraft(
          type = it.category,
          difficulty = it.difficulty
        )
      )
    }
  }

  fun dismissQuestionDialog() {
    _createState.update { it.copy(isCreatingQuestion = false) }
  }

  fun updateDraft(updater: (NewQuestionDraft) -> NewQuestionDraft) {
    _createState.update { it.copy(currentDraft = updater(it.currentDraft)) }
  }

  fun addDraftQuestion() {
    val draft = _createState.value.currentDraft
    if (draft.compoundName.isBlank() || draft.formula.isBlank() || draft.questionText.isBlank() ||
      draft.optionA.isBlank() || draft.optionB.isBlank() || draft.optionC.isBlank() || draft.optionD.isBlank() ||
      draft.explanation.isBlank()
    ) {
      _createState.update { it.copy(errorMessage = "Por favor completa todos los campos de la pregunta y la explicación.") }
      return
    }

    val newQuestion = TriviaQuestion(
      id = "custom_${System.currentTimeMillis()}",
      compoundName = draft.compoundName.trim(),
      formula = draft.formula.trim(),
      type = draft.type,
      difficulty = draft.difficulty,
      questionText = draft.questionText.trim(),
      options = listOf(draft.optionA.trim(), draft.optionB.trim(), draft.optionC.trim(), draft.optionD.trim()),
      correctIndex = draft.correctIndex,
      explanation = draft.explanation.trim(),
      chemicalFact = draft.chemicalFact.ifBlank { "Compuesto sintetizado para estudio en QuizMaster." },
      isCustom = true
    )

    _createState.update {
      it.copy(
        draftQuestions = it.draftQuestions + newQuestion,
        isCreatingQuestion = false,
        currentDraft = NewQuestionDraft(),
        errorMessage = null
      )
    }
  }

  fun removeDraftQuestion(index: Int) {
    _createState.update {
      val mutable = it.draftQuestions.toMutableList()
      if (index in mutable.indices) {
        mutable.removeAt(index)
      }
      it.copy(draftQuestions = mutable)
    }
  }

  fun saveCustomQuiz(onSaved: () -> Unit) {
    val curr = _createState.value
    if (curr.title.isBlank()) {
      _createState.update { it.copy(errorMessage = "Escribe un título para el cuestionario.") }
      return
    }
    if (curr.draftQuestions.isEmpty()) {
      _createState.update { it.copy(errorMessage = "Debes añadir al menos 1 pregunta al cuestionario.") }
      return
    }

    viewModelScope.launch {
      repository.createCustomQuiz(
        title = curr.title.trim(),
        description = curr.description.ifBlank { "Cuestionario personalizado de química" },
        category = curr.category,
        difficulty = curr.difficulty,
        questions = curr.draftQuestions
      )
      _createState.value = CreateQuizUiState(saveSuccessMessage = "¡Cuestionario guardado con éxito!")
      onSaved()
    }
  }

  fun deleteQuiz(quizId: Long) {
    viewModelScope.launch {
      repository.deleteCustomQuiz(quizId)
    }
  }

  suspend fun loadQuizQuestions(quizId: Long): List<TriviaQuestion> {
    return repository.getQuestionsForQuiz(quizId)
  }
}
