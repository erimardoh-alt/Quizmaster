package com.example.ui.screens

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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Science
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
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CompoundType
import com.example.data.model.DifficultyLevel
import com.example.ui.components.ChemicalFormulaBadge
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
import com.example.ui.viewmodel.CreateQuizUiState
import com.example.ui.viewmodel.NewQuestionDraft

@Composable
fun CustomQuizCreatorScreen(
  state: CreateQuizUiState,
  onUpdateTitle: (String) -> Unit,
  onUpdateDescription: (String) -> Unit,
  onUpdateCategory: (CompoundType) -> Unit,
  onUpdateDifficulty: (DifficultyLevel) -> Unit,
  onOpenQuestionDialog: () -> Unit,
  onDismissQuestionDialog: () -> Unit,
  onUpdateDraft: ((NewQuestionDraft) -> NewQuestionDraft) -> Unit,
  onAddQuestion: () -> Unit,
  onRemoveQuestion: (Int) -> Unit,
  onSaveQuiz: () -> Unit,
  onBack: () -> Unit,
  modifier: Modifier = Modifier
) {
  // Dialog to Add a Question
  if (state.isCreatingQuestion) {
    QuestionBuilderDialog(
      draft = state.currentDraft,
      onUpdateDraft = onUpdateDraft,
      onConfirm = onAddQuestion,
      onDismiss = onDismissQuestionDialog
    )
  }

  Scaffold(
    modifier = modifier
      .fillMaxSize()
      .background(ObsidianBg),
    containerColor = ObsidianBg,
    topBar = {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 8.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          IconButton(onClick = onBack) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Volver",
              tint = TextSecondary
            )
          }
          Text(
            text = "Crear Cuestionario",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = TextPrimary
          )
        }

        Button(
          onClick = onSaveQuiz,
          enabled = state.draftQuestions.isNotEmpty() && state.title.isNotBlank(),
          colors = ButtonDefaults.buttonColors(
            containerColor = NeonCyan,
            disabledContainerColor = ObsidianCard
          ),
          shape = RoundedCornerShape(12.dp),
          contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
          modifier = Modifier.testTag("save_custom_quiz_button")
        ) {
          Text(
            text = "Guardar",
            fontWeight = FontWeight.Bold,
            color = if (state.draftQuestions.isNotEmpty() && state.title.isNotBlank()) Color.Black else TextMuted
          )
        }
      }
    }
  ) { padding ->
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(padding)
        .padding(horizontal = 16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp),
      contentPadding = PaddingValues(bottom = 32.dp)
    ) {
      // Error message banner
      if (!state.errorMessage.isNullOrBlank()) {
        item {
          Surface(
            shape = RoundedCornerShape(10.dp),
            color = CrimsonHazard.copy(alpha = 0.15f),
            border = BorderStroke(1.dp, CrimsonHazard)
          ) {
            Text(
              text = state.errorMessage,
              modifier = Modifier.padding(12.dp),
              color = CrimsonHazard,
              fontSize = 13.sp,
              fontWeight = FontWeight.SemiBold
            )
          }
        }
      }

      // Quiz Metadata Card
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = ObsidianCard),
          border = BorderStroke(1.dp, ObsidianCardBorder)
        ) {
          Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
          ) {
            Text(
              text = "Información del Cuestionario",
              fontWeight = FontWeight.Bold,
              fontSize = 15.sp,
              color = NeonCyan
            )

            OutlinedTextField(
              value = state.title,
              onValueChange = onUpdateTitle,
              label = { Text("Título del Cuestionario") },
              placeholder = { Text("Ej: Hidrocarburos y Compuestos de Síntesis") },
              singleLine = true,
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = NeonCyan,
                unfocusedBorderColor = ObsidianCardBorder,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary
              ),
              modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
              value = state.description,
              onValueChange = onUpdateDescription,
              label = { Text("Descripción o Tema de Estudio") },
              placeholder = { Text("Ej: Preguntas para el examen de química universitaria") },
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = NeonCyan,
                unfocusedBorderColor = ObsidianCardBorder,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary
              ),
              modifier = Modifier.fillMaxWidth()
            )

            // Category choice
            Text(
              text = "Tipo de Química:",
              fontWeight = FontWeight.SemiBold,
              fontSize = 13.sp,
              color = TextSecondary
            )
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              listOf(
                CompoundType.ORGANIC to "🌿 Orgánica",
                CompoundType.INORGANIC to "💎 Inorgánica",
                CompoundType.ALL to "🧪 Mixto"
              ).forEach { (cat, label) ->
                val isSelected = state.category == cat
                Surface(
                  modifier = Modifier
                    .weight(1f)
                    .clickable { onUpdateCategory(cat) },
                  shape = RoundedCornerShape(10.dp),
                  color = if (isSelected) NeonCyan.copy(alpha = 0.2f) else Color(0xFF0F172A),
                  border = BorderStroke(1.dp, if (isSelected) NeonCyan else ObsidianCardBorder)
                ) {
                  Box(
                    modifier = Modifier.padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                  ) {
                    Text(
                      text = label,
                      fontSize = 11.5.sp,
                      fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                      color = if (isSelected) TextPrimary else TextSecondary
                    )
                  }
                }
              }
            }
          }
        }
      }

      // Questions Header & Add Button
      item {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = "Preguntas Químicas (${state.draftQuestions.size})",
              fontWeight = FontWeight.Bold,
              fontSize = 16.sp,
              color = TextPrimary
            )
            Text(
              text = "Cada pregunta incluye compuesto, fórmula y explicación",
              fontSize = 12.sp,
              color = TextSecondary
            )
          }

          Button(
            onClick = onOpenQuestionDialog,
            colors = ButtonDefaults.buttonColors(containerColor = VioletIon),
            shape = RoundedCornerShape(12.dp),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
            modifier = Modifier.testTag("add_question_to_draft_button")
          ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Añadir", fontSize = 13.sp, fontWeight = FontWeight.Bold)
          }
        }
      }

      if (state.draftQuestions.isEmpty()) {
        item {
          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = ObsidianCard)
          ) {
            Column(
              modifier = Modifier.padding(24.dp),
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              Text(text = "🧪", fontSize = 28.sp)
              Text(
                text = "Aún no has agregado preguntas",
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
              )
              Text(
                text = "Presiona 'Añadir' para redactar una pregunta con su compuesto químico, fórmula, 4 opciones y explicación detallada.",
                fontSize = 12.sp,
                color = TextSecondary,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
              )
            }
          }
        }
      } else {
        itemsIndexed(state.draftQuestions) { index, question ->
          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = ObsidianCard),
            border = BorderStroke(1.dp, ObsidianCardBorder)
          ) {
            Column(
              modifier = Modifier.padding(14.dp),
              verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                  Text(
                    text = "#${index + 1}",
                    fontWeight = FontWeight.Bold,
                    color = NeonCyan,
                    fontSize = 14.sp
                  )
                  ChemicalFormulaBadge(formula = question.formula, type = question.type)
                  Text(
                    text = question.compoundName,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                  )
                }

                IconButton(
                  onClick = { onRemoveQuestion(index) },
                  modifier = Modifier.size(28.dp)
                ) {
                  Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Borrar Pregunta",
                    tint = CrimsonHazard,
                    modifier = Modifier.size(18.dp)
                  )
                }
              }

              Text(
                text = question.questionText,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary
              )

              Text(
                text = "✓ Correcta: ${question.options.getOrNull(question.correctIndex) ?: ""}",
                fontSize = 12.sp,
                color = EmeraldChem,
                fontWeight = FontWeight.Bold
              )
            }
          }
        }
      }
    }
  }
}

@Composable
fun QuestionBuilderDialog(
  draft: NewQuestionDraft,
  onUpdateDraft: ((NewQuestionDraft) -> NewQuestionDraft) -> Unit,
  onConfirm: () -> Unit,
  onDismiss: () -> Unit
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    containerColor = ObsidianCard,
    title = {
      Text(
        text = "Nueva Pregunta Química",
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = TextPrimary
      )
    },
    text = {
      LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        item {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            OutlinedTextField(
              value = draft.compoundName,
              onValueChange = { name -> onUpdateDraft { it.copy(compoundName = name) } },
              label = { Text("Compuesto") },
              placeholder = { Text("Ej: Ácido Nítrico") },
              singleLine = true,
              modifier = Modifier.weight(1.2f),
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = NeonCyan,
                unfocusedBorderColor = ObsidianCardBorder,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary
              )
            )

            OutlinedTextField(
              value = draft.formula,
              onValueChange = { f -> onUpdateDraft { it.copy(formula = f) } },
              label = { Text("Fórmula") },
              placeholder = { Text("Ej: HNO₃") },
              singleLine = true,
              modifier = Modifier.weight(1f),
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = NeonCyan,
                unfocusedBorderColor = ObsidianCardBorder,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary
              )
            )
          }
        }

        item {
          OutlinedTextField(
            value = draft.questionText,
            onValueChange = { q -> onUpdateDraft { it.copy(questionText = q) } },
            label = { Text("Enunciado de la Pregunta") },
            placeholder = { Text("¿Qué función química o propiedad presenta...?") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = NeonCyan,
              unfocusedBorderColor = ObsidianCardBorder,
              focusedTextColor = TextPrimary,
              unfocusedTextColor = TextPrimary
            )
          )
        }

        item {
          Text(
            text = "Opciones (marca el círculo de la correcta):",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextSecondary
          )
        }

        val options = listOf(
          Triple("Opción A", draft.optionA) { text: String -> onUpdateDraft { it.copy(optionA = text) } },
          Triple("Opción B", draft.optionB) { text: String -> onUpdateDraft { it.copy(optionB = text) } },
          Triple("Opción C", draft.optionC) { text: String -> onUpdateDraft { it.copy(optionC = text) } },
          Triple("Opción D", draft.optionD) { text: String -> onUpdateDraft { it.copy(optionD = text) } }
        )

        itemsIndexed(options) { idx, (label, value, updater) ->
          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
          ) {
            RadioButton(
              selected = draft.correctIndex == idx,
              onClick = { onUpdateDraft { it.copy(correctIndex = idx) } },
              colors = RadioButtonDefaults.colors(selectedColor = EmeraldChem)
            )
            OutlinedTextField(
              value = value,
              onValueChange = updater,
              placeholder = { Text(label) },
              singleLine = true,
              modifier = Modifier.weight(1f),
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (draft.correctIndex == idx) EmeraldChem else NeonCyan,
                unfocusedBorderColor = ObsidianCardBorder,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary
              )
            )
          }
        }

        item {
          OutlinedTextField(
            value = draft.explanation,
            onValueChange = { exp -> onUpdateDraft { it.copy(explanation = exp) } },
            label = { Text("Explicación Científica Detallada") },
            placeholder = { Text("Explica por qué la respuesta correcta es válida basándote en enlaces, reactividad o estructura...") },
            minLines = 3,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = NeonCyan,
              unfocusedBorderColor = ObsidianCardBorder,
              focusedTextColor = TextPrimary,
              unfocusedTextColor = TextPrimary
            )
          )
        }

        item {
          OutlinedTextField(
            value = draft.chemicalFact,
            onValueChange = { fact -> onUpdateDraft { it.copy(chemicalFact = fact) } },
            label = { Text("Dato Curioso / Aplicación en la Vida Real") },
            placeholder = { Text("Ej: Se utiliza en fertilizantes y grabado industrial...") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = NeonCyan,
              unfocusedBorderColor = ObsidianCardBorder,
              focusedTextColor = TextPrimary,
              unfocusedTextColor = TextPrimary
            )
          )
        }
      }
    },
    confirmButton = {
      Button(
        onClick = onConfirm,
        colors = ButtonDefaults.buttonColors(containerColor = NeonCyan)
      ) {
        Text("Agregar Pregunta", color = Color.Black, fontWeight = FontWeight.Bold)
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text("Cancelar", color = TextSecondary)
      }
    }
  )
}
